package com.hqq.core.ui.web;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.hqq.core.R;
import com.hqq.core.listenner.WebLoadListener;
import com.hqq.core.ui.BaseFragment;
import com.hqq.core.utils.RegexUtils;

/**
 * @Author : huangqiqiang
 * @Package : com.qi.core.ui
 * @FileName :   BaseWebFragment
 * @Date : 2019/3/4 0004  下午 4:22
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public class BaseWebFragment extends BaseFragment {


    private ProgressBar mProgressBar;
    private WebView mWebView;
    private String mUrl;
    private CharSequence mTitle = "";
    WebLoadListener mWebLoadListener;


    public static BaseWebFragment instantiate(Context context, String title, String url) {
        BaseWebFragment baseWebFragment = new BaseWebFragment();
        Bundle bundle = new Bundle();
        bundle.putString(context.getString(R.string.key_url), url);
        bundle.putString(context.getString(R.string.key_title), title);
        baseWebFragment.setArguments(bundle);
        return baseWebFragment;
    }


    @Override
    public int getViewId() {
        return R.layout.fragment_web;
    }


    @Override
    public void onPause() {
        super.onPause();
        mWebView.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mWebView.onResume();
    }

    @Override
    protected void initDefBuild() {
        super.initDefBuild();
        mRootViewBuild.setShowStatus(true);
        mRootViewBuild.setShowToolBar(true);
    }


    @SuppressLint("JavascriptInterface")
    @Override
    public void initBasic(Bundle savedInstanceState) {
        mWebView = (WebView) findViewById(R.id.web_view);
        mProgressBar = (ProgressBar) findViewById(R.id.pb_progressbar);

        mProgressBar.setMax(100);
        //先加载5%，以使用户觉得界面没有卡死
        mProgressBar.setProgress(5);


        initWebViewSettings();

        mWebView.setWebViewClient(getWebViewClient());
        mWebView.setWebChromeClient(getWebChromeClient());
        // 标识 为Android的 js 支持 对象是activity
        mWebView.addJavascriptInterface(getActivity(), "android");

        mUrl = getArguments().getString(getString(R.string.key_url));
        mTitle = getArguments().getString(getString(R.string.key_title));
        getActivity().setTitle(mTitle);
        mWebView.loadUrl(mUrl);

    }

    protected void initWebViewSettings() {
        WebSettings settings = mWebView.getSettings();
        settings.setUserAgentString(settings.getUserAgentString() + "" + mActivity.getPackageName());

        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);

        settings.setAllowFileAccess(true);

        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setBuiltInZoomControls(true);
        settings.setDomStorageEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            settings.setDisplayZoomControls(false);
        }
/**
 * MIXED_CONTENT_ALWAYS_ALLOW：允许从任何来源加载内容，即使起源是不安全的；
 * MIXED_CONTENT_NEVER_ALLOW：不允许Https加载Http的内容，即不允许从安全的起源去加载一个不安全的资源；
 * MIXED_CONTENT_COMPATIBILITY_MODE：当涉及到混合式内容时，WebView 会尝试去兼容最新Web浏览器的风格。
 **/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWebView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
            //提高渲染优先级
            settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
            settings.setSavePassword(false);
            settings.setPluginState(WebSettings.PluginState.ON);
        }
    }


    protected WebViewClient getWebViewClient() {
        return new WebViewClient() {

            @Nullable
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                return super.shouldInterceptRequest(view, request);
                //request.getUrl()
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                mProgressBar.setVisibility(View.VISIBLE);
                if (null != mWebLoadListener) {
                    mWebLoadListener.onPageStarted(url, favicon);
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mProgressBar.setVisibility(View.GONE);
                if (null != mWebLoadListener) {
                    mWebLoadListener.onPageFinished(url);
                }
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                mProgressBar.setVisibility(View.GONE);

            }

        };
    }


    protected WebChromeClient getWebChromeClient() {
        return new WebChromeClient() {

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (TextUtils.isEmpty(mTitle) && RegexUtils.checkNotNull(title)) {
                    if (mRootViewBuild.getDefToolBar() != null) {
                        mRootViewBuild.getDefToolBar().setToolbarTitle(title);
                    }
                }
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress >= 5) {
                    mProgressBar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }
        };
    }

    public final boolean onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return false;
    }

    public final void loadUrl(String url) {
        loadUrl(url, true);
    }

    public final void loadUrl(String url, boolean clear) {
        if (mWebView != null) {
            if (clear) {
                mWebView.clearHistory();
            }
            mWebView.loadUrl(mUrl = url);
        }
    }

    public BaseWebFragment setWebLoadListener(WebLoadListener webLoadListener) {
        mWebLoadListener = webLoadListener;
        return this;
    }

    public ProgressBar getProgressBar() {
        return mProgressBar;
    }

    public final WebView getWebView() {
        return mWebView;
    }
}
