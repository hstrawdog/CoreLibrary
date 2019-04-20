package com.hqq.core.listenner;

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

     void onPageStarted(String url, Bitmap favicon);


    void onPageFinished(String url);
}
