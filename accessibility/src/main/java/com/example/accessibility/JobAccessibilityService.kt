package com.example.accessibility

import android.accessibilityservice.AccessibilityService
import android.os.Build
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityWindowInfo
import com.hqq.core.utils.log.LogUtils
import org.json.JSONObject

/**
 * @Author : huangqiqiang
 * @Package : com.example.accessibility
 * @Date : 15:35
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class JobAccessibilityService : AccessibilityService() {
    override fun onAccessibilityEvent(accessibilityEvent: AccessibilityEvent) {
        LogUtils.e("accessibilityEvent :            ${accessibilityEvent.className.toString()}"    )

        when (accessibilityEvent.eventType) {
            AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED -> {
                // 打开新窗口
            }
            AccessibilityEvent.TYPE_VIEW_CLICKED -> {
                // 当View被点击时发送此事件。

            }
            AccessibilityEvent.TYPE_VIEW_LONG_CLICKED -> {
                // 当View被长按时发送此事件。

            }
            AccessibilityEvent.TYPE_VIEW_FOCUSED -> {
                // View获取到焦点时发送此事件。。

            }
            AccessibilityEvent.TYPE_VIEW_SCROLLED -> {
                // 当View滑动时发送此事件。。
            }

        }


    }

    override fun onInterrupt() {}
}