package com.easy.core.ui.compose

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.easy.core.common.TAG
import com.easy.core.R
import com.easy.core.annotation.ToolBarMode
import com.easy.core.utils.BundleAction
import com.easy.core.utils.log.LogUtils
import com.easy.core.utils.statusbar.StatusBarManager
import com.easy.core.widget.LoadingView
import java.util.concurrent.atomic.AtomicInteger

/**
 * Compose 弹窗基类。
 * 默认使用透明背景，并关闭沉浸式状态栏控制，便于弹窗自己决定视觉表现。
 */
abstract class BaseComposeDialog : DialogFragment(), ComposeRootViewHost, BundleAction, View.OnClickListener {
    internal val activityResultMap = mutableMapOf<Int, (ActivityResult) -> Unit>()
    internal var requestCodeGenerator = AtomicInteger(2000)
    internal val registerForActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val requestCode = result.data?.getIntExtra("__request_code__", -1) ?: -1
            if (requestCode != -1) {
                activityResultMap.remove(requestCode)?.invoke(result)
            }
        }

    protected var rootView: View? = null
    private var rootViewConfig: RootViewConfig? = null
    var loadingView: LoadingView? = null

    protected val rootViewBuilder: RootViewBuilder by lazy {
        RootViewBuilder.forDialogFragment(this, this)
    }

    override val bundle: Bundle?
        get() = arguments

    /**
     * Dialog 默认读取 window title 作为页面标题。
     */
    override fun providePageTitle(): CharSequence? = dialog?.window?.attributes?.title

    /**
     * 提供默认的标题栏状态控制器，便于子类直接调节标题栏状态。
     */
    protected open val composeToolbarController: ComposeToolbarController by lazy {
        ComposeToolbarController(title = providePageTitle())
    }

    /**
     * 将默认标题栏控制器暴露给根布局装配层使用。
     */
    override fun provideComposeToolbarController(): ComposeToolbarController = composeToolbarController

    /**
     * Dialog 默认补一个返回关闭动作，保持与旧弹窗行为一致。
     */
    override fun provideComposeToolbarSpec(): ComposeToolbarSpec {
        return ComposeToolbarSpec(
            title = providePageTitle(),
            controller = provideComposeToolbarController(),
            onNavigationClick = { dismiss() }
        )
    }

    /**
     * 默认以 Compose 作为弹窗内容区域。
     */
    override fun provideContentSpec(): ContentSpec {
        return ContentSpec.Compose {
            initComposeView()
        }
    }

    /**
     * 弹窗默认不接管宿主状态栏。
     */
    override fun useImmersiveStatusBar(): Boolean = false

    /**
     * 弹窗默认透明背景，由内容自己决定视觉样式。
     */
    override fun provideBackgroundColor(): Int = android.R.color.transparent

    @Composable
    protected abstract fun initComposeView()

    /**
     * 保留旧基类 initView 的使用习惯。
     */
    open fun initView() {}

    /**
     * 保留旧基类点击扩展点，默认不处理。
     */
    override fun onClick(v: View) {}

    /**
     * 设置 Dialog 样式并记录关键生命周期日志。
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.DefDialogStyle)
        LogUtils.dMark(tag = TAG.LIVE_TAG, block = { "${this}   onCreate" })
    }

    /**
     * 首次创建时渲染 Compose 弹窗根布局。
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        LogUtils.dMark(tag = TAG.LIVE_TAG, block = { "${this}   onCreateView" })
        if (rootView == null) {
            activity?.let {
                loadingView = LoadingView(it)
            }
            val config = rootViewBuilder.buildConfig()
            rootViewConfig = config
            rootView = rootViewBuilder.rootViewImpl.render(config)
            rootView?.let { installViewTreeOwners(it) }
            initView()
            LogUtils.dMark(tag = TAG.LIVE_TAG, block = { "${this}   onCreateView   rootView is  null" })
        }
        return rootView
    }

    /**
     * Dialog 窗口层默认铺满父容器，由内部内容决定实际显示区域。
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        LogUtils.dMark(tag = TAG.LIVE_TAG, block = { "${this} onActivityCreated" })
        dialog?.window?.setBackgroundDrawable(ColorDrawable(0x00000000))
        dialog?.window?.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        applyDialogWindowConfig()
    }

    /**
     * 默认使用类名作为 tag 显示弹窗。
     */
    open fun show(manager: FragmentManager) {
        super.show(manager, javaClass.simpleName)
    }

    /**
     * 清理 View 和 loading 引用。
     */
    override fun onDestroyView() {
        super.onDestroyView()
        LogUtils.dMark(tag = TAG.LIVE_TAG, block = { "${this}   onDestroyView" })
        loadingView = null
        rootView = null
        rootViewConfig = null
    }

    /**
     * 根据当前根布局配置同步 Dialog window 的状态栏表现。
     */
    protected open fun applyDialogWindowConfig() {
        val window = dialog?.window ?: return
        val config = rootViewConfig ?: return
        when (config.statusBarMode) {
            ToolBarMode.LIGHT_MODE -> StatusBarManager.setStatusBarModel(window, true)
            ToolBarMode.DARK_MODE -> StatusBarManager.setStatusBarModel(window, false)
            else -> StatusBarManager.transparencyBar(window)
        }
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
