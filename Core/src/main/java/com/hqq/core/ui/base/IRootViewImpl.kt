package com.hqq.core.ui.base

import android.app.Activity
import android.content.pm.ActivityInfo
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.hqq.core.R
import com.hqq.core.utils.log.LogUtils

/**
 * @Author : huangqiqiang
 * @Package : com.core.library.ui
 * @FileName :   RootViewBuilder
 * @Date : 2018/12/4 0004  下午 7:03
 * @Descrive :
 * @Email :  qiqiang213@gmail.com
 * 1. 　动态添加 生成根布局  支持LineLayout 与FarmLayout
 * 2. 　根据条件　判断添加状态栏标题栏以及设置状态栏模式
 */
class IRootViewImpl<T : Any>(context: T, showStatus: Boolean, showToolBar: Boolean) : IRootView {
    /**
     * 当前activity
     */
     var activity: Activity? = null

    /**
     *  延迟加载  根布局
     */
    lateinit var rootView: View

    /**
     * 是否强制竖屏
     */
    var isAlwaysPortrait: Boolean = true

    /**
     * 布局构建器
     */
    var createRootViewModel: CreateRootViewModel = CreateRootViewModel()

    constructor(activity: T) : this(activity, false, false)

    init {
        createRootViewModel.iToolBarBuilder.showStatusBar = showStatus
        createRootViewModel.iToolBarBuilder.showToolBar = showToolBar
        if (context is Activity) {
            activity = context
            // 只有在Activity的情况下才会去设置状态栏的颜色  其他的情况默认采用 activity的颜色
            createRootViewModel.immersiveStatusBar = true
        } else if (context is DialogFragment) {
            activity = (context as DialogFragment).activity
            createRootViewModel.bgColor = R.color.transparent
        } else if (context is Fragment) {
            activity = (context as Fragment).activity
        } else {
            LogUtils.e(Exception("不支持的类" + context.javaClass.getName()))
        }
        activity?.let { createRootViewModel.setActivity(it) }
    }

    override fun buildContentView(iActivityBuilder: ICreateRootView): View {
        rootView = createRootViewModel.createRootView(iActivityBuilder)
        return rootView
    }

    override fun initActivity(fullScreen: Boolean) {
        // 全屏的需求只有在activity上才需要的
        if (fullScreen) {
            //隐藏状态 上的字体和颜色
            activity!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            activity!!.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                    , WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
        //强制竖屏
        if (isAlwaysPortrait) {
            activity!!.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }
}