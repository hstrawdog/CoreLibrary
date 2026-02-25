package com.easy.core.ui.web

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.widget.ProgressBar
import com.easy.core.R
import com.easy.core.listenner.ScriptInterface
import com.easy.core.listenner.WebLoadListener
import com.easy.core.ui.base.BaseFragment
import com.easy.core.utils.AppTool
import com.easy.core.utils.data.DataUtils
import com.easy.core.utils.log.LogUtils


/**
 * @Author : huangqiqiang
 * @Package : com.qi.core.ui
 * @FileName :   BaseWebFragment
 * @Date : 2019/3/4 0004  下午 4:22
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
open class BaseWebFragment : BaseFragment() {
    /**
     *  进度条
     */
    var progressBar:ProgressBar? = null

    /**
     *  进度条颜色
     */
    var progressBarColor:ColorStateList? = null

    /**
     * WebView
     */
    var webView:WebView? = null

    /**
     *  URL
     */
    var url:String? = null

    /**
     *  标题哦
     */
    private var title:CharSequence? = ""

    /**
     *  加载监听
     */
    var webLoadListener:WebLoadListener? = null

    /**
     * 交互脚本
     */
    var scriptInterface:ScriptInterface? = null

    /**
     *  布局Id
     */
    override fun getLayoutViewId():Int {
        return R.layout.fragment_web
    }

    /**
     *  标题栏
     */
    var showToolBar = true

    /**
     *  状态栏
     */
    var showStatusBar = true

    /**
     *  是否可以返回到上一个网页
     */
    var isGoBackWebView = true

    var webChromeClient:WebChromeClient = object : WebChromeClient() {
        override fun onReceivedTitle(view:WebView, title:String?) {
            super.onReceivedTitle(view, title)

            title?.let {
                if (this@BaseWebFragment.title.isNullOrEmpty()) {
                    iToolBar?.setToolbarTitle(this@BaseWebFragment.title)
                }
            }
        }

        override fun onConsoleMessage(cm:ConsoleMessage?):Boolean {
            LogUtils.e { "WebViewConsole"+ cm?.message() + " -- From line " + cm?.lineNumber() + " of " + cm?.sourceId() };
            return super.onConsoleMessage(cm)

        }

        override fun onShowCustomView(view:View?, callback:CustomViewCallback?) {
            super.onShowCustomView(view, callback)

        }

        override fun onProgressChanged(view:WebView, newProgress:Int) {
            if (newProgress >= 5) {
                progressBar?.progress = newProgress
            }
            super.onProgressChanged(view, newProgress)
        }
    }


    override fun onPause() {
        super.onPause()
        webView?.onPause()
    }

    override fun onResume() {
        super.onResume()
        webView?.onResume()
    }

    override fun initConfig() {
        super.initConfig()
        rootViewImpl.iToolBarBuilder.showToolBar = arguments?.getBoolean(getString(R.string.key_showToolBar), true) == true
        rootViewImpl.iToolBarBuilder.showLine = arguments?.getBoolean(getString(R.string.key_showToolBar), true) == true
        rootViewImpl.iToolBarBuilder.showStatusBar = arguments?.getBoolean(getString(R.string.key_showstatusBar), true) == true
    }

    @SuppressLint("JavascriptInterface")
    override fun initView() {
        webView = findViewById(R.id.web_view) as WebView?
        progressBar = findViewById(R.id.pb_progressbar) as ProgressBar
        if (DataUtils.checkUnNull(progressBarColor)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                progressBar?.indeterminateTintList = progressBarColor
            }
        }

        //先加载5%，以使用户觉得界面没有卡死
        progressBar?.max = 100
        progressBar?.progress = 5
        initWebViewSettings()
        webView?.webViewClient = webViewClient

        // 标识 为Android的 js 支持 对象是activity
        webView?.webChromeClient = webChromeClient

        webView?.let {
           it.setWebChromeClient(InsideWebChromeClient(it, it.parent as ViewGroup))
        }
        if (Build.VERSION.SDK_INT >= 21) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            WebView.setWebContentsDebuggingEnabled(true);
        }



        scriptInterface?.let {
            webView?.addJavascriptInterface(it, "android")
        }
        url = getBundleString(getString(R.string.key_url))
        title = getBundleString(getString(R.string.key_title))

        requireActivity().title = title
        iToolBar?.setToolbarTitle(title)
        url?.let {
            webView?.loadUrl(it)
        }
    }

    private fun initWebViewSettings() {
        val settings = webView?.settings
        settings?.run {
            val originalUA = userAgentString
            //Mozilla/5.0 (Linux; Android 12; JAD-AL50 Build/HUAWEIJAD-AL50; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/114.0.5735.196 Mobile Safari/537.36/emmcloud/ccwork/1.0.
            userAgentString = "$originalUA/emmcloud/ccwork/3.8.1"
            // 白色背景
            webView?.setBackgroundColor(Color.TRANSPARENT)
            webView?.setBackgroundResource(R.color.white)
            webView?.overScrollMode = View.OVER_SCROLL_NEVER
            webView?.isNestedScrollingEnabled = false // 默认支持嵌套滑动
            // 设置自适应屏幕，两者合用
            useWideViewPort = true
            loadWithOverviewMode = true
            // 是否支持缩放，默认为true
            setSupportZoom(false)
            // 是否使用内置的缩放控件
            builtInZoomControls = false
            // 是否显示原生的缩放控件
            displayZoomControls = false
            // 设置文本缩放 默认 100
            textZoom = 100
            // 是否保存密码
            savePassword = false
            // 是否可以访问文件
            allowFileAccess = true
            // 是否支持通过js打开新窗口
            javaScriptCanOpenWindowsAutomatically = true
            //启用或禁用 JavaScript 的执行
            javaScriptEnabled = true
            // 是否支持自动加载图片
            loadsImagesAutomatically = true

            blockNetworkImage = false
            // 设置编码格式
            defaultTextEncodingName = "utf-8"

            layoutAlgorithm = WebSettings.LayoutAlgorithm.NORMAL
            // 是否启用 DOM storage API
            domStorageEnabled = true
            // 是否启用 database storage API 功能
            databaseEnabled = true
            // 配置当安全源试图从不安全源加载资源时WebView的行为
            mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            // 设置缓存模式
            cacheMode = WebSettings.LOAD_NO_CACHE

        }
    }

    private class InsideWebChromeClient(var webView:WebView, var mFrameLayout:ViewGroup) : WebChromeClient() {
        var mCustomView:View? = null
        var mCustomViewCallback:CustomViewCallback? = null

        override fun getVideoLoadingProgressView():View? {
            return super.getVideoLoadingProgressView()
        }

        override fun onShowCustomView(view:View, callback:CustomViewCallback) {
            super.onShowCustomView(view, callback)
            if (mCustomView != null) {
                callback.onCustomViewHidden()
                return
            }
            mCustomView = view
            mFrameLayout.addView(mCustomView)
            mCustomViewCallback = callback
            webView.setVisibility(View.GONE)
            Handler().postDelayed({
                val js = "javascript: var v=document.getElementsByTagName('video')[0]; " + "v.play(); "
                webView.loadUrl(js)
            }, 100)
        }

        override fun onHideCustomView() {
            webView.setVisibility(View.VISIBLE)
            if (mCustomView == null) {
                return
            }
            mCustomView?.setVisibility(View.GONE)
            mFrameLayout.removeView(mCustomView)
            mCustomViewCallback!!.onCustomViewHidden()
            mCustomView = null
            super.onHideCustomView()
        }
    }

    //处理 android err_unknown_url_scheme异常
    val webViewClient:WebViewClient = object : WebViewClient() {


        override fun shouldOverrideUrlLoading(view:WebView?, url:String?):Boolean {
            LogUtils.e { " shouldOverrideUrlLoading :    ${url.toString()}" }
            return super.shouldOverrideUrlLoading(view, url)
        }
        override fun shouldOverrideUrlLoading(view:WebView, request:WebResourceRequest):Boolean {
            LogUtils.e { " shouldOverrideUrlLoading2 :    ${url.toString()}" }
            if (URLUtil.isNetworkUrl(request.url.toString())) {
                return false
            }
            if (AppTool.appInstalledOrNot(activity, request.url.toString())) {
                val intent = Intent(Intent.ACTION_VIEW, request.url)
                startActivity(intent)
            } else { // do something if app is not installed
            }
            return super.shouldOverrideUrlLoading(view, request)
        }

        override fun shouldInterceptRequest(view:WebView, request:WebResourceRequest):WebResourceResponse? {
            LogUtils.e { " shouldInterceptRequest :    ${request.url.toString()}" }
//            if (request.url.toString().toLowerCase().contains("/favicon.ico")) {
//                try {
//                    return WebResourceResponse("image/png", null, null)
//                } catch (e:Exception) {
//                    e.printStackTrace()
//                }
//            }
            return super.shouldInterceptRequest(view, request) //request.getUrl()
        }

        override fun onPageStarted(view:WebView?, url:String?, favicon:Bitmap?) {
            LogUtils.e { " onPageStarted :    ${url.toString()}" }
            super.onPageStarted(view, url, favicon)
            progressBar?.visibility = View.VISIBLE
            if (null != webLoadListener) {
                webLoadListener?.onPageStarted(url, favicon)
            }
        }

        override fun onPageFinished(view:WebView, url:String) {
            LogUtils.e { " onPageFinished :    ${url.toString()}" }
            super.onPageFinished(view, url)
            progressBar?.visibility = View.GONE
            if (null != webLoadListener) {
                webLoadListener?.onPageFinished(url)
            }
        }

        override fun onReceivedError(view:WebView, errorCode:Int, description:String?, failingUrl:String?) {
            super.onReceivedError(view, errorCode, description, failingUrl) // 断网或者网络连接超时
            LogUtils.e { " onReceivedError :    ${url.toString()}  errorCode: $errorCode  description: $description  failingUrl: $failingUrl" }
            if (errorCode == ERROR_HOST_LOOKUP || errorCode == ERROR_CONNECT || errorCode == ERROR_TIMEOUT) {
                view.loadUrl("about:blank");// 避免出现默认的错误界面
                webLoadListener?.onError(failingUrl)
            }
        }

        override fun onReceivedError(view:WebView, request:WebResourceRequest, error:WebResourceError) {
            super.onReceivedError(view, request, error)
            LogUtils.e { " onReceivedError :    ${request.url.toString()}  errorCode: ${error.errorCode }                 ${error.description }" }
            progressBar?.visibility = View.GONE

        }
    }


    fun onBackPressed():Boolean {
        if (isGoBackWebView) {
            webView?.run {
                return if (canGoBack() && visibility == View.VISIBLE) {
                    goBack()
                    true
                } else {
                    false
                }
            }
        }
        return false
    }

    override fun onActivityResult(requestCode:Int, resultCode:Int, data:Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }


    override fun onRequestPermissionsResult(requestCode:Int, permissions:Array<out String>, grantResults:IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

    }

    @JvmOverloads
    fun loadUrl(url:String, clear:Boolean = true) {
        webView?.run {
            if (clear) {
                clearHistory()
            }
            loadUrl(url)
        }
    }

    companion object {
        fun instantiate(context:Context, title:String?, url:String?, scriptInterface:ScriptInterface? = null, showToolBar:Boolean = true, showstatusBar:Boolean = true):BaseWebFragment {
            val baseWebFragment = BaseWebFragment()
            val bundle = Bundle()
            bundle.putString(context.getString(R.string.key_url), url)
            bundle.putString(context.getString(R.string.key_title), title)
            bundle.putBoolean(context.getString(R.string.key_showToolBar), showToolBar)
            bundle.putBoolean(context.getString(R.string.key_showstatusBar), showstatusBar)
            baseWebFragment.arguments = bundle
            baseWebFragment.scriptInterface = (scriptInterface)
            return baseWebFragment
        }
    }
};