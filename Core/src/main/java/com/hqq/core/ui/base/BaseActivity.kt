package com.hqq.core.ui.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.hqq.core.R
import com.hqq.core.ui.builder.ICreateRootView.IActivityRootView
import com.hqq.core.widget.LoadingView

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.ui
 * @FileName :   BaseActivity
 * @Date : 2018/2/9  10:01
 * @Email :  qiqiang213@gmail.com
 * @Descrive :
 * 1. 生成根布局与ToolBar 与Manifest中的label 添加页面标题
 * 2. 默认强制竖屏
 * 3. 初始化 LoadingView
 */
abstract class BaseActivity : AppCompatActivity(), IActivityRootView, View.OnClickListener {
    /**
     * 当前对象
     */
    @JvmField
    protected var mActivity: Activity? = null

    /**
     * LoadingDialog
     */
    @JvmField
    var mLoadingView: LoadingView? = null

    /**
     * 根布局
     */
    @JvmField
    protected var mRootViewBuild: IRootViewImpl? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        initAnimEnter()
        super.onCreate(savedInstanceState)
        mActivity = this
        mLoadingView = LoadingView(this)
        mRootViewBuild = IRootViewImpl(this, true, true)
        initDefConfig()
        setContentView(mRootViewBuild!!.buildContentView(this))
        initView()
    }

    override fun onDestroy() {
        super.onDestroy()
        // 手动回收
        mRootViewBuild = null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            onResult(requestCode, resultCode, data!!)
        }
    }

    override fun finish() {
        super.finish()
        initAnimExit()
    }

    override fun initAnimEnter() {
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out)
    }

    override fun initAnimExit() {
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out)
    }

    override fun onResult(requestCode: Int, resultCode: Int, data: Intent) {}
    override fun onClick(v: View) {}

    /**
     * 默认配置
     */
    override fun initDefConfig() {
        mRootViewBuild!!.initActivity(false)
    }

    /**
     * 初始化  rootView
     * 默认 不采用
     *
     * @return
     */
    override fun getLayoutView(parent: ViewGroup): View? {
        return null
    }
}