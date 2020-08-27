package com.hqq.core.ui.web

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.view.View
import android.webkit.*
import android.widget.ProgressBar
import com.hqq.core.R
import com.hqq.core.listenner.ScriptInterface
import com.hqq.core.listenner.WebLoadListener
import com.hqq.core.ui.base.BaseFragment
import com.hqq.core.utils.BundleUtils
import com.hqq.core.utils.RegexUtils
import com.hqq.core.utils.VersionUtils

/**
 * @Author : huangqiqiang
 * @Package : com.qi.core.ui
 * @FileName :   BaseWebFragment
 * @Date : 2019/3/4 0004  下午 4:22
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
class BaseWebFragment : BaseFragment() {
    var progressBar: ProgressBar? = null
    var webView: WebView? = null
        private set
    private var mUrl: String? = null
    private var mTitle: CharSequence? = ""
    private var mWebLoadListener: WebLoadListener? = null
    var progressBarColor: ColorStateList? = null
    private var mScriptInterface: ScriptInterface? = null

    override val layoutViewId: Int = R.layout.fragment_web


    override fun onPause() {
        super.onPause()
        webView!!.onPause()
    }

    override fun onResume() {
        super.onResume()
        webView!!.onResume()
    }

    override fun initConfig() {
        super.initConfig()
        rootViewBuild.mIRootViewImpl.iToolBarBuilder.showToolBar=true
        rootViewBuild.mIRootViewImpl.iToolBarBuilder.showStatusBar=true
    }

    @SuppressLint("JavascriptInterface")
    override fun initView() {
        webView = findViewById(R.id.web_view) as WebView?
        progressBar = findViewById(R.id.pb_progressbar) as ProgressBar?
        if (RegexUtils.unNull(progressBarColor)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                progressBar!!.indeterminateTintList = progressBarColor
            }
        }
        progressBar!!.max = 100
        //先加载5%，以使用户觉得界面没有卡死
        progressBar!!.progress = 5
        initWebViewSettings()
        webView!!.webViewClient = webViewClient
        webView!!.webChromeClient = webChromeClient
        // 标识 为Android的 js 支持 对象是activity
        if (mScriptInterface != null) {
            webView!!.addJavascriptInterface(requireActivity(), "android")
        }
        mUrl = BundleUtils.getString(this, getString(R.string.key_url))
        mTitle = BundleUtils.getString(this, getString(R.string.key_title))
        requireActivity().title = mTitle
        iToolBar?.setToolbarTitle(mTitle)
        webView!!.loadUrl(mUrl!!)
    }

    protected fun initWebViewSettings() {
        val settings = webView!!.settings
        settings.setUserAgentString(settings.userAgentString + "" + requireActivity().packageName)
        settings.javaScriptEnabled = true
        settings.javaScriptCanOpenWindowsAutomatically = true
        settings.allowFileAccess = true
        settings.useWideViewPort = true
        settings.loadWithOverviewMode = true
        settings.builtInZoomControls = true
        settings.domStorageEnabled = true
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            settings.displayZoomControls = false
        }
        // MIXED_CONTENT_ALWAYS_ALLOW：允许从任何来源加载内容，即使起源是不安全的；
        // MIXED_CONTENT_NEVER_ALLOW：不允许Https加载Http的内容，即不允许从安全的起源去加载一个不安全的资源；
        // MIXED_CONTENT_COMPATIBILITY_MODE：当涉及到混合式内容时，WebView 会尝试去兼容最新Web浏览器的风格。
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView!!.settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
            //提高渲染优先级
            settings.setRenderPriority(WebSettings.RenderPriority.HIGH)
            settings.savePassword = false
            settings.pluginState = WebSettings.PluginState.ON
        }
    }//request.getUrl()// do something if app is not installed

    //处理 android err_unknown_url_scheme异常
    protected val webViewClient: WebViewClient
        protected get() = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                //处理 android err_unknown_url_scheme异常
                if (URLUtil.isNetworkUrl(request.url.toString())) {
                    return false
                }
                if (VersionUtils.appInstalledOrNot(activity, request.url.toString())) {
                    val intent = Intent(Intent.ACTION_VIEW, request.url)
                    startActivity(intent)
                } else {
                    // do something if app is not installed
                }
                return super.shouldOverrideUrlLoading(view, request)
            }

            override fun shouldInterceptRequest(view: WebView, request: WebResourceRequest): WebResourceResponse? {
                return super.shouldInterceptRequest(view, request)
                //request.getUrl()
            }

            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap) {
                super.onPageStarted(view, url, favicon)
                progressBar!!.visibility = View.VISIBLE
                if (null != mWebLoadListener) {
                    mWebLoadListener!!.onPageStarted(url, favicon)
                }
            }

            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                progressBar!!.visibility = View.GONE
                if (null != mWebLoadListener) {
                    mWebLoadListener!!.onPageFinished(url)
                }
            }

            override fun onReceivedError(view: WebView, request: WebResourceRequest, error: WebResourceError) {
                super.onReceivedError(view, request, error)
                progressBar!!.visibility = View.GONE
            }
        }

    protected val webChromeClient: WebChromeClient
        protected get() = object : WebChromeClient() {
            override fun onReceivedTitle(view: WebView, title: String?) {
                super.onReceivedTitle(view, title)
                title?.let {
                    if (mTitle.isNullOrEmpty()) {
                        iToolBar?.setToolbarTitle(mTitle)
                    }
                }
            }

            override fun onProgressChanged(view: WebView, newProgress: Int) {
                if (newProgress >= 5) {
                    progressBar!!.progress = newProgress
                }
                super.onProgressChanged(view, newProgress)
            }
        }

    fun onBackPressed(): Boolean {
        if (webView!!.canGoBack()) {
            webView!!.goBack()
            return true
        }
        return false
    }

    @JvmOverloads
    fun loadUrl(url: String, clear: Boolean = true) {
        if (webView != null) {
            if (clear) {
                webView!!.clearHistory()
            }
            webView!!.loadUrl(url.also { mUrl = it })
        }
    }

    fun setWebLoadListener(webLoadListener: WebLoadListener?): BaseWebFragment {
        mWebLoadListener = webLoadListener
        return this
    }

    fun setScriptInterface(scriptInterface: ScriptInterface?) {
        mScriptInterface = scriptInterface
    }

    companion object {
        @JvmOverloads
        fun instantiate(context: Context, title: String?, url: String?, scriptInterface: ScriptInterface? = null): BaseWebFragment {
            val baseWebFragment = BaseWebFragment()
            val bundle = Bundle()
            bundle.putString(context.getString(R.string.key_url), url)
            bundle.putString(context.getString(R.string.key_title), title)
            baseWebFragment.arguments = bundle
            baseWebFragment.setScriptInterface(scriptInterface)
            return baseWebFragment
        }
    }
}