package com.hqq.core.toolbar

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import com.hqq.core.R
import com.hqq.core.utils.ScreenUtils
import java.lang.ref.WeakReference

/**
 * @Author : huangqiqiang
 * @Package : com.core.library.toolbar
 * @FileName :   BaseToolBar
 * @Date : 2018/11/15 0015  下午 7:26
 * @Descrive :
 * @Email :  qiqiang213@gmail.com
 * -  第一层 背景    toolBar
 * -  第二层 状态栏  statusBar
 * -  第三层 标题栏  TitleBar
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
    protected var defStatusColor = R.color.toolbar_status_color

    /**
     * 获取状态 底部的View
     *
     * @return
     */
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
    protected var isShowLine = true

    /**
     * 是否显示 标题栏
     */
    protected var isShowBar = true

    /**
     * 是否显示状态栏
     */
    protected var isShowStatusBar = true

    override fun setShowStatusBar(showStatusBar: Boolean): BaseToolBar {
        isShowStatusBar = showStatusBar
        return this
    }

    /**
     * 创建 toolbar
     */
    override fun createToolBar(activity: Activity?): BaseToolBar {
        this.activity = WeakReference(activity)
        val linearLayout = LinearLayout(activity)
        linearLayout.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        linearLayout.orientation = LinearLayout.VERTICAL
        if (isShowStatusBar) {
            initStatusBar(this.activity!!.get())
            val mStatusBarHeight = ScreenUtils.getStatusBarHeight4Resources(activity)
            linearLayout.addView(statusBar, LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mStatusBarHeight))
        }

        // 目前不知道是否需要都执行 createToolBar
        val toolBar = iniToolBar(activity!!, linearLayout)
        if (isShowBar && toolBar != null) {
            linearLayout.addView(toolBar)
        }
        if (isShowLine) {
            viewLine = View(this.activity!!.get())
            viewLine!!.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ScreenUtils.dip2px(this.activity!!.get(), 1f))
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
     * @param colorInt
     */
    override fun setDefStatusColor(@ColorRes colorInt: Int): BaseToolBar {
        if (statusBar != null) {
            statusBar!!.setBackgroundResource(colorInt)
        } else {
            defStatusColor = colorInt
        }
        return this
    }

    override fun setShowBar(showBar: Boolean): BaseToolBar {
        isShowBar = showBar
        return this
    }

    /**
     * 滑动渐变
     *
     * @param alpha 0 -1
     */
    open fun initScroll(alpha: Float) {
        if (statusBar != null) {
            statusBar!!.alpha = alpha
        }
        if (viewLine != null) {
            viewLine!!.alpha = alpha
        }
    }

    /**
     * 初始化状态栏  底部的view
     *
     * @param activity
     */
    fun initStatusBar(activity: Activity?) {
        statusBar = View(activity)
        statusBar!!.setBackgroundResource(defStatusColor)
    }
    /***************** Toolbar   */
    /***************** Line   */


    /**
     * 是否显示分割线
     *
     * @param isShow isShow
     */
    fun showLine(isShow: Boolean) {
        if (isShow) {
            viewLine!!.visibility = View.VISIBLE
        } else {
            viewLine!!.visibility = View.GONE
        }
    }

    /**
     * 设置分割线颜色
     *
     * @param color color id
     */
    fun setLineColor(color: Int) {
        viewLine!!.setBackgroundResource(color)
    }
}