package com.easy.core.net.net.ok


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


}