package com.hqq.core.ui.model

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.annotation.ColorRes
import com.hqq.core.CoreBuildConfig
import com.hqq.core.R
import com.hqq.core.annotation.LayoutModel
import com.hqq.core.annotation.ToolBarMode
import com.hqq.core.toolbar.BaseToolBar
import com.hqq.core.toolbar.ICreateToolbar
import com.hqq.core.toolbar.IToolBar
import com.hqq.core.toolbar.IToolBarBuilder
import com.hqq.core.ui.builder.ICreateRootView
import com.hqq.core.utils.log.LogUtils
import com.hqq.core.utils.statusbar.StatusBarManager
import java.lang.ref.WeakReference

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.ui.model
 * @FileName :   CreateRootViewModel
 * @Date : 2019/7/17 0017  下午 5:53
 * @Email : qiqiang213@gmail.com
 * @Descrive : 根布局的创建
 * 目的
 * 1.创建根布局
 * 2.创建标题栏
 * 3.状态栏适配控制
 *
 *
 * 需求
 * 1.虚拟导航栏适配
 * 2.标题栏的多种创建方式
 * 3.微博详情的布局
 *
 *
 *
 *
 * 真正核心的内容 应该拆成两个部分
 * 一个view  一个头部
 */


class CreateRootViewModel() {
    /**
     * 是否显示状态栏
     */
    var isShowStatus: Boolean = true

    /**
     * 是否显示 标题栏
     */
    var isShowToolBar: Boolean = true

    /**
     * 标题栏类型
     */
    var iCreateToolbar: ICreateToolbar = CoreBuildConfig.instance.iCreateToolbar

    /**
     * 布局类型
     */
    @LayoutModel
    var layoutMode: Int = LayoutModel.LAYOUT_MODE_LINEAR_LAYOUT

    /**
     * 当前上下文
     */
    var mActivity: WeakReference<Activity?>? = null

    /**
     * 状态栏颜色
     */
    @ColorRes
    var statusColor = R.color.white

    /**
     * dialogFragment 不带背景颜色
     *     背景颜色 默认是color_bg
     * 当前是dialog 的时候默认设置为透明颜色
     */
    @ColorRes
    var bgColor = R.color.bg_color

    /**
     * 标题栏
     */
    var mIToolBar: IToolBar? = null

    /**
     * 是否执行 状态栏 透明化
     * 是否设置状态适配  activity 是默认设置的
     */
    var immersiveStatusBar = false

    /**
     * 状态栏模式
     */
    @ToolBarMode
    var statusBarMode: Int = CoreBuildConfig.instance.isStatusMode

    /**
     * 构建跟布局
     *
     * @param iActivityBuilder
     * @return构建后的View
     */
    fun createRootView(iActivityBuilder: ICreateRootView): View {
        return if (layoutMode == LayoutModel.Companion.LAYOUT_MODE_LINEAR_LAYOUT) {
            createLayoutView(iActivityBuilder)
        } else if (layoutMode == LayoutModel.Companion.LAYOUT_MODE_FRAME_LAYOUT) {
            createFrameLayoutView(iActivityBuilder)
        } else {
            View(mActivity?.get())
        }
    }

    /**
     * 正常 情况下只有 帧布局 才需要 有渐变
     *
     * @param iActivityBuilder
     * @return
     */
    protected fun createFrameLayoutView(iActivityBuilder: ICreateRootView): View {
        val frameLayout = FrameLayout(mActivity!!.get()!!)
        frameLayout.overScrollMode = View.OVER_SCROLL_NEVER
        val view = getLayoutView(iActivityBuilder, frameLayout)
        frameLayout.setBackgroundResource(bgColor)
        frameLayout.addView(view)
        createToolBar(frameLayout)
        return frameLayout
    }

    /**
     * 线性 布局
     *
     * @param iActivityBuilder
     * @return
     */
    protected fun createLayoutView(iActivityBuilder: ICreateRootView): View {
        val layout = LinearLayout(mActivity!!.get())
        layout.orientation = LinearLayout.VERTICAL
        layout.overScrollMode = View.OVER_SCROLL_NEVER
        createToolBar(layout)
        val view = getLayoutView(iActivityBuilder, layout)
        layout.setBackgroundResource(bgColor)
        layout.addView(view)
        return layout
    }

    private fun getLayoutView(iActivityBuilder: ICreateRootView, layout: ViewGroup): View? {
        var view: View?
        if (iActivityBuilder.layoutViewId!! > 0) {
            view = mActivity!!.get()!!.layoutInflater.inflate(iActivityBuilder.layoutViewId!!, layout, false)
        } else {
            view = iActivityBuilder.getLayoutView(layout)
            if (view == null) {
                LogUtils.e(Exception("no fount layoutId and rootView  , must init RootView"))
                view = View(mActivity!!.get())
            }
        }
        return view
    }

    /**
     * 设置状态栏颜色   所以需要init
     * 但是可以不添加到布局中
     *
     * @param layout
     */
    private fun createToolBar(layout: ViewGroup) {
        // 默认只有Activity 会去执行设置状态栏的颜色
        if (immersiveStatusBar) {
            if (statusBarMode == ToolBarMode.Companion.LIGHT_MODE) {
                StatusBarManager.setStatusBarModel(mActivity!!.get()!!.window, true)
            } else {
                StatusBarManager.setStatusBarModel(mActivity!!.get()!!.window, false)
            }
        }
        if (isShowToolBar || isShowStatus) {
            mIToolBar = initIToolBar()
            layout.addView(mIToolBar?.rootView)
        }
    }

    /**
     * 可以重写 这个方法 去自定义  头部
     *
     * @return
     */
    fun initIToolBar(): IToolBar? {
        val iToolBarBuilder = IToolBarBuilder()
                .setActivity(mActivity!!.get())
                .setShowStatusBar(isShowStatus)
                .setShowToolBar(isShowToolBar)
                .setStatusBarColor(statusColor)
        return iToolBarBuilder?.create(iCreateToolbar)
    }

    /**
     * 获取 父类态栏
     *
     * @return
     */
    fun <T : BaseToolBar?> getIToolBar(): T? {
        if (mIToolBar == null) {
            //  自定义异常
            LogUtils.e(Exception("RootViewBuilder no fount BaseDefToolBarImpl "))
            return null
        }
        return mIToolBar as T
    }

    /**
     * 是否显示  状态栏  与标题栏
     *
     * @param showStatus  状态栏
     * @param showToolBar 标题栏
     */
    fun setToolbarVisibility(showStatus: Boolean, showToolBar: Boolean) {
        isShowStatus = showStatus
        isShowToolBar = showToolBar
    }





    /**
     * 当前Activity 对象
     *
     * @param activity
     */
    fun setActivity(activity: Activity?) {
        mActivity = WeakReference(activity)
    }


}