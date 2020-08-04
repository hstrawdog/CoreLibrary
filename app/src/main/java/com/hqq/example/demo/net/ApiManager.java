package com.hqq.example.demo.net;

import com.hqq.example.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.demo.net
 * @FileName :   ApiManager
 * @Date : 2020/7/28 0028  下午 4:36
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public class ApiManager {
    //默认超时时间(毫秒)
    protected static final long DEFAULT_TIMEOUT = 900000;

    static WangAndroidInterface mWangAndroidInterface;
    private static String mWangAndroidUrl="https://www.wanandroid.com/";

    public static WangAndroidInterface getWangAndroidInterface() {
        if (mWangAndroidInterface == null) {

            //设置出现错误进行重新连接
            OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
            okHttpClient.retryOnConnectionFailure(true)
                    .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                    .readTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS);

            HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
            if (BuildConfig.DEBUG) {
                //打印拦截器
                logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                okHttpClient.addInterceptor(logInterceptor);
            } else {
                logInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
            }

            mWangAndroidInterface = new Retrofit.Builder()
                    .client(okHttpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(mWangAndroidUrl)
                    .build().create(WangAndroidInterface.class);
        }
        return mWangAndroidInterface;
    }

    public static JuHeInterface getJuHeInterface(String url ) {
        JuHeInterface juHeInterface;

            //设置出现错误进行重新连接
            OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
            okHttpClient.retryOnConnectionFailure(true)
                    .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                    .readTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS);

            HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
            if (BuildConfig.DEBUG) {
                //打印拦截器
                logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                okHttpClient.addInterceptor(logInterceptor);
            } else {
                logInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
            }

        juHeInterface = new Retrofit.Builder()
                    .client(okHttpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(url)
                    .build().create(JuHeInterface.class);

        return juHeInterface;
    }


}
