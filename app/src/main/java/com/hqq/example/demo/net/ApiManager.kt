package com.hqq.example.demo.net

import com.hqq.example.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.demo.net
 * @FileName :   ApiManager
 * @Date : 2020/7/28 0028  下午 4:36
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
object ApiManager {
    //默认超时时间(毫秒)
    internal const val DEFAULT_TIMEOUT: Long = 900000
    var mWangAndroidInterface: WangAndroidInterface? = null
    private const val mWangAndroidUrl = "https://www.wanandroid.com/"//打印拦截器

    //设置出现错误进行重新连接
    val wangAndroidInterface: WangAndroidInterface
        get() {
            if (mWangAndroidInterface == null) {

                //设置出现错误进行重新连接
                val okHttpClient = OkHttpClient.Builder()
                okHttpClient.retryOnConnectionFailure(true)
                        .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                        .readTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                val logInterceptor = HttpLoggingInterceptor()
                if (BuildConfig.DEBUG) {
                    //打印拦截器
                    logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
                    okHttpClient.addInterceptor(logInterceptor)
                } else {
                    logInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE)
                }
                mWangAndroidInterface = Retrofit.Builder()
                        .client(okHttpClient.build())
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl(mWangAndroidUrl)
                        .build().create(WangAndroidInterface::class.java)
            }
            return mWangAndroidInterface!!
        }

    fun getJuHeInterface(url: String?): JuHeInterface {
        val juHeInterface: JuHeInterface

        //设置出现错误进行重新连接
        val okHttpClient = OkHttpClient.Builder()
        okHttpClient.retryOnConnectionFailure(true)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
        val logInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            //打印拦截器
            logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            okHttpClient.addInterceptor(logInterceptor)
        } else {
            logInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE)
        }
        juHeInterface = Retrofit.Builder()
                .client(okHttpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(url!!)
                .build().create(JuHeInterface::class.java)
        return juHeInterface
    }
}