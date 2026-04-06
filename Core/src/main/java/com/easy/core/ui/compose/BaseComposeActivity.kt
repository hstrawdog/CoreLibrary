package com.easy.core.ui.compose

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import com.easy.core.common.TAG
import com.easy.core.utils.BundleAction
import com.easy.core.utils.log.LogUtils
import com.easy.core.widget.LoadingView
import java.util.concurrent.atomic.AtomicInteger

/**
 * Compose 页面 Activity 基类。
 *
 * 用法：
 * 1. 重写 initComposeView() 提供 Compose 页面内容。
 * 2. 按需重写 ComposeRootViewHost 中的 provideXxx 方法调整页面装配。
 */
abstract class BaseComposeActivity : AppCompatActivity(), ComposeRootViewHost, BundleAction, View.OnClickListener {
    internal val activityResultMap = mutableMapOf<Int, (ActivityResult) -> Unit>()
    internal var requestCodeGenerator = AtomicInteger(2000)
    internal val registerForActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val requestCode = result.data?.getIntExtra("__request_code__", -1) ?: -1
            if (requestCode != -1) {
                activityResultMap.remove(requestCode)?.invoke(result)
            }
        }

    lateinit var activity: Activity

    val loadingView: LoadingView by lazy {
        LoadingView(this)
    }

    protected val rootViewBuilder: RootViewBuilder by lazy {
        RootViewBuilder.forActivity(this, this)
    }

    override val bundle: Bundle?
        get() = intent.extras

    /**
     * 默认读取 Activity title 作为页面标题来源。
     */
    override fun providePageTitle(): CharSequence? = title

    /**
     * 提供默认的标题栏状态控制器，方便子类只改标题栏状态，不必重建整套 spec。
     */
    protected open val composeToolbarController: ComposeToolbarController by lazy {
        ComposeToolbarController(title = providePageTitle())
    }

    /**
     * 将默认标题栏控制器暴露给根布局装配层使用。
     */
    override fun provideComposeToolbarController(): ComposeToolbarController = composeToolbarController

    /**
     * 默认把页面内容声明为 Compose 内容。
     */
    override fun provideContentSpec(): ContentSpec {
        return ContentSpec.Compose {
            InitComposeView()
        }
    }

    @Composable
    protected abstract fun InitComposeView()

    /**
     * 保留给子类做页面初始化，位置与旧 BaseActivity 的 initView 对齐。
     */
    open fun initView() {}

    /**
     * 保留旧基类点击扩展点，默认不处理。
     */
    override fun onClick(v: View) {}

    /**
     * Activity 启动流程：
     * 1. builder 汇总配置
     * 2. 应用宿主配置
     * 3. 交给新的 RootViewImpl 渲染
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        LogUtils.dMark(tag = TAG.LIVE_TAG, block = { " ${this}     onCreate  start" })
        super.onCreate(savedInstanceState)
        activity = this
        val config = rootViewBuilder.buildConfig()
        rootViewBuilder.initHost(config)
        val rootView = rootViewBuilder.rootViewImpl.render(config)
        installViewTreeOwners(rootView)
        setContentView(rootView)
        initView()
        LogUtils.dMark(tag = TAG.LIVE_TAG, block = { " ${this}     onCreate end" })
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
