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
    private var activity: Activity? = null

    /**
     * 是否显示 状态栏 背景
     */
    private var isShowStatusBar = true

    /**
     * 是否显示 ToolBar
     */
    private var isShowToolBar = true

    /**
     * 默认白色
     */
    @ColorRes
    private var statusBarColor = R.color.white
    fun setStatusBarColor(@ColorRes statusBarColor: Int): IToolBarBuilder {
        this.statusBarColor = statusBarColor
        return this
    }

    fun setActivity(activity: Activity?): IToolBarBuilder {
        this.activity = activity
        return this
    }

    fun setShowStatusBar(isShowStatusBar: Boolean): IToolBarBuilder {
        this.isShowStatusBar = isShowStatusBar
        return this
    }

    fun setShowToolBar(showToolBar: Boolean): IToolBarBuilder {
        isShowToolBar = showToolBar
        return this
    }

    /**
     * @param clss 类型
     * @return
     */
    fun create(iCreateToolbar: ICreateToolbar): IToolBar? {
        try {
            var toolBar = iCreateToolbar.createTooBar()
            toolBar!!.setShowStatusBar(isShowStatusBar)
                    .setShowBar(isShowToolBar)
                    .setDefStatusColor(statusBarColor)
                    .createToolBar(activity)
            return toolBar
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}