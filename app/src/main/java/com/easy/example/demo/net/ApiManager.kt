package com.easy.example.demo.net

import com.easy.core.net.net.RetrofitService
import com.easy.example.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @Author : huangqiqiang
 * @Package : com.easy.example.demo.net
 * @FileName :   ApiManager
 * @Date : 2020/7/28 0028  下午 4:36
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
object ApiManager {
    var mWangAndroidInterface: WangAndroidInterface? = null
    private const val mWangAndroidUrl = "https://www.wanandroid.com/"//打印拦截器

    //设置出现错误进行重新连接
    var wangAndroidInterface: WangAndroidInterface? = null
        get() {
            if (mWangAndroidInterface == null) {
                mWangAndroidInterface = RetrofitService.createService(mWangAndroidUrl, WangAndroidInterface::class.java)
            }
            return mWangAndroidInterface!!
        }

    fun getJuHeInterface(url: String): JuHeInterface {
        return RetrofitService.createService(url, JuHeInterface::class.java)
    }
}