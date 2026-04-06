package com.easy.core.ui.base

import android.app.Activity
import android.content.pm.ActivityInfo
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.easy.core.R

/**
 * @Author : huangqiqiang
 * @Package : com.core.library.ui
 * @FileName :   RootViewBuilder
 * @Date : 2018/12/4 0004  下午 7:03
 * @Email :  qiqiang213@gmail.com
 * @Describe :
 * 1. 　动态添加 生成根布局  支持LineLayout 与FarmLayout
 * 2. 　根据条件　判断添加状态栏标题栏以及设置状态栏模式
 */
class RootViewBuilder private constructor(private val hostActivity:Activity?,
                                          showStatus:Boolean,
                                          showToolBar:Boolean,
                                          private var isAlwaysPortrait:Boolean = true,
                                          private val immersiveStatusBar:Boolean,
                                          private val backgroundColor:Int? = null) : IRootViewBuild {
    /**
     * 当前activity
     */
    var activity:Activity? = null

    /**
     * 布局构建器
     */
    var rootViewImpl:RootViewImpl = RootViewImpl()

    init {
        rootViewImpl.iToolBarBuilder.showStatusBar = showStatus
        rootViewImpl.iToolBarBuilder.showToolBar = showToolBar
        activity = hostActivity
        rootViewImpl.immersiveStatusBar = immersiveStatusBar
        backgroundColor?.let { rootViewImpl.bgColor = it }
        activity?.let { rootViewImpl.setActivity(it) }
    }

    companion object {
        /**
         * Activity 页面壳。
         * 默认启用沉浸式状态栏能力，可按需显示状态栏占位和 Toolbar。
         */
        fun forActivity(activity:Activity, showStatus:Boolean, showToolBar:Boolean, isAlwaysPortrait:Boolean = true):RootViewBuilder {
            return RootViewBuilder(hostActivity = activity, showStatus = showStatus, showToolBar = showToolBar, isAlwaysPortrait = isAlwaysPortrait, immersiveStatusBar = true)
        }

        /**
         * Fragment 页面壳。
         * 默认沿用宿主 Activity 的状态栏设置，不主动开启沉浸式状态栏。
         */
        fun forFragment(fragment:Fragment, showStatus:Boolean = false, showToolBar:Boolean = false):RootViewBuilder {
            return RootViewBuilder(hostActivity = fragment.activity, showStatus = showStatus, showToolBar = showToolBar, immersiveStatusBar = false)
        }

        /**
         * DialogFragment 页面壳。
         * 默认背景透明，便于弹窗样式自行控制遮罩和内容区域。
         */
        fun forDialogFragment(dialogFragment:DialogFragment, showStatus:Boolean = false, showToolBar:Boolean = false):RootViewBuilder {
            return RootViewBuilder(hostActivity = dialogFragment.activity, showStatus = showStatus, showToolBar = showToolBar, immersiveStatusBar = false, backgroundColor = R.color.transparent)
        }
    }

    /**
     *  绑定 rootView
     */
    override fun buildContentView(iRootView:IRootView):View? {
        return rootViewImpl.createRootView(iRootView)
    }

    /**
     *  Activity 需要处理的 是否全屏
     */
    override fun initActivity(fullScreen:Boolean) {
        // 全屏的需求只有在activity上才需要的
        if (fullScreen) {
            //隐藏状态 上的字体和颜色
            activity?.requestWindowFeature(Window.FEATURE_NO_TITLE)
            activity?.window?.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
        //强制竖屏
        if (isAlwaysPortrait) {
            //SCREEN_ORIENTATION_LANDSCAPE   横屏设
            //SCREEN_ORIENTATION_PORTRAIT   竖屏设置
            //SCREEN_ORIENTATION_UNSPECIFIED   默认设置
            // 避免 Activity 重启
            //             android:configChanges="keyboardHidden|orientation|screenSize"
            activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }
}

@Deprecated(message = "Use RootViewBuilder instead.", replaceWith = ReplaceWith("RootViewBuilder"))
typealias IRootViewBuildBuild = RootViewBuilder
