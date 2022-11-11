package com.hqq.core.net.ok

import com.hqq.core.net.ok.HttpCompat.ParamsCompat

/**
 * @Author : huangqiqiang
 * @Package : com.qq.readbook.net.ok
 * @FileName :   OkHttp
 * @Date : 2016/12/14 15:51
 * @Email :  qiqiang213@gmail.com
 * @Descrive :
 */
object OkHttp {
    private val mOkHttp: HttpCompat by lazy {
        OkHttpImpl().create()
    }

    @Synchronized
    fun newHttpCompat(): HttpCompat {
        return mOkHttp
    }

    /**
     * 空的对象
     *
     * @return
     */
    fun newParamsCompat(): ParamsCompat {
        return OkParamsImpl()
    }

    /**
     * key value 对象
     *
     * @param key
     * @param values
     * @return
     */
    fun newParamsCompat(key: String?, values: Any?): ParamsCompat {
        val paramsCompat = newParamsCompat()
        paramsCompat.put(key, values)
        return paramsCompat
    }
}