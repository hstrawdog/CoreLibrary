package com.easy.example.jsbridge;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.easy.core.utils.log.LogUtils;
import com.easy.example.jsbridge.core.BridgeHandler;
import com.easy.example.jsbridge.core.BridgeHelper;
import com.easy.example.jsbridge.core.IWebView;
import com.easy.example.jsbridge.core.OnBridgeCallback;
import com.easy.example.jsbridge.core.WebViewJavascriptBridge;


/**
 * 采用BridgeHelper集成JsBridge功能示例.定制WebView,可只添加实际需要的JsBridge接口.
 *
 * @author ZhengAn
 * @date 2019-07-07
 */
@SuppressLint("SetJavaScriptEnabled")
public class CustomWebView extends WebView implements WebViewJavascriptBridge, IWebView {

    private BridgeHelper bridgeHelper;

    public CustomWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public CustomWebView(Context context) {
        super(context);
        init();
    }

    private void init() {
        this.setVerticalScrollBarEnabled(false);
        this.setHorizontalScrollBarEnabled(false);
        this.getSettings().setJavaScriptEnabled(true);
        getSettings().setUserAgentString(getSettings().getUserAgentString() + "/emmcloud/ccwork/3.8.11");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        getSettings().setDomStorageEnabled(true);
        getSettings().setDatabaseEnabled(true);
        getSettings().setAllowFileAccess(true);

        bridgeHelper = new BridgeHelper(this);
        this.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView webView, String s) {
                bridgeHelper.onPageFinished();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String s) {
                LogUtils.e("shouldOverrideUrlLoading  ");
                return bridgeHelper.shouldOverrideUrlLoading(s);
            }
        });
    }

    /**
     * @param handler default handler,handle messages send by js without assigned handler name,
     *                if js message has handler name, it will be handled by named handlers registered by native
     */
    public void setDefaultHandler(BridgeHandler handler) {
        bridgeHelper.setDefaultHandler(handler);
    }

    /**
     * register handler,so that javascript can call it
     * 注册处理程序,以便javascript调用它
     *
     * @param handlerName handlerName
     * @param handler     BridgeHandler
     */
    public void registerHandler(String handlerName, BridgeHandler handler) {
        bridgeHelper.registerHandler(handlerName, handler);
    }

    /**
     * unregister handler
     *
     * @param handlerName
     */
    public void unregisterHandler(String handlerName) {
        bridgeHelper.unregisterHandler(handlerName);
    }

    /**
     * call javascript registered handler
     * 调用javascript处理程序注册
     *
     * @param handlerName handlerName
     * @param data        data
     * @param callBack    CallBackFunction
     */
    public void callHandler(String handlerName, String data, OnBridgeCallback callBack) {
        bridgeHelper.callHandler(handlerName, data, callBack);
    }

    @Override
    public void sendToWeb(String data) {
        sendToWeb(data, (OnBridgeCallback) null);
    }

    @Override
    public void sendToWeb(String data, OnBridgeCallback responseCallback) {
        bridgeHelper.sendToWeb(data, responseCallback);
    }

    @Override
    public void sendToWeb(String function, Object... values) {
        bridgeHelper.sendToWeb(function, values);
    }
}
