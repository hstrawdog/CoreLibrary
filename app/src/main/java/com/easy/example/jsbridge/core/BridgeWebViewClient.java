package com.easy.example.jsbridge.core;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import androidx.annotation.Nullable;

import android.util.Log;
import android.view.KeyEvent;
import android.webkit.ClientCertRequest;
import android.webkit.HttpAuthHandler;
import android.webkit.RenderProcessGoneDetail;
import android.webkit.SafeBrowsingResponse;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * 如果要自定义WebViewClient必须要集成此类
 * Created by bruce on 10/28/15.
 */
class BridgeWebViewClient extends WebViewClient {

    private static final String TAG = "BridgeWebViewClient";
    private OnLoadJSListener mListener;

    private WebViewClient mClient;
    BridgeWebView bridgeWebView ;
    public BridgeWebViewClient(OnLoadJSListener listener, BridgeWebView bridgeWebView) {
        mListener = listener;
       this. bridgeWebView = bridgeWebView;
    }

    public void setWebViewClient(WebViewClient client) {
        mClient = client;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {

       return  mListener.shouldOverrideUrlLoading(url);

//        if (mClient != null) {
//            return mClient.shouldOverrideUrlLoading(view, url);
//        }
//        return interceptUrl(url) ? true : super.shouldOverrideUrlLoading(view, url);
    }

    private boolean interceptUrl(String url) {
        try {
            url = URLDecoder.decode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String replacedUrl = url.replaceAll("%(?![0-9a-fA-F]{2})", "%25").replaceAll("\\+", "%2B");
        try {
            url = URLDecoder.decode(replacedUrl, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        Log.i(TAG, "shouldOverrideUrlLoading, url = " + url);
        if (url.startsWith(BridgeUtil.YY_RETURN_DATA)) { // 如果是返回数据
            return true;
        } else if (url.startsWith(BridgeUtil.YY_OVERRIDE_SCHEMA)) { //

            return true;
        }
        return false;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        if (mClient != null) {
            mClient.onPageStarted(view, url, favicon);
        } else {
            super.onPageStarted(view, url, favicon);
        }

    }

    @Override
    public void onPageFinished(WebView view, String url) {
        if (mClient != null) {
            mClient.onPageFinished(view, url);
        } else {
            super.onPageFinished(view, url);
        }
        mListener.onLoadStart();
        BridgeUtil.webViewLoadLocalJs(view, BridgeUtil.JAVA_SCRIPT);
        mListener.onLoadFinished();
    }

    @Override
    public void onLoadResource(WebView view, String url) {
        if (mClient != null) {
            mClient.onLoadResource(view, url);
        } else {
            super.onLoadResource(view, url);
        }
    }

    @Override
    public void onPageCommitVisible(WebView view, String url) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && mClient != null) {
            mClient.onPageCommitVisible(view, url);
        } else {
            super.onPageCommitVisible(view, url);
        }
    }

    @Nullable
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
        if (mClient != null) {
            return mClient.shouldInterceptRequest(view, url);
        }
        return super.shouldInterceptRequest(view, url);
    }

    @Nullable
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && mClient != null) {
            return mClient.shouldInterceptRequest(view, request);
        }
        return super.shouldInterceptRequest(view, request);
    }

    @Override
    public void onTooManyRedirects(WebView view, android.os.Message cancelMsg, android.os.Message continueMsg) {
        if (mClient != null) {
            mClient.onTooManyRedirects(view, cancelMsg, continueMsg);
        } else {
            super.onTooManyRedirects(view, cancelMsg, continueMsg);
        }
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        if (mClient != null) {
            mClient.onReceivedError(view, errorCode, description, failingUrl);
        } else {
            super.onReceivedError(view, errorCode, description, failingUrl);
        }
    }

    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && mClient != null) {
            mClient.onReceivedError(view, request, error);
        } else {
            super.onReceivedError(view, request, error);
        }
    }

    @Override
    public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && mClient != null) {
            mClient.onReceivedHttpError(view, request, errorResponse);
        } else {
            super.onReceivedHttpError(view, request, errorResponse);
        }

    }

    @Override
    public void onFormResubmission(WebView view, android.os.Message dontResend, android.os.Message resend) {
        if (mClient != null) {
            mClient.onFormResubmission(view, dontResend, resend);
        } else {
            super.onFormResubmission(view, dontResend, resend);
        }

    }

    @Override
    public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
        if (mClient != null) {
            mClient.doUpdateVisitedHistory(view, url, isReload);
        } else {
            super.doUpdateVisitedHistory(view, url, isReload);
        }
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        if (mClient != null) {
            mClient.onReceivedSslError(view, handler, error);
        } else {
            super.onReceivedSslError(view, handler, error);
        }
    }

    @Override
    public void onReceivedClientCertRequest(WebView view, ClientCertRequest request) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && mClient != null) {
            mClient.onReceivedClientCertRequest(view, request);
        } else {
            super.onReceivedClientCertRequest(view, request);
        }
    }

    @Override
    public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
        if (mClient != null) {
            mClient.onReceivedHttpAuthRequest(view, handler, host, realm);
        } else {
            super.onReceivedHttpAuthRequest(view, handler, host, realm);
        }
    }

    @Override
    public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
        if (mClient != null) {
            return mClient.shouldOverrideKeyEvent(view, event);
        }
        return super.shouldOverrideKeyEvent(view, event);
    }

    @Override
    public void onUnhandledKeyEvent(WebView view, KeyEvent event) {
        if (mClient != null) {
            mClient.onUnhandledKeyEvent(view, event);
        } else {
            super.onUnhandledKeyEvent(view, event);
        }
    }

    @Override
    public void onScaleChanged(WebView view, float oldScale, float newScale) {
        if (mClient != null) {
            mClient.onScaleChanged(view, oldScale, newScale);
        } else {
            super.onScaleChanged(view, oldScale, newScale);
        }
    }

    @Override
    public void onReceivedLoginRequest(WebView view, String realm, @Nullable String account, String args) {
        if (mClient != null) {
            mClient.onReceivedLoginRequest(view, realm, account, args);
        } else {
            super.onReceivedLoginRequest(view, realm, account, args);
        }
    }

    @Override
    public boolean onRenderProcessGone(WebView view, RenderProcessGoneDetail detail) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && mClient != null){
            return mClient.onRenderProcessGone(view, detail);
        }
        return super.onRenderProcessGone(view, detail);
    }

    @Override
    public void onSafeBrowsingHit(WebView view, WebResourceRequest request, int threatType, SafeBrowsingResponse callback) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1 && mClient != null){
            mClient.onSafeBrowsingHit(view, request, threatType, callback);
        }else {
            super.onSafeBrowsingHit(view, request, threatType, callback);
        }
    }


    public interface OnLoadJSListener {

        void onLoadStart();

        void onLoadFinished();


        boolean shouldOverrideUrlLoading (String url);
    }
}