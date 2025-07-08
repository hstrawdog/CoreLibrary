package com.easy.example.ui.web

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.view.View
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.widget.TextView
import com.easy.core.listenner.WebLoadListener
import com.easy.core.ui.base.BaseFrameLayoutActivity
import com.easy.core.ui.web.BaseWebFragment
import com.easy.core.utils.log.LogUtils
import com.easy.example.R
import org.json.JSONObject


/**
 * @Author : huangqiqiang
 * @Package : com.easy.core.app.ui.web
 * @FileName :   WebActivity
 * @Date : 2019/8/5 0005  下午 7:52
 * @Email :  qiqiang213@gmail.com
 * @Describe :
 */
class WebActivity : BaseFrameLayoutActivity() {


    override fun getLayoutViewId():Int {
        return R.layout.activity_web
    }

    override fun initConfig() {
        super.initConfig()
        rootViewImpl.iToolBarBuilder.showStatusBar = false
        rootViewImpl.iToolBarBuilder.showToolBar = false
    }

    override fun initView() {
        var url = intent.extras?.getString(URL, "https://www.baidu.com/")
        var title = intent.extras?.getString(TITLE, "网页")
        url = if (url == null) "https://www.baidu.com/" else url
        title = if (title == null) "网页" else title
        val mBaseFragment = BaseWebFragment.instantiate(this, title, url)

//        mBaseFragment.webView?.addJavascriptInterface(JsBridge( mBaseFragment.webView!!), "imp") // 或 "r.default"
//        mBaseFragment.webView?.addJavascriptInterface(JsBridge(mBaseFragment.webView!!), "imp") // 或 "r.default"
//        mBaseFragment.webView?.addJavascriptInterface(JsBridge(mBaseFragment.webView!!), "getContent") // 或 "r.default"
//        mBaseFragment.webView?.addJavascriptInterface(JsBridge( mBaseFragment.webView!!), "bridgeToWeb") // 或 "r.default"
//        mBaseFragment.webView?.addJavascriptInterface(JsBridge( mBaseFragment.webView!!), "AMapAndroidLoc") // 或 "r.default"
//        mBaseFragment.webView?.addJavascriptInterface(JsBridge( mBaseFragment.webView!!), "r_default") // 或 "r.default"
//        mBaseFragment.webView?.addJavascriptInterface(JsBridge( mBaseFragment.webView!!), "r.default") // 或 "r.default"

        mBaseFragment.webView?.addJavascriptInterface(JsInterface(), "imp")
        mBaseFragment.webView?.addJavascriptInterface(GetContent(), "bridgeToWeb")

        findViewById<TextView>(R.id.tv_fail).setOnClickListener {
            mBaseFragment.url?.let { it1 -> mBaseFragment.webView?.loadUrl(it1) }
            findViewById<TextView>(R.id.tv_fail).visibility = View.GONE
            mBaseFragment.webView?.visibility = View.VISIBLE
        }



        mBaseFragment.webLoadListener = object : WebLoadListener {
            override fun onPageStarted(url:String?, favicon:Bitmap?) {


            }

            override fun onPageFinished(url:String?) {
                var json =
                    "{\"data\":{\"build\":\"10299\",\"confidence\":0.0,\"contentCode\":\"sky-yddk\",\"contentId\":\"42\",\"contentName\":\"移动签到\",\"contentType\":\"app\",\"img\":[],\"latitude\":0.0,\"level\":0,\"longitude\":0.0,\"moduleCode\":\"other\",\"organCode\":\"cass\",\"organId\":\"05f55be45ce541f4850ee2f4b36c7d60\",\"platformCode\":\"\",\"state\":2,\"status\":0,\"token\":\"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJjZXNoaXJlbnl1YW4yMSIsImN0diI6IiIsImRpZCI6ImZmZmZmZmZmLWIwYzEtYjIwMS1mZmZmLWZmZmZlZjA1YWM0YSIsImR0eSI6Im1vYmlsZSIsImV4cCI6MTc1MTAxNTAwNSwiaWF0IjoxNzUwODM1MDA1LCJpc3MiOiJodGltZWFwcCIsImp0aSI6ImE1NjQyYzM1NWRkYTQ2NDBhZmQ4ZjFhMTI2ODNhOTI2IiwibmJmIjoxNzUwODM1MDA1LCJvcmciOiIwNWY1NWJlNDVjZTU0MWY0ODUwZWUyZjRiMzZjN2Q2MCIsInVpZCI6ImQyYmVlZTNjN2JjNjQ0MzBiZmFjNjVjM2I0OWIzZTI0IiwidW5hIjoi5rWL6K-V5Lq65ZGYMjEifQ.2oMh42Zd8uDS2IUdG3Cy7Lna6_7tLHR843fW1aAp3Bg\",\"userCorpOrganId\":\"\",\"version\":\"3.8.11\"},\"reqid\":\"\",\"type\":\"common\"}"
                val url = "javascript:bridgeToWeb(" + { json } + ")"
                val escapedJson = JSONObject.quote(json.toString()) // 自动加引号并转义
//                mBaseFragment.webView?.loadUrl(url)
                val js = "javascript:window.bridge && window.bridge.callHandler('bridgeToWeb', ${escapedJson.toString()})"
                mBaseFragment.webView?.evaluateJavascript(js, null)

//                mBaseFragment.webView?.loadUrl(url)
//              mBaseFragment.webView?.loadUrl("javascript:window.bridge.callHandler('bridgeToWeb', '${json}')")

            }

            override fun onError(url:String?) {
                findViewById<TextView>(R.id.tv_fail).visibility = View.VISIBLE
                mBaseFragment.webView?.visibility = View.GONE

            }

        }

        addOrShowFragment(mBaseFragment, R.id.fl_layout)
    }

