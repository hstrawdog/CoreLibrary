package com.hqq.example.listenner;

import java.io.File;

/**
 * @Author : huangqiqiang
 * @Package : com.qi.core.listenner
 * @FileName :   OnCompressListener
 * @Date : 2019/2/21 0021  下午 2:37
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public interface OnCompressListener {
    /**
     * Fired when the compression is started, override to handle in your own code
     */
    void onStart();

    /**
     * Fired when a compression returns successfully, override to handle in your own code
     */
    void onSuccess(File file);

    /**
     * Fired when a compression fails to complete, override to handle in your own code
     */
    void onError(Throwable e);
}
