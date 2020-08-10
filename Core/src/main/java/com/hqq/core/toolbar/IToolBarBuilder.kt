package com.hqq.core.toolbar

import android.app.Activity
import androidx.annotation.ColorRes
import com.hqq.core.R

/**
 * @Author : huangqiqiang
 * @Package : com.core.library.toolbar
 * @FileName :   IToolBarBuilder
 * @Date : 2018/11/23 0023  下午 1:34
 * @Descrive :
 * @Email :  qiqiang213@gmail.com
 */
class IToolBarBuilder {
    private var mActivity: Activity? = null

    /**
     * 是否显示 状态栏 背景
     */
    private var mIsShowStatusBar = true

    /**
     * 是否显示 ToolBar
     */
    private var mIsShowToolBar = true

    /**
     * 默认白色
     */
    @ColorRes
    private var mStatusBarColor = R.color.white
    fun setStatusBarColor(@ColorRes statusBarColor: Int): IToolBarBuilder {
        mStatusBarColor = statusBarColor
        return this
    }

    fun setActivity(activity: Activity?): IToolBarBuilder {
        mActivity = activity
        return this
    }

    fun setShowStatusBar(isShowStatusBar: Boolean): IToolBarBuilder {
        mIsShowStatusBar = isShowStatusBar
        return this
    }

    fun setShowToolBar(showToolBar: Boolean): IToolBarBuilder {
        mIsShowToolBar = showToolBar
        return this
    }

    /**
     * @param clss 类型
     * @return
     */
    fun <T : IToolBar?> create(clss: Class<T?>): T? {
        try {
            val toolBar: T = clss.newInstance()!!
            toolBar!!.setShowStatusBar(mIsShowStatusBar)
                    .setShowBar(mIsShowToolBar)
                    .setDefStatusColor(mStatusBarColor)
                    .createToolBar(mActivity)
            return toolBar
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}