package com.example.accessibility

import android.accessibilityservice.AccessibilityService
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Build
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import android.view.accessibility.AccessibilityWindowInfo
import androidx.annotation.RequiresApi
import com.hqq.core.utils.log.LogUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import kotlin.collections.ArrayList

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.accessibility
 * @Date : 11:29
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class TestAccessibilityService : AccessibilityService() {

    var set1 = ArrayList<String>()
    var set2 = ArrayList<String>()
    var set3 = ArrayList<String>()
    var set4 = ArrayList<String>()
    var set5 = ArrayList<String>()

    var mainSearchId = "com.box.art:id/tv_search_tips"
    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        LogUtils.e("onAccessibilityEvent")

        when (event.eventType) {
            AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED -> {
                // 打开新窗口
                set1.add(event.className.toString())
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    openNewActivity(event)
                }
            }
            AccessibilityEvent.TYPE_VIEW_CLICKED -> {
                // 当View被点击时发送此事件。
                set2.add(event.className.toString())

                var source = event.source
                if (source != null) {
                    var js = JSONObject()
                    recursionTravelView(source, js)
                    LogUtils.e(" ${js.toString()}")
                }


            }
            AccessibilityEvent.TYPE_VIEW_LONG_CLICKED -> {
                // 当View被长按时发送此事件。
                set3.add(event.className.toString())

            }
            AccessibilityEvent.TYPE_VIEW_FOCUSED -> {
                // View获取到焦点时发送此事件。。
                set4.add(event.className.toString())

            }
            AccessibilityEvent.TYPE_VIEW_SCROLLED -> {
                // 当View滑动时发送此事件。。
                set5.add(event.className.toString())
                when (event.className) {
                    "androidx.recyclerview.widget.RecyclerVie" -> {
                        //监听到 列表
                        for (window in windows) {
                            if (window.type == AccessibilityWindowInfo.TYPE_APPLICATION) {
                                var nodeInfo = window.root
                                if (nodeInfo != null) {
                                    // 迭代View
                                    LogUtils.e("--RecyclerVie-----------  ${nodeInfo.childCount}")
                                    var js = JSONObject()
                                    recursionTravelView(nodeInfo, js)
                                    LogUtils.e(js.toString())
                                }
                            }

                        }

                    }
                }
            }

        }
        LogUtils.e("打开新窗口 ------   $set1  ")
        LogUtils.e("当View被点击时发送此事件------   $set2  ")
        LogUtils.e("当View被长按时发送此事件------   $set3  ")
        LogUtils.e("View获取到焦点时发送此事件------   $set4  ")
        LogUtils.e("当View滑动时发送此事件------   $set5  ")

    }


    var isFirst = true

    @RequiresApi(Build.VERSION_CODES.N)
    private fun openNewActivity(event: AccessibilityEvent) {
        when (event.className) {
            "com.ibox.nft.app.activity.IBoxMainActivity" -> {
                // 打开首页
                for (window in windows) {
                    if (window.type == AccessibilityWindowInfo.TYPE_APPLICATION && window.title?.equals("主页") == true) {
                        var nodeInfo = window.root
                        if (nodeInfo != null) {
                            // 迭代View
//                            LogUtils.e("--IBoxMainActivity-----------  ${nodeInfo.childCount}")
//                            var js = JSONObject()
//                            recursionTravelView(nodeInfo, js)
//                            LogUtils.e(js.toString())
                            nodeInfo.findAccessibilityNodeInfosByViewId(mainSearchId)[0]?.parent?.parent?.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                            isFirst = true
                        }

                    }

                }

            }
            "com.ibox.nft.art.activity.SearchMainActivity" -> {
                // 需要复制的内容
                (getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager).setPrimaryClip(ClipData.newPlainText("Label", "关羽"))

                for (window in windows) {
                    if (window.type == AccessibilityWindowInfo.TYPE_APPLICATION && isFirst) {
                        var nodeInfo = window.root
                        if (nodeInfo != null) {
                            // 将内容复制到搜索款
                            nodeInfo.findAccessibilityNodeInfosByViewId("com.box.art:id/et_keywords_input")[0].apply {
                                if (text == "关羽") {
                                } else {
                                    performAction(AccessibilityNodeInfo.ACTION_FOCUS)
                                    performAction(AccessibilityNodeInfo.ACTION_PASTE)
                                }
                            }
                            CoroutineScope(Dispatchers.IO).launch {
                                delay(200)


                                nodeInfo.findAccessibilityNodeInfosByViewId("com.box.art:id/tv_operate_btn")[0].apply {
                                    performAction(AccessibilityNodeInfo.ACTION_FOCUS)
                                    performAction(AccessibilityNodeInfo.ACTION_CLICK)
                                }
                            }

                            isFirst = false
                        }

                    } else {
                        LogUtils.e(" " + window.type)
                        var nodeInfo = window.root
                        if (nodeInfo != null) {
                            // 迭代View
                            LogUtils.e("--RecyclerVie-----------  ${nodeInfo.childCount}")
                            var js = JSONObject()
                            recursionTravelView(nodeInfo, js)
                            LogUtils.e(js.toString())
                        }
                    }

                }

            }

        }


    }

    private fun recursionTravelView(nodeInfo: AccessibilityNodeInfo, js: JSONObject) {
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

    override fun onInterrupt() {
        LogUtils.e("onInterrupt")

    }


    fun getRootWindow(): AccessibilityWindowInfo? {

        for (window in windows) {
            if (window.type == 4) {
                return window
            }
        }
        return null
    }

}