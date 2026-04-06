package com.easy.core.ui.compose

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.fragment.app.Fragment
import com.easy.core.common.TAG
import com.easy.core.utils.BundleAction
import com.easy.core.utils.log.LogUtils
import com.easy.core.widget.LoadingView
import java.util.concurrent.atomic.AtomicInteger

/**
 * Compose 页面 Fragment 基类。
 * 结构与 BaseComposeActivity 保持一致，但宿主生命周期改为 Fragment。
 */
abstract class BaseComposeFragment : Fragment(), ComposeRootViewHost, BundleAction, View.OnClickListener {
    internal val activityResultMap = mutableMapOf<Int, (ActivityResult) -> Unit>()
    internal var requestCodeGenerator = AtomicInteger(2000)
    internal val registerForActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val requestCode = result.data?.getIntExtra("__request_code__", -1) ?: -1
            if (requestCode != -1) {
                activityResultMap.remove(requestCode)?.invoke(result)
            }
        }

    protected var rootView:View? = null

    var loadingView:LoadingView? = null

    protected val rootViewBuilder:RootViewBuilder by lazy {
        RootViewBuilder.forFragment(this, this)
    }

    override val bundle:Bundle?
        get() = arguments

    /**
     * Fragment 默认复用宿主 Activity title 作为页面标题。
     */
    override fun providePageTitle():CharSequence? = activity?.title

    /**
     * 提供默认的标题栏状态控制器，便于子类按需改标题栏状态。
     */
    protected open val composeToolbarController: ComposeToolbarController by lazy {
        ComposeToolbarController(title = providePageTitle())
    }

    /**
     * 将默认标题栏控制器暴露给根布局装配层使用。
     */
    override fun provideComposeToolbarController(): ComposeToolbarController = composeToolbarController

    /**
     * 默认以 Compose 作为 Fragment 的内容区域。
     */
    override fun provideContentSpec():ContentSpec {
        return ContentSpec.Compose {
            initComposeView()
        }
    }

    @Composable
    protected abstract fun initComposeView()

    /**
     * 保留旧基类 initView 的使用习惯。
     */
    open fun initView() {}

    /**
     * 保留旧基类点击扩展点，默认不处理。
     */
    override fun onClick(v:View) {}

    /**
     * 首次创建时渲染根布局，避免重复构建 ComposeView 容器。
     */
    override fun onCreateView(inflater:LayoutInflater, container:ViewGroup?, savedInstanceState:Bundle?):View? {
        if (rootView == null) {
            activity?.let {
                loadingView = LoadingView(it)
            }
            val config = rootViewBuilder.buildConfig()
            rootView = rootViewBuilder.rootViewImpl.render(config)
            rootView?.let { installViewTreeOwners(it) }
            initView()
        }
        LogUtils.dMark(TAG.LIVE_TAG, block = { "${this}   onCreateView" })
        return rootView
    }

    /**
     * 清理对 View 的引用，避免 Fragment 视图生命周期结束后继续持有旧 rootView。
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        LogUtils.dMark(TAG.LIVE_TAG, block = { "${this}   onViewCreated" })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        LogUtils.dMark(TAG.LIVE_TAG, block = { "${this}   onDestroyView" })
        loadingView = null
        rootView = null
    }

    /**
     * 给根视图补齐 Compose 运行所需的 ViewTree owner。
     */
    protected open fun installViewTreeOwners(rootView: View) {
        setViewTreeOwner(
            className = "androidx.lifecycle.ViewTreeLifecycleOwner",
            ownerTypeName = "androidx.lifecycle.LifecycleOwner",
            rootView = rootView,
            owner = this
        )
        setViewTreeOwner(
            className = "androidx.lifecycle.ViewTreeViewModelStoreOwner",
            ownerTypeName = "androidx.lifecycle.ViewModelStoreOwner",
            rootView = rootView,
            owner = this
        )
        setViewTreeOwner(
            className = "androidx.savedstate.ViewTreeSavedStateRegistryOwner",
            ownerTypeName = "androidx.savedstate.SavedStateRegistryOwner",
            rootView = rootView,
            owner = this
        )
    }

    /**
     * 通过反射兼容设置不同 AndroidX 版本下的 ViewTree owner。
     */
    protected fun setViewTreeOwner(
        className: String,
        ownerTypeName: String,
        rootView: View,
        owner: Any
    ) {
        runCatching {
            val ownerClass = Class.forName(ownerTypeName)
            val treeOwnerClass = Class.forName(className)
            val setMethod = treeOwnerClass.getMethod("set", View::class.java, ownerClass)
            setMethod.invoke(null, rootView, owner)
        }
    }
}