    override fun onBackPressed() {
        if (currentFragment != null) {
            if (!(currentFragment as BaseWebFragment).onBackPressed()) {
                super.onBackPressed()
            }
        }
    }

    override fun onActivityResult(requestCode:Int, resultCode:Int, data:Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    class JsBridge(var webView:WebView) {
        @JavascriptInterface
        fun ccworkGetUserInfo(callback:String):String {
            // 模拟数据
            val json = "{\"status\":1,\"result\":{\"userId\":\"10001\",\"name\":\"小明\"}}"
            val js = "$callback($json);"
            webView.post {
                webView.evaluateJavascript(js, null)
            }
            return json
        }

        @JavascriptInterface
        fun ccworkGetUserInfo(callback:String, a:String):String {
            // 模拟数据
            val json = "{\"status\":1,\"result\":{\"userId\":\"10001\",\"name\":\"小明\"}}"
            val js = "$callback($json);"
            webView.post {
                webView.evaluateJavascript(js, null)
            }
            return json
        }

        @JavascriptInterface
        fun ccworkGetUserInfo(callback:String, a:String, b:String):String {
            // 模拟数据
            val json = "{\"status\":1,\"result\":{\"userId\":\"10001\",\"name\":\"小明\"}}"
            val js = "$callback($json);"
            webView.post {
                webView.evaluateJavascript(js, null)
            }
            return json
        }

        @JavascriptInterface
        fun ccworkGetUserInfo() {
            // 模拟数据
            val json = "{\"status\":1,\"result\":{\"userId\":\"10001\",\"name\":\"小明\"}}"

        }
    }


    class GetContent {
        @JavascriptInterface
        fun onHashChangeEvent() {
            LogUtils.e("GetContent", "onHashChangeEvent")
        }

        @JavascriptInterface
        fun onGetHtmlContent(str:String?) {
            LogUtils.e("GetContent", str)
        }
    }

    class JsInterface() {

        @JavascriptInterface
        fun invoke(str:String?, str2:String?, str3:String?) {
            LogUtils.e("GetContent", "invoke")
        }

        @JavascriptInterface
        fun invoke(str:String?, str2:String?) {
            LogUtils.e("GetContent", "invoke")
        }

        @JavascriptInterface
        fun invokeAndReturn(str:String, str2:String, str3:String?):String {
            LogUtils.e("GetContent", "invokeAndReturn")
            var str = str
            var str2 = str2
            if (str == "com.inspur.gsp.imp.framework.plugin.GloSessionService" && str2 == "getClose") {
                str = "com.inspur.imp.plugin.app.AppService"
                str2 = "close"
            } else if (str == "com.inspur.gsp.imp.framework.plugin.GloSessionService" && str2 == "downloadFile") {
                str = "com.inspur.imp.plugin.filetransfer.FileTransferService"
                str2 = "downloadFile"
            }
            return ""
        }

        @JavascriptInterface
        fun invokeAndReturn(str:String?, str2:String?):String {
            LogUtils.e("GetContent", "invokeAndReturn")
            return ""
        }
    }

    companion object {
        const val URL = "URL"
        const val TITLE = "TITLE"
        fun open(context:Activity, url:String?, title:String?) {
            val starter = Intent(context, WebActivity::class.java)
            starter.putExtra(URL, url)
            starter.putExtra(TITLE, title)
            context.startActivityForResult(starter, -1)
        }
    }
}