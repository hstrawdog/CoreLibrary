package com.easy.example.jsbridge;

import android.util.Log;
import android.webkit.JavascriptInterface;

import com.easy.example.jsbridge.core.BridgeWebView;
import com.easy.example.jsbridge.core.OnBridgeCallback;

import java.util.Map;


/**
 * Created on 2019/7/10.
 * Author: bigwang
 * Description:
 */
public class MainJavascriptInterface extends BridgeWebView.BaseJavascriptInterface {

    private BridgeWebView mWebView;

    public MainJavascriptInterface(Map<String, OnBridgeCallback> callbacks, BridgeWebView webView) {
        super(callbacks);
        mWebView = webView;
    }

    public MainJavascriptInterface(Map<String, OnBridgeCallback> callbacks) {
        super(callbacks);
    }

    @Override
    public String send(String data) {
        return "it is default response";
    }


    @JavascriptInterface
    public void submitFromWeb(String data, String callbackId) {
        Log.d("MainJavascriptInterface", data + ", callbackId: " + callbackId + " " + Thread.currentThread().getName());
//        mWebView.sendResponse("submitFromWeb response", callbackId);
    }

    @JavascriptInterface
    public void submitFromWeb22(String data, String callbackId) {
        Log.d("MainJavascriptInterface", data + ", callbackId: " + callbackId + " " + Thread.currentThread().getName());
        //        mWebView.sendResponse("submitFromWeb response", callbackId);
    }

}
