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
 * @Email :  qiqiang213@gmail.com
 * @Descrive :
 * 1. 　动态添加 生成根布局  支持LineLayout 与FarmLayout
 * 2. 　根据条件　判断添加状态栏标题栏以及设置状态栏模式
 */
class ICreateRootViewImpl<T : Any>(context: T, showStatus: Boolean, showToolBar: Boolean) : ICreateRootView {
    /**
     * 当前activity
     */
    var activity: Activity? = null

    /**
     * 是否强制竖屏
     */
    private val isAlwaysPortrait: Boolean = true

    /**
     * 布局构建器
     */
    var iRootViewImpl: IRootViewImpl = IRootViewImpl()

    /**
     *  跟布局
     */
    override var rootView: View? = null

    constructor(activity: T) : this(activity, false, false)

    init {
        iRootViewImpl.iToolBarBuilder.showStatusBar = showStatus
        iRootViewImpl.iToolBarBuilder.showToolBar = showToolBar
        when (context) {
            is Activity -> {
                activity = context
                //只有在Activity的情况下才会去设置状态栏的颜色  其他的情况默认采用 activity的颜色
                iRootViewImpl.immersiveStatusBar = true
            }
            is DialogFragment -> {
                activity = (context as DialogFragment).activity
                iRootViewImpl.bgColor = R.color.transparent
            }
            is Fragment -> {
                activity = (context as Fragment).activity
            }
            else -> {
                LogUtils.e(Exception("不支持的类" + context.javaClass.name))
            }
        }
        activity?.let { iRootViewImpl.setActivity(it) }
    }

    /**
     *  绑定 rootView
     */
    override fun buildContentView(iActivityBuilder: IRootView): View? {
        rootView = iRootViewImpl.createRootView(iActivityBuilder)
        return rootView
    }

    /**
     *  Activity 需要处理的 是否全屏
     */
    override fun initActivity(fullScreen: Boolean) {
        // 全屏的需求只有在activity上才需要的
        if (fullScreen) {
            //隐藏状态 上的字体和颜色
            activity?.requestWindowFeature(Window.FEATURE_NO_TITLE)
            activity?.window?.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
        //强制竖屏
        if (isAlwaysPortrait) {
            activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }
}