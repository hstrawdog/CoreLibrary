/*
 * Create on 2016-10-26 下午2:47
 * FileName: OKParamsImpl.java
 * Author: Du Jianchi
 * Contact: du-1@qq.com or http://dujc.cn
 */
package com.hqq.core.net.ok

import com.hqq.core.net.ok.HttpCompat.ParamsCompat
import okhttp3.Headers
import okhttp3.Headers.Companion.headersOf
import okhttp3.MediaType
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
    private val keys: MutableList<String?> = ArrayList()
    private val values: MutableList<Any?> = ArrayList()
    override var decode: String? = ""
    override var encode: String? = ""
    override var headers: MutableMap<String, String> =HashMap()


    override fun put(key: String?, value: Any?): ParamsCompat? {
        keys.add(key)
        values.add(value)
        return this
    }

    override fun paramGet(): String? {
        return lineUp()
    }

    override fun  paramForm(): RequestBody {
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
                val f = value
                val filename = f.name
                val fileBody = RequestBody.create(guessMimeType(filename).toMediaTypeOrNull(), f)
                keys[index]?.let { builder.addFormDataPart(it, filename, fileBody) }
                builder.addPart(fileBody)
            } else {
                builder.addPart(headersOf("Content-Disposition", "form-data; name=\"" + keys[index] + "\""),
                    RequestBody.create(null, value.toString() + ""))
                stringBuilder.append(keys[index]).append('=').append(encodeString(value.toString() + "")).append('&')
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
            builder.append(keys[index]).append('=').append(encodeString(values[index].toString() + "")).append('&')
        }
        if (length - 1 >= 0) {
            builder.append(keys[length - 1]).append('=').append(encodeString(values[length - 1].toString() + ""))
        }
        return builder.toString()
    }

    private fun encodeString(string: String): String {
        if (encode == null || encode!!.isEmpty()) {
            return string
        }
        try {
            return URLEncoder.encode(string, encode)
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }
        return string
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