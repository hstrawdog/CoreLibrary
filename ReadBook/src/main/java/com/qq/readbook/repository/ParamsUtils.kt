package com.qq.readbook.repository

import com.hqq.core.net.ok.HttpCompat
import com.hqq.core.net.ok.OkHttp
import com.hqq.core.net.ok.ParamsCompat
import com.qq.readbook.bean.book.BookSource
import org.json.JSONObject

/**
 * @Author : huangqiqiang
 * @Package : com.qq.readbook.repository
 * @Date : 下午 5:30
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
object ParamsUtils {
    fun getParams(source: BookSource): ParamsCompat {
        val params = OkHttp.newParamsCompat()
        params.decodeCharset = source.decode
        if (source.requestHeads.isNotEmpty()) {
            val json = JSONObject(source.requestHeads)
            json.keys().let {
                for (mutableEntry in it) {
                    params.headers[mutableEntry] = json.getString(mutableEntry)
                }
            }
        }
        return params
    }
}