package com.hqq.core.net.ok;


import okhttp3.Call;

/**
 * @Author : huangqiqiang
 * @Package : com.qq.readbook.net.ok
 * @FileName :   HttpCompat
 * @Date : 2016/12/14 15:40.
 * @Email :  qiqiang213@gmail.com
 * @Descrive :  坚持封装的 OkHttp 请求
 */
public interface HttpCompat {
    /**
     * 参数的通用性上还是有些问题
     */
    interface ParamsCompat {

        ParamsCompat put(String key, Object value);

        String paramGet();

        <T> T paramForm();

        <T> T paramMulti();

        String toString();
    }


    /**
     * 请求对象
     *
     * @return
     */
    HttpCompat create();

    /**
     * get方法
     *
     * @param url
     * @param params
     * @param callback
     */
    void get(String url, ParamsCompat params, OkNetCallback callback);

    /**
     * 同步的get
     *  @param url
     * @param params
     * @param callback
     * @return
     */
    Call getExecute(String url, ParamsCompat params, OkNetCallback callback);

    /**
     * post  方法
     *  @param url
     * @param params
     * @param callback
     * @return
     */
    Call post(String url, ParamsCompat params, OkNetCallback callback);

    /**
     * 同步的post
     *  @param url
     * @param params
     * @param callback
     * @return
     */
    Call postExecute(String url, ParamsCompat params, OkNetCallback callback);


    //  其他请求方式


}
