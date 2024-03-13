package com.easy.core.ui.web

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Bitmap
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
import com.easy.core.utils.DataUtils
import com.easy.core.utils.log.LogUtils

/**
 * @Author : huangqiqiang
 * @Package : com.qi.core.ui
 * @FileName :   BaseWebFragment
 * @Date : 2019/3/4 0004  下午 4:22
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
open class BaseWebFragment : BaseFragment() {
    /**
     *  进度条
     */
    var progressBar: ProgressBar? = null

    /**
     *  进度条颜色
     */
    var progressBarColor: ColorStateList? = null

    /**
     * WebView
     */
    var webView: WebView? = null

    /**
     *  URL
     */
    var url: String? = null

    /**
     *  标题哦
     */
    private var title: CharSequence? = ""

    /**
     *  加载监听
     */
    var webLoadListener: WebLoadListener? = null

    /**
     * 交互脚本
     */
    var scriptInterface: ScriptInterface? = null

    /**
     *  布局Id
     */
    override fun getLayoutViewId(): Int {
        return  R.layout.fragment_web
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

    var webChromeClient: WebChromeClient = object : WebChromeClient() {
        override fun onReceivedTitle(view: WebView, title: String?) {
            super.onReceivedTitle(view, title)

            title?.let {
                if (this@BaseWebFragment.title.isNullOrEmpty()) {
                    iToolBar?.setToolbarTitle(this@BaseWebFragment.title)
                }
            }
        }

        override fun onConsoleMessage(cm: ConsoleMessage?): Boolean {
            LogUtils.d("WebViewConsole", cm?.message() + " -- From line "
                    + cm?.lineNumber() + " of "
                    + cm?.sourceId() );
            return super.onConsoleMessage(cm)

        }
        override fun onShowCustomView(view: View?, callback: CustomViewCallback?) {
            super.onShowCustomView(view, callback)

        }

        override fun onProgressChanged(view: WebView, newProgress: Int) {
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
        scriptInterface?.let {
            webView?.addJavascriptInterface(it, "android")
        }
        url = getString(getString(R.string.key_url))
        title =   getString(getString(R.string.key_title))

        requireActivity().title = title
        iToolBar?.setToolbarTitle(title)
        url?.let {
            webView?.loadUrl(it)
        }
    }

    private fun initWebViewSettings() {
        val settings = webView?.settings
        settings?.run {
            setUserAgentString(userAgentString + "" + requireActivity().packageName)
            javaScriptEnabled = true
            javaScriptCanOpenWindowsAutomatically = true
            allowFileAccess = true
            useWideViewPort = true
            loadWithOverviewMode = true
            builtInZoomControls = true
            domStorageEnabled = true
            cacheMode = WebSettings.LOAD_NO_CACHE
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                displayZoomControls = false
            } // MIXED_CONTENT_ALWAYS_ALLOW：允许从任何来源加载内容，即使起源是不安全的；
            // MIXED_CONTENT_NEVER_ALLOW：不允许Https加载Http的内容，即不允许从安全的起源去加载一个不安全的资源；
            // MIXED_CONTENT_COMPATIBILITY_MODE：当涉及到混合式内容时，WebView 会尝试去兼容最新Web浏览器的风格。
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            }
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) { //提高渲染优先级
                setRenderPriority(WebSettings.RenderPriority.HIGH)
                savePassword = false
                pluginState = WebSettings.PluginState.ON
            }
        }
    }

    private class InsideWebChromeClient(var webView: WebView, var mFrameLayout: ViewGroup) : WebChromeClient() {
        var mCustomView: View? = null
        var mCustomViewCallback: CustomViewCallback? = null

        override fun getVideoLoadingProgressView(): View? {
            return super.getVideoLoadingProgressView()
        }

        override fun onShowCustomView(view: View, callback: CustomViewCallback) {
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
    val webViewClient: WebViewClient = object : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
            LogUtils.e(" shouldOverrideUrlLoading :    ${url.toString()}")
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

        override fun shouldInterceptRequest(view: WebView, request: WebResourceRequest): WebResourceResponse? {
            return super.shouldInterceptRequest(view, request) //request.getUrl()
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            progressBar?.visibility = View.VISIBLE
            if (null != webLoadListener) {
                webLoadListener?.onPageStarted(url, favicon)
            }
        }

        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
            progressBar?.visibility = View.GONE
            if (null != webLoadListener) {
                webLoadListener?.onPageFinished(url)
            }
        }

        override fun onReceivedError(view: WebView, errorCode: Int, description: String?, failingUrl: String?) {
            super.onReceivedError(view, errorCode, description, failingUrl) // 断网或者网络连接超时
            if (errorCode == ERROR_HOST_LOOKUP || errorCode == ERROR_CONNECT || errorCode == ERROR_TIMEOUT) {
                view.loadUrl("about:blank");// 避免出现默认的错误界面
                webLoadListener?.onError(failingUrl)
            }
        }

        override fun onReceivedError(view: WebView, request: WebResourceRequest, error: WebResourceError) {
            super.onReceivedError(view, request, error)
            progressBar?.visibility = View.GONE

        }
    }


    fun onBackPressed(): Boolean {
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

    @JvmOverloads
    fun loadUrl(url: String, clear: Boolean = true) {
        webView?.run {
            if (clear) {
                clearHistory()
            }
            loadUrl(url)
        }
    }

    companion object {
        fun instantiate(context: Context,
                        title: String?,
                        url: String?,
                        scriptInterface: ScriptInterface? = null,
                        showToolBar: Boolean = true,
                        showstatusBar: Boolean = true): BaseWebFragment {
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