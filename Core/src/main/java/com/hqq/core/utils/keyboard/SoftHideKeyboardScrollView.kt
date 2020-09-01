package com.hqq.core.utils.keyboard

import android.content.Context
import android.graphics.Rect
import android.view.View

/**
 * @Author : huangqiqiang
 * @Package : com.core.library.utils
 * @FileName :   SoftHideKeyboardScrollView
 * @Date : 2018/7/9 0009  下午 2:52
 * @Email :  qiqiang213@gmail.com
 * @Descrive :
 * SoftHideKeyboardRedraw   重绘 view
 * SoftHideKeyboardScrollView  保留一部分view 被键盘遮挡效果是类似的
 */
object SoftHideKeyboardScrollView {
    /**
     * 保持登录按钮始终不会被覆盖
     * https://www.jianshu.com/p/fd46d6924b78
     * @param root  根布局
     * @param subView  不被遮挡的 空
     */
    fun keepLoginBtnNotOver(root: View, subView: View) {
        root.viewTreeObserver.addOnGlobalLayoutListener {
            val rect = Rect()
            // 获取root在窗体的可视区域
            root.getWindowVisibleDisplayFrame(rect)
            // 获取root在窗体的不可视区域高度(被其他View遮挡的区域高度)
            val rootInvisibleHeight = root.rootView.height - rect.bottom
            // 若不可视区域高度大于200，则键盘显示,其实相当于键盘的高度
            if (rootInvisibleHeight > 200) {
                // 显示键盘时
                val srollHeight = rootInvisibleHeight - (root.height - subView.height) - getNavigationBarHeight(root.context)
                if (srollHeight > 0) {
                    root.scrollTo(0, srollHeight)
                }
            } else {
                // 隐藏键盘时
                root.scrollTo(0, 0)
            }
        }
    }

    /**
     * 获取底部虚拟按键高度
     *
     * @return
     */
    fun getNavigationBarHeight(context: Context): Int {
        var navigationBarHeight = 0
        val rs = context.resources
        val id = rs.getIdentifier("navigation_bar_height", "dimen", "android")
        if (id > 0 && checkDeviceHasNavigationBar(context)) {
            navigationBarHeight = rs.getDimensionPixelSize(id)
        }
        return navigationBarHeight
    }

    /**
     * 判断是否有虚拟底部按钮
     *
     * @return
     */
    fun checkDeviceHasNavigationBar(context: Context): Boolean {
        var hasNavigationBar = false
        val rs = context.resources
        val id = rs.getIdentifier("config_showNavigationBar", "bool", "android")
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id)
        }
        try {
            val systemPropertiesClass = Class.forName("android.os.SystemProperties")
            val m = systemPropertiesClass.getMethod("get", String::class.java)
            val navBarOverride = m.invoke(systemPropertiesClass, "qemu.hw.mainkeys") as String
            if ("1" == navBarOverride) {
                hasNavigationBar = false
            } else if ("0" == navBarOverride) {
                hasNavigationBar = true
            }
        } catch (e: Exception) {
        }
        return hasNavigationBar
    }
}