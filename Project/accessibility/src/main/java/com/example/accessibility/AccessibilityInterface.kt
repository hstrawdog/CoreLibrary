package com.example.accessibility

import android.view.accessibility.AccessibilityEvent

/**
 * @Author : huangqiqiang
 * @Package : com.example.accessibility
 * @Date : 11:23
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
interface AccessibilityInterface {
    /**
     *  打开了新的window
     * @param accessibilityEvent AccessibilityEvent
     */
    fun onNewWindow(accessibilityEvent: AccessibilityEvent)

    /**
     *  点击界面
     * @param accessibilityEvent AccessibilityEvent
     */
    fun onWindowClick(accessibilityEvent: AccessibilityEvent)
}