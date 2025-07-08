package com.easy.example.jsbridge.core;



public interface WebViewJavascriptBridge {
	
	void sendToWeb(String data);

	void sendToWeb(String data, OnBridgeCallback responseCallback);

	void sendToWeb(String function, Object... values);

}
