package com.example.accessibility.kuaishou

import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import com.example.accessibility.AccessibilityInterface
import com.easy.core.utils.log.LogUtils
import kotlinx.coroutines.*
import org.json.JSONArray
import org.json.JSONObject

/**
 * @Author : huangqiqiang
 * @Package : com.example.accessibility.kuaishou
 * @Date : 14:36
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class KuaiShouAccessibility : AccessibilityInterface {
    override fun onNewWindow(accessibilityEvent: AccessibilityEvent) {

        when (accessibilityEvent.className.toString()) {
            "com.yxcorp.gifshow.HomeActivity" -> {
                LogUtils.e("打开首页  ")
                accessibilityEvent.source.findAccessibilityNodeInfosByText("去赚钱")[0].performAction(AccessibilityNodeInfo.ACTION_CLICK)

            }
            "android.webkit.WebView" -> {
                val js = JSONObject()
                recursionTravelView(accessibilityEvent.source, js)
                LogUtils.e("    " + js.toString())

            }
        }

        LogUtils.e("KuaiShouAccessibility       ${accessibilityEvent.className.toString()}")
    }

    override fun onWindowClick(accessibilityEvent: AccessibilityEvent) {

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