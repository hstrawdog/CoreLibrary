package com.easy.core.toolbar

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.ColorRes
import com.easy.core.CoreConfig
import  com.easy.core.R
import  com.easy.core.utils.ScreenUtils
import java.lang.ref.WeakReference

/**
 * @Author : huangqiqiang
 * @Package : com.core.library.toolbar
 * @FileName :   BaseToolBar
 * @Date : 2018/11/15 0015  下午 7:26
 * @Descrive :
 * @Email :  qiqiang213@gmail.com
 * -  第一层 背景    toolBar
 * -    第二层 状态栏  statusBar
 * -    第三层 标题栏  TitleBar
 */
abstract class BaseToolBar : IToolBar {
    /**
     * mLinearLayout  包含
     * 1. 顶部状态栏背景颜色
     * 2. 中间 toolbar 与 toolbar  背景颜色  与 底部的分割线
     */
    /**
     * 获取 根布局
     *
     * @return mRootView
     */
    /**
     * 根布局
     */
    override var rootView: View? = null

    /**
     * 状态栏背景颜色
     */
    @ColorRes
    protected var defStatusColor = CoreConfig.get().defStatusColor

    /**
     * 标题栏 颜色
     */
    @ColorRes
    protected var defToolBarColor = CoreConfig.get().defToolBarColor


    /**
     * 状态栏背景
     */
    var statusBar: View? = null
        protected set

    /**
     * 当前activity
     */
    protected var activity: WeakReference<Activity?>? = null

    /**
     * 获取分割线
     *
     * @return mViewLine
     */
    /**
     * 底部分割线
     */
    var viewLine: View? = null
        protected set

    /**
     * 是否显示 分割线
     */
    private var isShowLine = true

    /**
     * 是否显示 标题栏
     */
    private var isShowBar = true

    /**
     * 是否显示状态栏
     */
    private var isShowStatusBar = true

    override fun setShowStatusBar(isShowStatusBar: Boolean): BaseToolBar {
        this.isShowStatusBar = isShowStatusBar
        return this
    }

    /**
     * 创建 toolbar
     */
    override fun createToolBar(activity: Activity?): BaseToolBar {
        this.activity = WeakReference(activity)
        val linearLayout = LinearLayout(activity)
        linearLayout.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )
        linearLayout.orientation = LinearLayout.VERTICAL
        if (isShowStatusBar) {
            initStatusBar(activity)
            val mStatusBarHeight = CoreConfig.get().statusBarHeight
            linearLayout.addView(
                statusBar,
                LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mStatusBarHeight)
            )
        }
        val toolBar = activity?.let { iniToolBar(it, linearLayout) }
        if (isShowBar) {
            linearLayout.addView(toolBar)
        }
        if (isShowLine) {
            viewLine = View(activity)
            viewLine!!.layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ScreenUtils.dip2px(activity, 1f)
            )
            viewLine!!.setBackgroundResource(R.color.toolbar_line_bg)
            linearLayout.addView(viewLine)
        }
        rootView = linearLayout
        return this
    }
    /***************** statusBar   */

    /**
     * 设置 状态栏底部的颜色
     *
     * @param statusBarColor
     */
    override fun setStatusColor(@ColorRes statusBarColor: Int): BaseToolBar {
        statusBar?.setBackgroundResource(statusBarColor)
        defStatusColor = statusBarColor
        return this
    }

    /**
     * 是否显示 toolBar
     *
     * @param isShowToolBar 是否显示
     * @return this
     */
    override fun setShowBar(isShowToolBar: Boolean): BaseToolBar {
        isShowBar = isShowToolBar
        return this
    }

    /**
     * 滑动渐变
     *
     * @param alpha 0 -1
     */
    open fun initScroll(alpha: Float) {
        statusBar?.alpha = alpha
        viewLine?.alpha = alpha
    }

    /**
     * 初始化状态栏  底部的view
     *
     * @param activity
     */
    fun initStatusBar(activity: Activity?) {
        statusBar = View(activity)
        statusBar?.setBackgroundResource(defStatusColor)
    }

    /**
     * 是否显示分割线
     *
     * @param isShowLine
     */
    override fun showLine(isShowLine: Boolean): IToolBar {
        viewLine?.visibility = View.GONE
        if (isShowLine) {
            viewLine?.visibility = View.VISIBLE
        }
        this.isShowLine = isShowLine
        return this
    }

    /**
     * 设置分割线颜色
     *
     * @param color color id
     */
    fun setLineColor(color: Int) {
        viewLine?.setBackgroundResource(color)
    }
}