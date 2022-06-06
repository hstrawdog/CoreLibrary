package com.example.accessibility

import android.accessibilityservice.AccessibilityService
import android.os.Build
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import android.view.accessibility.AccessibilityWindowInfo
import com.hqq.core.utils.log.LogUtils
import org.json.JSONArray
import org.json.JSONObject

/**
 * @Author : huangqiqiang
 * @Package : com.example.accessibility
 * @Date : 15:35
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class JobAccessibilityService : AccessibilityService() {


    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        LogUtils.e("accessibilityEvent :   ${event.eventType}              ${event.className.toString()}")

        when (event.eventType) {
            AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED -> {
                // 打开新窗口
                App.accessibilityInterface?.onNewWindow(event)
            }
            AccessibilityEvent.TYPE_VIEW_CLICKED -> {
                // 当View被点击时发送此事件。
                App.accessibilityInterface?.onWindowClick(event)

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
            AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED -> {
                // 2048 WINDOW视图变化才进行对应操
                if (event.className.equals("android.webkit.WebView")
                ) {
                    val js = JSONObject()
                    recursionTravelView(event.source, js)
                    LogUtils.e("    " + js.toString())

                }
            }

        }


    }

    override fun onInterrupt() {
        LogUtils.e("com.kuaishou.nebula\"")
    }


    fun recursionTravelView(nodeInfo: AccessibilityNodeInfo, js: JSONObject) {
        var jsObject = JSONObject()
        jsObject.put("className", nodeInfo.className).put("text", nodeInfo.text).put("childCount", nodeInfo.childCount)
            .put("viewIdResourceName", nodeInfo.viewIdResourceName)
        if (nodeInfo.childCount > 0) {
            var jsArray = JSONArray()
            for (i in 0 until nodeInfo.childCount) {
                var ob = JSONObject()
                var no = nodeInfo.getChild(i)
                if (no != null) {
                    recursionTravelView(no, ob)
                    jsArray.put(ob)
                }
            }
            jsObject.put("child", jsArray)
        }

        js.put(nodeInfo.className.toString(), jsObject)
    }

}