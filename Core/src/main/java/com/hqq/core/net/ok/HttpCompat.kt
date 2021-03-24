package com.hqq.core.net.ok

import okhttp3.Call
import okhttp3.RequestBody

/**
 * @Author : huangqiqiang
 * @Package : com.qq.readbook.net.ok
 * @FileName :   HttpCompat
 * @Date : 2016/12/14 15:40.
 * @Email :  qiqiang213@gmail.com
 * @Descrive :  坚持封装的 OkHttp 请求
 */
interface HttpCompat {
    /**
     * 参数的通用性上还是有些问题
     */
    interface ParamsCompat {
        var decode: String?
        var encode: String?
        var headers: MutableMap<String, String>
        fun put(key: String?, value: Any?): ParamsCompat?
        fun paramGet(): String?
        fun paramForm(): RequestBody
        fun paramMulti(): RequestBody
    }

    /**
     * 请求对象
     *
     * @return
     */
    fun create(): HttpCompat?

    /**
     * get方法
     *
     * @param url
     * @param params
     * @param callback
     */
    operator fun get(url: String, params: ParamsCompat, callback: OkNetCallback)

    /**
     * 同步的get
     *
     * @param url
     * @param params
     * @param callback
     * @return
     */
    fun getExecute(url: String, params: ParamsCompat, callback: OkNetCallback): Call

    /**
     * post  方法
     *
     * @param url
     * @param params
     * @param callback
     * @return
     */
    fun post(url: String, params: ParamsCompat, callback: OkNetCallback): Call

    /**
     * 同步的post
     *
     * @param url
     * @param params
     * @param callback
     * @return
     */
    fun postExecute(url: String, params: ParamsCompat, callback: OkNetCallback): Call //  其他请求方式
}