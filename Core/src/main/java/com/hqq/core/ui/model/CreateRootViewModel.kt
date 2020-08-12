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
class CreateRootViewModel
/**
 * 创建rootView
 *
 * @param isShowStatus  状态栏
 * @param isShowToolBar 标题栏
 */(
        /**
         * 是否显示状态栏
         */
        private var mIsShowStatus: Boolean,
        /**
         * 是否显示 标题栏
         */
        private var mIsShowToolBar: Boolean) {
    /**
     * 标题栏类型
     */
    private var mClass: Class<out IToolBar?> = CoreBuildConfig.instance.defItoobar

    /**
     * 布局类型
     */
    @LayoutModel
    private var mLayoutMode: Int = LayoutModel.Companion.LAYOUT_MODE_LINEAR_LAYOUT

    /**
     * 当前上下文
     */
    var mActivity: WeakReference<Activity?>? = null

    /**
     * 状态栏颜色
     */
    @ColorRes
    var mStatusColor = R.color.white

    /**
     * dialogFragment 不带背景颜色
     */
    @ColorRes
    var mBgColor = R.color.bg_color

    /**
     * 标题栏
     */
    private var mIToolBar: IToolBar? = null

    /**
     * 是否执行 状态栏 透明化
     */
    private var immersiveStatusBar = false

    /**
     * 获取状态栏模式
     *
     * @return
     */
    /**
     * 设置状态栏模式
     *
     * @param statusBarMode
     * @return
     */
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
    fun createRootView(iActivityBuilder: ICreateRootView): View? {
        return if (mLayoutMode == LayoutModel.Companion.LAYOUT_MODE_LINEAR_LAYOUT) {
            createLayoutView(iActivityBuilder)
        } else if (mLayoutMode == LayoutModel.Companion.LAYOUT_MODE_FRAME_LAYOUT) {
            createFrameLayoutView(iActivityBuilder)
        } else {
            null
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
        frameLayout.setBackgroundResource(mBgColor)
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
        layout.setBackgroundResource(mBgColor)
        layout.addView(view)
        return layout
    }

    private fun getLayoutView(iActivityBuilder: ICreateRootView, layout: ViewGroup): View? {
        var view: View?
        if (iActivityBuilder.mLayoutViewId!! > 0) {
            view = mActivity!!.get()!!.layoutInflater.inflate(iActivityBuilder.mLayoutViewId!!, layout, false)
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
        if (mIsShowToolBar || mIsShowStatus) {
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
                .setShowStatusBar(mIsShowStatus)
                .setShowToolBar(mIsShowToolBar)
                .setStatusBarColor(mStatusColor)
        return iToolBarBuilder!!.create(mClass)
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
        mIsShowStatus = showStatus
        mIsShowToolBar = showToolBar
    }

    /**
     * 是否显示标题栏
     *
     * @param showStatus
     */
    fun setShowStatusBar(showStatus: Boolean) {
        mIsShowStatus = showStatus
    }

    /**
     * 是否显示标题栏
     *
     * @param showToolBar
     */
    fun setShowToolBar(showToolBar: Boolean) {
        mIsShowToolBar = showToolBar
    }

    /**
     * toolBar 的类名
     *
     * @param clss
     */
    fun setIToolBarClass(clss: Class<out BaseToolBar?>) {
        mClass = clss
    }

    /**
     * 设置根部的样式  目前只支持两种  布局
     *
     * @param layoutMode
     */
    fun setLayoutMode(@LayoutModel layoutMode: Int) {
        mLayoutMode = layoutMode
    }

    /**
     * 状态栏颜色
     *
     * @param statusColor
     */
    fun setStatusColor(@ColorRes statusColor: Int) {
        mStatusColor = statusColor
    }

    /**
     * 当前Activity 对象
     *
     * @param activity
     */
    fun setActivity(activity: Activity?) {
        mActivity = WeakReference(activity)
    }

    /**
     * 是否设置状态适配  activity 是默认设置的
     *
     * @param immersiveStatusBar
     */
    fun setImmersiveStatusBar(immersiveStatusBar: Boolean) {
        this.immersiveStatusBar = immersiveStatusBar
    }

    /**
     * 背景颜色 默认是color_bg
     * 当前是dialog 的时候默认设置为透明颜色
     *
     * @param bgColor
     */
    fun setBgColor(@ColorRes bgColor: Int) {
        mBgColor = bgColor
    }

}