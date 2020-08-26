package com.hqq.core.toolbar

import android.app.Activity
import androidx.annotation.ColorRes
import com.hqq.core.R
import java.lang.ref.WeakReference

/**
 * @Author : huangqiqiang
 * @Package : com.core.library.toolbar
 * @FileName :   IToolBarBuilder
 * @Date : 2018/11/23 0023  下午 1:34
 * @Descrive :
 * @Email :  qiqiang213@gmail.com
 */
class IToolBarBuilder {
    var activity: WeakReference<Activity>? = null

    /**
     * 是否显示 状态栏 背景
     */
    var showStatusBar = true

    /**
     * 是否显示 ToolBar
     */
    var showToolBar = true

    /**
     * 默认白色
     */
    @ColorRes
    var statusBarColor = R.color.white

    /**
     * @param clss 类型
     * @return
     */
    fun create(iCreateToolbar: ICreateToolbar?): IToolBar? {
        var toolBar = iCreateToolbar?.createTooBar()
        toolBar?.let {
            it.setShowStatusBar(showStatusBar)
                    .setShowBar(showToolBar)
                    .setDefStatusColor(statusBarColor)
                    .createToolBar(activity?.get())
        }
        return toolBar
    }
}