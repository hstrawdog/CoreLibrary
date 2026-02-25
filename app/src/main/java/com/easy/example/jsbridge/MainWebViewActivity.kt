package com.easy.example.jsbridge

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.Button
import com.easy.core.utils.log.LogUtils
import com.easy.example.R
import com.easy.example.jsbridge.core.BridgeWebView
import com.easy.example.jsbridge.core.OnBridgeCallback
import com.google.gson.Gson

class MainWebViewActivity : Activity(), View.OnClickListener {
    private val TAG = "MainActivity"

    var webView:BridgeWebView? = null

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
        setContentView(R.layout.activity_main_webview)

        webView = findViewById<BridgeWebView>(R.id.webView) as BridgeWebView

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

//        webView!!.addJavascriptInterface(MainJavascriptInterface(webView!!.callbacks, webView), "WebViewJavascriptBridge")
        webView!!.setGson(Gson())
//        webView!!.loadUrl("http://117.73.18.229:9527/cc-app/#/pages/password/modify")
        webView!!.loadUrl("http://117.73.18.229:9527/cc-app/#/pages/attendence/sign?authCorpId=05f55be45ce541f4850ee2f4b36c7d60")



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
                "{\"data\":{\"build\":\"10299\",\"confidence\":0.0,\"contentCode\":\"sky-yddk\",\"contentId\":\"42\",\"contentName\":\"移动签到\",\"contentType\":\"app\",\"img\":[],\"latitude\":0.0,\"level\":0,\"longitude\":0.0,\"moduleCode\":\"other\",\"organCode\":\"cass\",\"organId\":\"05f55be45ce541f4850ee2f4b36c7d60\",\"platformCode\":\"\",\"state\":2,\"status\":0,\"token\":\"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJjZXNoaXJlbnl1YW4yMSIsImN0diI6IiIsImRpZCI6ImZmZmZmZmZmLWIwYzEtYjIwMS1mZmZmLWZmZmZlZjA1YWM0YSIsImR0eSI6Im1vYmlsZSIsImV4cCI6MTc1MTAxNTAwNSwiaWF0IjoxNzUwODM1MDA1LCJpc3MiOiJodGltZWFwcCIsImp0aSI6ImE1NjQyYzM1NWRkYTQ2NDBhZmQ4ZjFhMTI2ODNhOTI2IiwibmJmIjoxNzUwODM1MDA1LCJvcmciOiIwNWY1NWJlNDVjZTU0MWY0ODUwZWUyZjRiMzZjN2Q2MCIsInVpZCI6ImQyYmVlZTNjN2JjNjQ0MzBiZmFjNjVjM2I0OWIzZTI0IiwidW5hIjoi5rWL6K-V5Lq65ZGYMjEifQ.2oMh42Zd8uDS2IUdG3Cy7Lna6_7tLHR843fW1aAp3Bg\",\"userCorpOrganId\":\"\",\"version\":\"3.8.11\"},\"reqid\":\"\",\"type\":\"common\"}"
            webView!!.callHandler("bridgeToWeb",json, object : OnBridgeCallback {
                override fun onCallBack(p0:String?) {
                    LogUtils.e { " onCallBack :    ${p0.toString()}" }
                }
            })
        }
    }
}
