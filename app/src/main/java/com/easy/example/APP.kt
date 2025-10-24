package com.easy.example

import android.app.Application
import android.content.pm.ApplicationInfo
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.easy.example.ui.crash.CrashHandler
import dagger.hilt.android.HiltAndroidApp

import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

/**
 * @Author : huangqiqiang
 * @Package : com.easy.iblibrary
 * @FileName :   APP
 * @Date : 2018/11/23 0023  下午 2:21
 * @Describe :
 * @Email :  qiqiang213@gmail.com
 */
@HiltAndroidApp
class APP : Application() {
    override fun onCreate() {
        super.onCreate()
        //配置 默认加载的 toolBar    CoreInitProvider   会比 Application  更加优先
        //  CoreBuildConfig.getInstance().init(this, true);
        //.setDefItoobar(BaseDefToolBarImpl.class);


        // 本地异常捕捉
        CrashHandler.register(this)

        // 定时任务    最小间隔 15 分钟
        WorkManager.getInstance()
            .enqueueUniquePeriodicWork("Location", ExistingPeriodicWorkPolicy.REPLACE,
                PeriodicWorkRequest.Builder(TestWord::class.java, 15, TimeUnit.MINUTES)
                    .build())
        //  取消任务
        WorkManager.getInstance()
            .cancelAllWork()
     handleSSLHandshake()

    }
    fun handleSSLHandshake() {
        try {
            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                override fun getAcceptedIssuers():Array<X509Certificate> {
                    return arrayOf();
                }

                override fun checkClientTrusted(certs:Array<X509Certificate>, authType:String) {
                }

                override fun checkServerTrusted(certs:Array<X509Certificate>, authType:String) {
                }
            })

            val sc = SSLContext.getInstance("TLS")
            // trustAllCerts信任所有的证书
            sc.init(null, trustAllCerts, SecureRandom())
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.socketFactory)
            HttpsURLConnection.setDefaultHostnameVerifier { hostname, session -> true }
        } catch (ignored:java.lang.Exception) {
        }
    }

    //debug 返回true  release 返回false
    val isApkDebugable: Boolean
        get() {
            //debug 返回true  release 返回false
            try {
                val info = applicationInfo
                return info.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0
            } catch (e: Exception) {
            }
            return false
        }
}