package com.easy.core.ui.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.easy.core.R
import com.easy.core.lifecycle.SingleLiveEvent
import com.easy.core.toolbar.IToolBar
import com.easy.core.ui.base.IActivityRootView
import com.easy.core.utils.BundleAction
import com.easy.core.utils.log.LogUtils
import com.easy.core.widget.LoadingView

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
     *  回调
     */
    var activityResult = SingleLiveEvent<ActivityResult>()

    /**
     *  页面跳转
     */
    var registerForActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        activityResult.value = result
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
    private val iCreateRootView: IRootViewBuildBuild by lazy {
        IRootViewBuildBuild(this, showStatus = true, showToolBar = true, isAlwaysPortrait = isAlwaysPortrait)
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
        LogUtils.e4Debug(" ${this}     onCreate  start")
        initAnimEnter()
        super.onCreate(savedInstanceState)
        activity = this
        initConfig()
        setContentView(iCreateRootView.buildContentView(this))
        initView()
        LogUtils.e4Debug(" ${this}     onCreate end")
    }

    /**
     *  统一的判断
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
     *  统一判断  onActivityResult 方法
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