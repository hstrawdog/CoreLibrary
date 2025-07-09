package com.easy.example.jsbridge

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.Button
import com.easy.core.utils.ToastUtils
import com.easy.core.utils.gson.GsonUtil
import com.easy.core.utils.log.LogUtils
import com.easy.example.R
import com.easy.example.jsbridge.core.BridgeHandler
import com.easy.example.jsbridge.core.BridgeWebView
import com.easy.example.jsbridge.core.OnBridgeCallback
import org.json.JSONException
import org.json.JSONObject


class MainWebView2Activity : Activity(), View.OnClickListener {
    private val TAG = "MainActivity"

    var webView:CustomWebView? = null

    var button:Button? = null

    var RESULT_CODE:Int = 0

    var mUploadMessage:ValueCallback<Uri?>? = null

    var mUploadMessageArray:ValueCallback<Array<Uri>>? = null

    internal class Location {
        var address:String? = null
    }

    internal class User {
        var name:String? = null
        var location:Location? = null
        var testStr:String? = null
    }

    override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_webview2)

        webView = findViewById<BridgeWebView>(R.id.webView) as CustomWebView

        button = findViewById<Button>(R.id.button) as Button

        button!!.setOnClickListener(this)


        webView!!.webChromeClient = object : WebChromeClient() {
            @Suppress("unused")
            fun openFileChooser(uploadMsg:ValueCallback<Uri?>?, AcceptType:String?, capture:String?) {
                this.openFileChooser(uploadMsg)
            }

            @Suppress("unused")
            fun openFileChooser(uploadMsg:ValueCallback<Uri?>?, AcceptType:String?) {
                this.openFileChooser(uploadMsg)
            }

            fun openFileChooser(uploadMsg:ValueCallback<Uri?>?) {
                mUploadMessage = uploadMsg
                pickFile()
            }

            override fun onShowFileChooser(webView:WebView, filePathCallback:ValueCallback<Array<Uri>>, fileChooserParams:FileChooserParams):Boolean {
                mUploadMessageArray = filePathCallback
                pickFile()
                return true
            }
        }
        webView!!.registerHandler("bridgeToNative", BridgeHandler { str, callBackFunction ->
            LogUtils.e(" bridgeToNative :    ${str.toString()}")
            var jSONObject = JSONObject(str);

            val jSONObject2 = JSONObject()
            var type = GsonUtil.getValue(str, "type")?.asString
            if (type == "WebHTTPRequest") {
                // jSONObject 中解析出接口数据  执行请求
                // 传递以下数据格式给页面
//                gotoRequest(jSONObject, callBackFunction, false);
                var result = JSONObject().apply {
                    put("code", "0000")
                    put("message", "获取免登录码成功")
                    put("data", JSONObject().apply {
                        put("code", "50f0e4ad-56d3-4975-b6bb-11e1dd8d6715")
                    })
                }
                jSONObject2.put("state", "1")
                jSONObject2.put("source", "network")
                jSONObject2.put("errormessage", "")
//                jSONObject2.put("result", result)
                jSONObject2.put("body", result)
                callBackFunction.onCallBack(jSONObject2.toString())
            } else if (type == "ccworkGetOrganizeRouter") {
                jSONObject2.put("status", 1)
                jSONObject2.put("errormessage", "getRouterResult")
                // ccwork 接口下发的登录地址 字段 loginServer
                jSONObject2.put("result", "http://117.73.18.229:81")
                callBackFunction.onCallBack(jSONObject2.toString())
            } else if (type == "ccworkGetUserInfo") {
                var result = JSONObject().apply {
                    put("userId", "4bd0eaad5f434b199a9f618231401838")
                    put("name", "测试人员22")
                }
                jSONObject2.put("status", 1)
                jSONObject2.put("errormessage", "")
                jSONObject2.put("result", result)
                callBackFunction.onCallBack(jSONObject2.toString())

            } else if (type == "ccworkGetGpsLocation") {

                var result = JSONObject().apply {
//                    put("lat", "39.908482") // 纬度：上海
//                    put("lng", "116.426593") // 经度：上海
                    put("lat", "26.019024") // 纬度：上海
                    put("lng", "119.223107") // 经度：上海
//                    put("addr", "中国上海市黄浦区人民广场")
//                    put("country", "中国")
//                    put("province", "上海市")
//                    put("city", "上海市")
//                    put("district", "黄浦区")
//                    put("street", "南京东路")
//                    put("streetNum", "300号")
//                    put("accuracy", 15.0f)
//                    put("course", 90.0f)
//                    put("speed", 0.0f)
                }
                jSONObject2.put("status", 1)
                jSONObject2.put("errormessage", str)
                jSONObject2.put("result", result)

                callBackFunction.onCallBack(jSONObject2.toString())
            } else if (type == "close") {
                ToastUtils.showToast("关闭界面")
            }

        })
