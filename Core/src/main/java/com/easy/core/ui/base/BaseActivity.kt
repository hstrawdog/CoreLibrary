package com.easy.core.ui.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.easy.core.R
import com.easy.core.common.TAG
import com.easy.core.toolbar.IToolBar
import com.easy.core.utils.BundleAction
import com.easy.core.utils.log.LogUtils
import com.easy.core.widget.LoadingView
import com.kunminx.architecture.domain.message.MutableResult
import java.util.concurrent.atomic.AtomicInteger

/**
 * @Author : huangqiqiang
 * @Package :com.easy.core.ui
 * @FileName :   BaseActivity
 * @Date : 2018/2/9  10:01
 * @Email :  qiqiang213@gmail.com
 * @Describe :
 * 1. 生成根布局与ToolBar 与Manifest中的label 添加页面标题
 * 2. 默认强制竖屏
 * 3. 初始化 LoadingView
 */

abstract class BaseActivity : AppCompatActivity(), IActivityRootView, BundleAction, View.OnClickListener {
    /**
     * 新版页面结果回调表。
     *
     * 目的：
     * 1. 让“在哪里发起页面跳转，就在哪里接收结果”。
     * 2. 避免所有结果都集中到 onActivityResult 中再由外层手动分发。
     *
     * 注意：
     * 这里服务的是库内主动封装的新跳转链路，不替代系统或三方 SDK
     * 仍依赖的 onActivityResult 兼容逻辑。
     */
    internal val activityResultMap = mutableMapOf<Int, (ActivityResult) -> Unit>()
    internal var requestCodeGenerator = AtomicInteger(2000) // 避免和 Activity 重复

    /**
     * Activity Result API 的统一入口。
     *
     * 通过在 intent 中写入内部 requestCode，将系统回调重新分发到
     * 发起方注册的 lambda 中，实现“就近接收结果”。
     */
    internal val registerForActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val requestCode = result.data?.getIntExtra("__request_code__", -1) ?: -1
            if (requestCode != -1) {
                activityResultMap.remove(requestCode)?.invoke(result)
            }
        }

    /**
     * 当前对象
     */
    lateinit var activity: Activity

    /**
     * LoadingDialog
     */
    val loadingView: LoadingView by lazy {
        LoadingView(this)
    }

    /**
     * 强制竖屏
     * 正常互联网APP 竖屏比较多
     * 横竖屏情况下不动 生命周期不会有关系
     * 等待测试
     */
    val isAlwaysPortrait = true;

    /**
     * 根布局创建
     */
    private val iCreateRootView: RootViewBuilder by lazy {
        RootViewBuilder.forActivity(
            activity = this,
            showStatus = true,
            showToolBar = true,
            isAlwaysPortrait = isAlwaysPortrait
        )
    }

    /**
     *  根布局
     */
    val rootViewImpl: RootViewImpl
        get() {
            return iCreateRootView.rootViewImpl
        }

    /**
     *  顶部标题栏与状态栏
     *  不支持赋值
     */
    val iToolBar: IToolBar?
        get() {
            return rootViewImpl.iToolBar
        }

    /**
     * bundle 集合
     */
    override val bundle: Bundle?
        get() = intent.extras

    /**
     *  初始化
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        LogUtils.dMark( tag = TAG.LIVE_TAG, block = { " ${this}     onCreate  start" })
        initAnimEnter()
        super.onCreate(savedInstanceState)
        activity = this
        initConfig()
        setContentView(iCreateRootView.buildContentView(this))
        initView()
        LogUtils.dMark( tag = TAG.LIVE_TAG, block = { " ${this}     onCreate end" })
    }

    /**
     * 兼容旧版结果回调链路。
     *
     * 保留原因：
     * 1. 部分历史页面仍通过 startActivityForResult/onActivityResult 传递结果。
     * 2. 一些第三方 SDK 或外部页面仍要求宿主继续接收 onActivityResult。
     *
     * 当前策略：
     * - 旧链路继续通过 onResult 暴露给子类处理。
     * - 新链路优先使用 registerForActivityResult，就近分发结果。
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            onResult(requestCode, resultCode, data)
        }
    }

    /**
     *  结束Activity
     */
    override fun finish() {
        super.finish()
        initAnimExit()
    }

    /**
     *  打开动画
     */
    override fun initAnimEnter() {
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out)
    }

    /**
     *  退出动画
     */
    override fun initAnimExit() {
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out)
    }

    /**
     * 旧版 Activity 结果回调扩展点。
     *
     * 仅处理仍依赖 onActivityResult 的场景，例如：
     * - 第三方 SDK 页面
     * - 历史业务页面
     * - 无法迁移到 registerForActivityResult 的旧链路
     */
    override fun onResult(requestCode: Int, resultCode: Int, data: Intent?) {}

    /**
     *  实现点击事件发方法
     */
    override fun onClick(v: View) {}

    /**
     * 默认配置
     */
    override fun initConfig() {
        iCreateRootView.initActivity(false)
    }

    /**
     * 初始化  rootView
     * 默认  不实现
     * @return
     */
    override fun getLayoutView(parent: ViewGroup): View? {
        return null
    }

}
