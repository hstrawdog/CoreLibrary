package com.easy.core.net.net

import com.google.gson.GsonBuilder
import com.easy.core.BuildConfig
import com.easy.core.CoreConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @Author : huangqiqiang
 * @Package : com.easy.core.net
 * @Date  : 下午 1:23
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
object RetrofitService {

    /**
     *  okHttp  请求对象
     */
    private val okHttp: OkHttpClient
        get() = OkHttpClient().newBuilder().apply {
            //连接超时
            connectTimeout(CoreConfig.get().connectTimeout, TimeUnit.SECONDS)
            //写超时
            writeTimeout(CoreConfig.get().writeTimeout, TimeUnit.SECONDS)
            //读取超时
            readTimeout(CoreConfig.get().readTimeout, TimeUnit.SECONDS)
            if (CoreConfig.get().isDebug) {
                //打印拦截器
                addInterceptor(HttpLoggingInterceptor().apply {
                    setLevel(HttpLoggingInterceptor.Level.BODY)
                })
            }
        }.build()

    /**
     *  Gson 对象
     */
    private val gson
        get() = GsonBuilder()
                .apply {
                    setPrettyPrinting()
                    // 动态注册 Gson转义
                    for (entry in CoreConfig.get().instanceCreators.keys) {
                        registerTypeAdapter(entry, CoreConfig.get().instanceCreators[entry])
                    }
                }
                .create()

    /**
     * Retrofit 构建器
     */
    private val retrofitBuilder = Retrofit.Builder()
            .client(okHttp)
            .addConverterFactory(GsonConverterFactory.create(gson))

    /**
     *  创建 Retrofit 对象
     */
    fun <S> createService(baseUrl: String = CoreConfig.get().baseUrl, serviceClass: Class<S>): S {
        return retrofitBuilder.baseUrl(baseUrl).build().create(serviceClass)

    }

}