//        webView!!.addJavascriptInterface(MainJavascriptInterface(webView!!.callbacks, webView), "WebViewJavascriptBridge")
//        webView!!.loadUrl("http://117.73.18.229:9527/cc-app/#/pages/password/modify")
//        webView!!.loadUrl("http://117.73.18.229:9527/cc-app/#/pages/attendence/sign?authCorpId=05f55be45ce541f4850ee2f4b36c7d60")
//        webView!!.loadUrl("http://117.73.18.229:9527/DreamMobile/dist/index.html#!/autoLoginUID/user/L3RlbXBsYXRlbGlzdC9Ub0RvTGlzdA==/111?authCorpId=05f55be45ce541f4850ee2f4b36c7d60")
//        webView!!.loadUrl("http://117.73.18.229:9527/DreamMobile/dist/index.html#!/autoLoginUID/user/L3RlbXBsYXRlbGlzdC9zb2NpZXR5VmFjYXRpb24=/111?authCorpId=05f55be45ce541f4850ee2f4b36c7d60")
//        webView!!.loadUrl("117.73.18.229:9527/cc-app/#/pages/attendence/sign?authCorpId=05f55be45ce541f4850ee2f4b36c7d60")
        webView!!.loadUrl("https://skycas.shangwenwan.com/cc-app/#/pages/password/modify")

    }


    /* JADX WARN: Removed duplicated region for block: B:74:0x01ea  */ /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    @Throws(JSONException::class)
    private fun gotoRequest(r15:JSONObject, r16:OnBridgeCallback, r17:Boolean) {/*
            Method dump skipped, instructions count: 606
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw UnsupportedOperationException("Method not decompiled: com.inspur.icity.web.Bridge2NativeManager.gotoRequest(org.json.JSONObject, com.inspur.icity.base.jsbridge.CallBackFunction, boolean):void")
    }

    fun pickFile() {
        val chooserIntent = Intent(Intent.ACTION_GET_CONTENT)
        chooserIntent.setType("image/*")
        startActivityForResult(chooserIntent, RESULT_CODE)
    }

    override fun onActivityResult(requestCode:Int, resultCode:Int, intent:Intent) {
        if (requestCode == RESULT_CODE) {
            if (null == mUploadMessage && null == mUploadMessageArray) {
                return
            }
            if (null != mUploadMessage && null == mUploadMessageArray) {
                val result = if (intent == null || resultCode != RESULT_OK) null else intent.data
                mUploadMessage!!.onReceiveValue(result)
                mUploadMessage = null
            }

            if (null == mUploadMessage && null != mUploadMessageArray) {
                val result = if (intent == null || resultCode != RESULT_OK) null else intent.data
                if (result != null) {
                    mUploadMessageArray!!.onReceiveValue(arrayOf(result))
                }
                mUploadMessageArray = null
            }
        }
    }

    override fun onClick(v:View) {
        if (button == v) {
            var json =
                "{\"data\":{\"build\":\"10299\",\"confidence\":0,\"contentCode\":\"sky-ysbg-qxjsq\",\"contentId\":\"25\",\"contentName\":\"请销假申请\",\"contentType\":\"app\",\"img\":[],\"latitude\":0,\"level\":0,\"longitude\":0,\"moduleCode\":\"other\",\"organCode\":\"cass\",\"organId\":\"05f55be45ce541f4850ee2f4b36c7d60\",\"platformCode\":\"\",\"state\":2,\"status\":0,\"token\":\"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJjZXNoaXJlbnl1YW4yMiIsImN0diI6IiIsImRpZCI6ImZmZmZmZmZmLWIwYzEtYjIwMS1mZmZmLWZmZmZlZjA1YWM0YSIsImR0eSI6Im1vYmlsZSIsImV4cCI6MTc1MTIwMzg3NCwiaWF0IjoxNzUxMDIzODc0LCJpc3MiOiJodGltZWFwcCIsImp0aSI6IjcyNzk2YTE2OWY4OTQ2ZGJhMzQ4MzZlN2U2Y2ZhODM4IiwibmJmIjoxNzUxMDIzODc0LCJvcmciOiIwNWY1NWJlNDVjZTU0MWY0ODUwZWUyZjRiMzZjN2Q2MCIsInVpZCI6IjRiZDBlYWFkNWY0MzRiMTk5YTlmNjE4MjMxNDAxODM4IiwidW5hIjoi5rWL6K-V5Lq65ZGYMjIifQ.Oa9iB5n2nHFWpVUMp7GHDFeni26MxQS39s0KuVgLytk\",\"userCorpOrganId\":\"\",\"version\":\"3.8.11\"},\"reqid\":\"\",\"type\":\"common\"}"
            webView!!.callHandler("bridgeToWeb", json, object : OnBridgeCallback {
                override fun onCallBack(p0:String?) {
                    LogUtils.e(" onCallBack :    ${p0.toString()}")
                }
            })
        }
    }
}
