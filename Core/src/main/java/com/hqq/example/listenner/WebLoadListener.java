package com.hqq.example.listenner;

import android.graphics.Bitmap;

/**
 * @Author : huangqiqiang
 * @Package : com.qi.core.listenner
 * @FileName :   WebLoadListener
 * @Date : 2019/3/22 0022  上午 11:47
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public interface WebLoadListener {

    /**
     * 开始加载网页
     *
     * @param url     String
     * @param favicon
     */
    void onPageStarted(String url, Bitmap favicon);


    /**
     * 结束加载网页
     * @param url String
     */
    void onPageFinished(String url);
}
