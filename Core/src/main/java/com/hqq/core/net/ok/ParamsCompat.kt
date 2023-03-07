package com.hqq.core.net.ok

import com.hqq.core.annotation.LayoutModel
import okhttp3.RequestBody

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.net.ok
 * @Date : 10:47
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
interface ParamsCompat {

    var decodeCharset: String?

    var encodeCharset: String?

    var headers: MutableMap<String, String>

    /**
     *  目前支持 数据格式转换 xml  与post
     */
    @DataFormat
    var dataFormat: String

    /**
     *   格式化 成put   数据
     */
    fun put(key: String?, value: Any?): ParamsCompat?

    /**
     *  格式化 成get
     */
    fun paramGet(): String?

    /**
     *   格式化  from 表单
     */
    fun paramForm(): RequestBody

    /**
     * 格式化 流
     */
    fun paramMulti(): RequestBody

    /**
     *  格式化成xml
     */
    fun paramXml(): RequestBody
}