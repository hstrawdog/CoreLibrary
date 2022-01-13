package com.hqq.example.demo.net

import com.hqq.core.utils.ToastUtils.showToast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.demo.net
 * @FileName :   NetCallback
 * @Date : 2020/8/4 0004  下午 4:41
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
abstract class NetCallback<T> : Callback<NetResponseBody<T>> {
    override fun onFailure(call: Call<NetResponseBody<T>>, t: Throwable) {
        onFail(-999, "网络连接失败,请检网络连接")
    }

    override fun onResponse(call: Call<NetResponseBody<T>>, response: Response<NetResponseBody<T>>?) {
        if (response != null) {
            if (response.code() == 200) {
                val responseBody: NetResponseBody<*> = response.body()!!
                if (responseBody.error_code == 0) {
                    //   if (responseBody.getBody().getStatus() == 0 || responseBody.getBody().getStatus() == 200) {
                    onSuccess(response?.body()?.result!!)
                } else {
                    onFail(responseBody.error_code, responseBody.reason!!)
                }
                return
            }
        }
        onFail(-9998, "服务器连接异常")
    }

    /**
     * @param response
     */
    abstract fun onSuccess(response: T)

    /**
     * @param code
     * @param message
     */
    open fun onFail(code: Int, message: String) {
        showToast(message)
    }
}