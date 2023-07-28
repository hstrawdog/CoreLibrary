package com.hqq.core.ui.base

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.annotation.ColorRes
import com.hqq.core.CoreConfig
import com.hqq.core.R
import com.hqq.core.annotation.LayoutModel
import com.hqq.core.annotation.ToolBarMode
import com.hqq.core.toolbar.IToolBar
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
 * 需求
 * 1.虚拟导航栏适配
 * 2.标题栏的多种创建方式
 * 3.微博详情的布局
 *
 * 真正核心的内容 应该拆成两个部分
 * 一个view  一个头部
 */
open class RootViewImpl {
    /**
     * 当前上下文
     */
    var activity: WeakReference<Activity>? = null

    /**
     * 布局类型
     */
    @LayoutModel
    var layoutMode: Int = LayoutModel.LAYOUT_MODE_LINEAR_LAYOUT

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
    var iToolBar: IToolBar? = null

    /**
     *  builder 构建 toolBar对象
     *  需要在 initConfig 中配置
     *  initConfig 后执行 create 之后 在设置就无效了
     *  initConfig 后就需要 去执行 iToolBar 对象 进行更新
     */
    val iToolBarBuilder = IToolBar.Builder()

    /**
     * 是否执行 状态栏 透明化
     * 是否设置状态适配  activity 是默认设置的
     */
    var immersiveStatusBar = false

    /**
     * 状态栏模式
     */
    @ToolBarMode
    var statusBarMode: Int = CoreConfig.get().isStatusMode

    /**
     *  跟布局
     */
    var rootView: View? = null

    /**
     * 构建跟布局
     *
     * @param iActivityBuilder
     * @return构建后的View
     */
    fun createRootView(iActivityBuilder: IRootView): View? {
        rootView = when (layoutMode) {
            LayoutModel.LAYOUT_MODE_LINEAR_LAYOUT -> {
                createLayoutView(iActivityBuilder)
            }

            LayoutModel.LAYOUT_MODE_FRAME_LAYOUT -> {
                createFrameLayoutView(iActivityBuilder)
            }

            else -> {
                null
            }
        }
        return rootView;
    }

    /**
     * 正常 情况下只有 帧布局 才需要 有渐变
     *
     * @param iActivityBuilder
     * @return
     */
    private fun createFrameLayoutView(iActivityBuilder: IRootView): View {
        val frameLayout = FrameLayout(activity!!.get()!!)
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
    private fun createLayoutView(iActivityBuilder: IRootView): View {
        val layout = LinearLayout(activity!!.get())
        layout.orientation = LinearLayout.VERTICAL
        layout.overScrollMode = View.OVER_SCROLL_NEVER
        layout.setBackgroundResource(bgColor)
        createToolBar(layout)
        layout.addView(getLayoutView(iActivityBuilder, layout))
        return layout
    }

    /**
     *  获取根布局  也就是Activity中绑定的View
     */
    private fun getLayoutView(iActivityBuilder: IRootView, layout: ViewGroup): View? {
        return if (iActivityBuilder.layoutViewId > 0) {
            activity?.get()?.layoutInflater?.inflate(iActivityBuilder.layoutViewId, layout, false)
        } else {
            iActivityBuilder.getLayoutView(layout)
        }
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
            if (statusBarMode == ToolBarMode.LIGHT_MODE) {
                StatusBarManager.setStatusBarModel(activity?.get()?.window, true)
            } else {
                StatusBarManager.setStatusBarModel(activity?.get()?.window, false)
            }
        }
        if (iToolBarBuilder.showToolBar || iToolBarBuilder.showStatusBar) {
            iToolBar = iToolBarBuilder.create()
            layout.addView(iToolBar?.rootView)
        }
    }

    /**
     * 当前Activity 对象
     *
     * @param activity
     */
    fun setActivity(activity: Activity) {
        this.activity = WeakReference(activity)
        iToolBarBuilder.activity = this.activity
    }


}