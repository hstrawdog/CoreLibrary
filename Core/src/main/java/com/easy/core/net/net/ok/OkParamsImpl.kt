/*
 * Create on 2016-10-26 下午2:47
 * FileName: OKParamsImpl.java
 * Author: Du Jianchi
 * Contact: du-1@qq.com or http://dujc.cn
 */
package com.easy.core.net.net.ok

import okhttp3.Headers.Companion.headersOf
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.UnsupportedEncodingException
import java.net.URLConnection
import java.net.URLEncoder
import java.util.*
import kotlin.collections.HashMap

/**
 * @Author : huangqiqiang
 * @Package : com.qq.readbook.net.ok
 * @FileName :   OKParamsImpl
 * @Date : 2020/12/9 0009  下午 5:15
 * @Email :  qiqiang213@gmail.com
 * @Descrive :Created by du on 16/9/4.
 */
class OkParamsImpl : ParamsCompat {

    /**
     *  请求key
     */
    private val keys: MutableList<String?> = ArrayList()

    /**
     * 请求value
     */
    private val values: MutableList<Any?> = ArrayList()

    /**
     *  解码
     */
    override var decodeCharset: String? = ""

    /**
     *  编码
     */
    override var encodeCharset: String? = ""

    /**
     *  请求header
     */
    override var headers: MutableMap<String, String> = HashMap()

    @DataFormat
    override var dataFormat: String = DataFormat.POST


    var paramMap = HashMap<Any?, Any?>()


    override fun put(key: String?, value: Any?): ParamsCompat {
        keys.add(key)
        values.add(value)
        paramMap.put(key, value)
        return this
    }

    fun put(params: HashMap<String?, Any>) {
        for (key in params.keys) {
            keys.add(key)
            values.add(params.getValue(key))
            paramMap.put(key, params.getValue(key))
        }
    }

    fun put(params: TreeMap<String, String?>) {
        for (key in params.keys) {
            keys.add(key)
            values.add(params.getValue(key))
            paramMap.put(key, params.getValue(key))
        }
    }


    override fun paramGet(): String {
        return lineUp()
    }

    override fun paramForm(): RequestBody {
        return RequestBody.create("application/x-www-form-urlencoded".toMediaTypeOrNull(), lineUp())
    }

    /**
     * 表单方式提交数据
     * 正常是用于数据上传
     * 未测试
     *
     * @return
     */
    override fun paramMulti(): RequestBody {
        val builder = MultipartBody.Builder()
        builder.setType(MultipartBody.FORM)
        val stringBuilder = StringBuilder()
        val length = keys.size
        for (index in 0 until length) {
            val value = values[index]
            if (value is File) {
                val filename = value.name
                val fileBody =
                    RequestBody.create(guessMimeType(filename).toMediaTypeOrNull(), value)
                keys[index]?.let { builder.addFormDataPart(it, filename, fileBody) }
                builder.addPart(fileBody)
            } else {
                builder.addPart(
                    headersOf("Content-Disposition", "form-data; name=\"" + keys[index] + "\""),
                    RequestBody.create(null, value.toString() + "")
                )
                stringBuilder.append(keys[index]).append('=')
                    .append(encodeString(value.toString() + "")).append('&')
            }
        }
        val len = stringBuilder.length
        if (len > 0) {
            val strings = stringBuilder.substring(0, len - 1)
            val params = RequestBody.create(MultipartBody.FORM, strings)
            builder.addPart(params)
        }
        return builder.build()
    }

    /**
     *  格式化成XMl
     */
    override fun paramXml(): RequestBody {
        return RequestBody.create(
            "text/plain; charset=utf-8".toMediaTypeOrNull(), parseString2Xml()
        )
    }

    override fun toString(): String {
        return lineUp()
    }

    /**
     * 组装成一行数据
     *
     * @return
     */
    private fun lineUp(): String {
        val builder = StringBuilder()
        val length = keys.size
        for (index in 0 until length - 1) {
            builder.append(keys[index]).append('=')
                .append(encodeString(values[index].toString() + "")).append('&')
        }
        if (length - 1 >= 0) {
            builder.append(keys[length - 1]).append('=')
                .append(encodeString(values[length - 1].toString() + ""))
        }
        return builder.toString()
    }

    private fun encodeString(string: String): String {
        if (encodeCharset == null || encodeCharset!!.isEmpty()) {
            return string
        }
        try {
            return URLEncoder.encode(string, encodeCharset)
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }
        return string
    }


    /**
     * 参数进行XML化
     *
     * @param map,sign
     * @return
     */
    fun parseString2Xml(): String {
        val sb = StringBuffer()
        sb.append("<xml>")
        val es: Set<*> = paramMap.entries
        val iterator = es.iterator()
        while (iterator.hasNext()) {
            val (key, value) = iterator.next() as Map.Entry<*, *>
            val k = key as String
            val v = value as String
            sb.append("<$k>$v</$k>")
        }
        sb.append("</xml>")
        return sb.toString()
    }


    companion object {
        private fun guessMimeType(path: String): String {
            val fileNameMap = URLConnection.getFileNameMap()
            var contentTypeFor: String? = null
            try {
                contentTypeFor = fileNameMap.getContentTypeFor(URLEncoder.encode(path, "UTF-8"))
            } catch (e: UnsupportedEncodingException) {
                e.printStackTrace()
            }
            if (contentTypeFor == null) {
                contentTypeFor = "application/octet-stream"
            }
            return contentTypeFor
        }
    }
}