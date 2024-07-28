/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */
package com.easy.core.album.utils

import android.content.Context

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @FileName: cn.huangqiqiang.halbum.utils.AlbumUtils.java
 * @author: 黄其强
 * @date: 2017-05-05 16:56
</描述当前版本功能> */
object AlbumUtils {
    /**
     * 防止连续点击跳转两个页面
     */
    var lastClickTime: Long = 0
    val isFastDoubleClick: Boolean
        get() {
            val time = System.currentTimeMillis()
            if (time - lastClickTime < 800) {
                return true
            }
            lastClickTime = time
            return false
        }

    fun isFastDoubleClick(times: Int): Boolean {
        val time = System.currentTimeMillis()
        if (time - lastClickTime < times) {
            return true
        }
        lastClickTime = time
        return false
    }

    fun isNull(vararg objects: Any?): Boolean {
        for (`object` in objects) {
            if (`object` == null) {
                return true
            }
        }
        return false
    }

    /**
     * dp2px
     */
    fun dip2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * 状态栏高度
     *
     * @param context
     * @return
     */
    fun getStatusBarHeight(context: Context): Int {
        var result = 0
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = context.resources.getDimensionPixelSize(resourceId)
        }
        return result
    }
}