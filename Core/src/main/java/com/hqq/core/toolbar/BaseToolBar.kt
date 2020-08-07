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
    protected var mDefStatusColor = R.color.toolbar_status_color

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
    protected var mActivity: WeakReference<Activity?>? = null

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
    protected var mIsShowLine = true

    /**
     * 是否显示 标题栏
     */
    protected var mIsShowBar = true

    /**
     * 是否显示状态栏
     */
    protected var mIsShowStatusBar = true
    override fun setShowStatusBar(showStatusBar: Boolean): BaseToolBar {
        mIsShowStatusBar = showStatusBar
        return this
    }

    /**
     * 创建 toolbar
     */
    override fun createToolBar(activity: Activity?): BaseToolBar {
        mActivity = WeakReference(activity)
        val linearLayout = LinearLayout(activity)
        linearLayout.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        linearLayout.orientation = LinearLayout.VERTICAL
        if (mIsShowStatusBar) {
            initStatusBar(mActivity!!.get())
            val mStatusBarHeight = ScreenUtils.getStatusBarHeight4Resources(activity)
            linearLayout.addView(statusBar, LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mStatusBarHeight))
        }

        // 目前不知道是否需要都执行 createToolBar
        val toolBar = iniToolBar(activity!!, linearLayout)
        if (mIsShowBar && toolBar != null) {
            linearLayout.addView(toolBar)
        }
        if (mIsShowLine) {
            viewLine = View(mActivity!!.get())
            viewLine!!.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ScreenUtils.dip2px(mActivity!!.get(), 1f))
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
    override fun setDefStatusColor(@ColorInt colorInt: Int): BaseToolBar {
        if (statusBar != null) {
            statusBar!!.setBackgroundColor(colorInt)
        } else {
            mDefStatusColor = colorInt
        }
        return this
    }

    override fun setShowBar(showBar: Boolean): BaseToolBar {
        mIsShowBar = showBar
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
        statusBar!!.setBackgroundResource(mDefStatusColor)
    }
    /***************** Toolbar   */
    /***************** Line   */
    /**
     * 需要在 createToolBar 之前调用才会生效
     * 这个方法会导致 mViewLine 是 null
     * 但是会优化布局
     * [.showLine]
     *
     * @param showLine
     */
    @Deprecated("")
    fun setShowLine(showLine: Boolean) {
        mIsShowLine = showLine
    }

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