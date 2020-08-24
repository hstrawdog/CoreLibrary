package com.hqq.core.ui.base

import android.app.Activity
import android.content.pm.ActivityInfo
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.annotation.ColorRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.hqq.core.R
import com.hqq.core.annotation.LayoutModel
import com.hqq.core.annotation.ToolBarMode
import com.hqq.core.toolbar.BaseDefToolBarImpl
import com.hqq.core.toolbar.BaseToolBar
import com.hqq.core.toolbar.ICreateToolbar
import com.hqq.core.ui.builder.ICreateRootView
import com.hqq.core.ui.builder.IRootView
import com.hqq.core.ui.model.CreateRootViewModel
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
class IRootViewImpl<T : Any>(context: T, isShowStatus: Boolean, isShowToolBar: Boolean) : IRootView {
    /**
     * 当前activity
     */
    private var mActivity: Activity? = null

    /**
     * 获取跟布局
     *
     * @return
     */
    /**
     *  延迟加载  根布局
     */
    lateinit var rootView: View

    /**
     * 是否显示标题栏
     *
     * @param showStatus
     */
    var showStatus: Boolean
        get() = createRootViewModel.isShowStatus
        set(value) {
            createRootViewModel.isShowStatus = (value)
        }

    /**
     * toolBar 的类名
     *
     * @param clss
     */
    var iToolBarClass: ICreateToolbar
        get() = createRootViewModel.iCreateToolbar
        set(value) {
            createRootViewModel.iCreateToolbar = value
        }


    /**
     * 状态栏颜色
     *
     * @param statusColor
     */
    var statusColor: Int
        get() = createRootViewModel.statusColor
        set(value) {
            createRootViewModel.statusColor = value
        }

    /**
     * 设置状态栏模式
     *
     * @param statusBarMode
     * @return
     */
    @ToolBarMode
    var statusBarMode: Int
        get() = createRootViewModel.statusBarMode
        set(value) {
            createRootViewModel.statusBarMode = value
        }

    /**
     * 设置根部的样式  目前只支持两种  布局
     *
     * @param layoutMode
     */
    @LayoutModel
    var layoutMode
        get() = createRootViewModel.layoutMode
        set(value) {
            createRootViewModel.layoutMode = value
        }

    /**
     * 是否显示标题栏
     *
     * @param showToolBar
     */
    var isShowToolBar
        get() = createRootViewModel.isShowToolBar
        set(value) {
            createRootViewModel.isShowToolBar = value
        }

    /**
     * 是否强制竖屏
     */
    var isAlwaysPortrait = true

    /**
     * 布局构建器
     */
    var createRootViewModel: CreateRootViewModel

    constructor(activity: T) : this(activity, false, false) {}

    override fun buildContentView(iActivityBuilder: ICreateRootView): View {
        rootView = createRootViewModel.createRootView(iActivityBuilder)
        return rootView
    }

    override fun initActivity(fullScreen: Boolean) {
        // 全屏的需求只有在activity上才需要的
        if (fullScreen) {
            //隐藏状态 上的字体和颜色
            mActivity!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            mActivity!!.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                    , WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
        //强制竖屏
        if (isAlwaysPortrait) {
            mActivity!!.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }
    /*********************************Builder 方法 */
    /**
     * 获取默认状态栏
     *
     * @return
     */
    fun getDefToolBar(): BaseDefToolBarImpl? {
        return if (createRootViewModel.getIToolBar<BaseToolBar?>() != null && createRootViewModel.getIToolBar<BaseToolBar>() is BaseDefToolBarImpl) {
            createRootViewModel.getIToolBar<BaseToolBar>() as BaseDefToolBarImpl
        } else {
            // 自定义的异常 目前先抛出 类型不正确
            LogUtils.e(Exception("RootViewBuilder no fount BaseDefToolBarImpl "))
            null
        }
    }

    /**
     * 是否显示  状态栏  与标题栏
     *
     * @param showStatus  状态栏
     * @param showToolBar 标题栏
     */
    fun setToolbarVisibility(showStatus: Boolean, showToolBar: Boolean): IRootViewImpl<T> {
        createRootViewModel.setToolbarVisibility(showStatus, showToolBar)
        return this
    }


    init {
        createRootViewModel = CreateRootViewModel()
        createRootViewModel.isShowStatus = isShowStatus
        createRootViewModel.isShowToolBar = isShowToolBar
        if (context is Activity) {
            mActivity = context
            // 只有在Activity的情况下才会去设置状态栏的颜色  其他的情况默认采用 activity的颜色
            createRootViewModel.immersiveStatusBar = true
        } else if (context is DialogFragment) {
            mActivity = (context as DialogFragment).activity
            createRootViewModel.bgColor = R.color.transparent
        } else if (context is Fragment) {
            mActivity = (context as Fragment).activity
        } else {
            LogUtils.e(Exception("不支持的类" + context.javaClass.getName()))
        }
        createRootViewModel.setActivity(mActivity)
    }
}