package com.qq.readbook

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.hqq.core.utils.log.LogUtils
import com.qq.readbook.bean.book.BookSource
import com.qq.readbook.room.RoomUtils
import com.qq.readbook.weight.page.BrightnessUtils
import com.qq.readbook.weight.page.ReadSettingManager
import com.qq.readbook.xpath.ReplaceString
import com.tencent.bugly.crashreport.CrashReport
import com.umeng.analytics.MobclickAgent
import com.umeng.commonsdk.UMConfigure
import org.seimicrawler.xpath.util.Scanner
import java.io.IOException
import java.io.InputStreamReader
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

/**
 * @Author : huangqiqiang
 * @Package : com.qq.readbook
 * @Date : 上午 11:04
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class App : Application(), Application.ActivityLifecycleCallbacks {

    override fun onCreate() {
        super.onCreate()
        CrashReport.initCrashReport(applicationContext, "be45a0ab0b", false);
        UMConfigure.init(this, "6038ff1ab8c8d45c13820e04", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "");
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
        handleSSLHandshake()
        initConfig()

        registerActivityLifecycleCallbacks(this)
        Scanner.registerFunction(ReplaceString::class.java)
    }

    private fun initConfig() {
        try {
            val inputStream = applicationContext.assets.open("2.json")
            //            val inputStream = applicationContext.assets.open("2.json")
            val inputStreamReader = InputStreamReader(inputStream)
            val jsonReader = JsonReader(inputStreamReader)
            val sourceList = Gson().fromJson<ArrayList<BookSource>>(
                jsonReader,
                object : TypeToken<ArrayList<BookSource>>() {}.type
            )

            RoomUtils.getBookSourceDao().deleteAll()
            RoomUtils.getArticleContentRuleDao().deleteAll()
            RoomUtils.getBookInfoRuleDao().deleteAll()
            RoomUtils.getChapterInfoRuleDao().deleteAll()
            RoomUtils.getSearchRuleDao().deleteAll()

            if (sourceList != null) {
                for (readSource in sourceList) {
                    val result = RoomUtils.getBookSourceDao().getSource4Name(readSource.sourceName)
                    if (result == null) {
                        RoomUtils.getBookSourceDao().insertAll(readSource)
                        readSource.ruleArticleContent?.let {
                            it.sourceName = readSource.sourceName
                            RoomUtils.getArticleContentRuleDao().insertAll(it)
                        }
                        readSource.ruleBookInfo?.let {
                            it.sourceName = readSource.sourceName
                            RoomUtils.getBookInfoRuleDao().insertAll(it)
                        }
                        readSource.ruleChapter?.let {
                            it.sourceName = readSource.sourceName
                            RoomUtils.getChapterInfoRuleDao().insertAll(it)
                        }
                        readSource.ruleSearch?.let {
                            it.sourceName = readSource.sourceName
                            RoomUtils.getSearchRuleDao().insertAll(it)
                        }
                    }

                }
            }


        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    companion object {
        fun handleSSLHandshake() {
            // Create a trust manager that does not validate certificate chains
            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return arrayOf()
                }

                override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
                    LogUtils.i("checkClientTrusted")
                }

                override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
                    LogUtils.i("checkServerTrusted")
                }
            })

            // Install the all-trusting trust manager
            try {
                val sc = SSLContext.getInstance("TLS")
                sc.init(null, trustAllCerts, SecureRandom())
                HttpsURLConnection.setDefaultSSLSocketFactory(sc.socketFactory)
                HttpsURLConnection.setDefaultHostnameVerifier { _, _ -> true }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        //设置当前 Activity 的亮度
        if (ReadSettingManager.getInstance().isBrightnessAuto) {
            BrightnessUtils.setDefaultBrightness(activity)
        } else {
            BrightnessUtils.setBrightness(activity, ReadSettingManager.getInstance().brightness)
        }
    }

    override fun onActivityStarted(activity: Activity) {
    }

    override fun onActivityResumed(activity: Activity) {
    }

    override fun onActivityPaused(activity: Activity) {
    }

    override fun onActivityStopped(activity: Activity) {
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    override fun onActivityDestroyed(activity: Activity) {
    }
}