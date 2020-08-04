package com.hqq.example.demo.net;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.demo.net
 * @FileName :   NetCallback
 * @Date : 2020/8/4 0004  下午 4:41
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public interface NetCallback<T> {
    /**
     * @param response
     */
    void onSuccess(T response);

    /**
     * @param code
     * @param message
     */
    void onFail(int code, String message);
}
