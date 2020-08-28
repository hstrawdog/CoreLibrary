package com.hqq.core.utils

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager

/**
 * @Author : huangqiqiang
 * @Package : com.core.library.utils
 * @FileName :   VersionUtils
 * @Date : 2018/7/3 0003  下午 1:44
 * @Descrive : TODO
 * @Email :  qiqiang213@gmail.com
 */
object VersionUtils {
    /**
     * 获取当前本地apk的版本
     *
     * @param context Context
     * @return int
     */
    @kotlin.jvm.JvmStatic
    fun getVersionCode(context: Context): Int {
        var versionCode = 0
        try {
            //获取软件版本号，对应AndroidManifest.xml下android:versionCode
            versionCode = context.packageManager.getPackageInfo(context.packageName, 0).versionCode
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return versionCode
    }

    /**
     * 获取版本号名称
     *
     * @param context 上下文
     * @return String
     */
    @kotlin.jvm.JvmStatic
    fun getVerName(context: Context): String {
        return context.packageManager.getPackageInfo(context.packageName, 0).versionName
    }

    /**
     * [获取应用程序版本名称信息]
     *
     * @param context
     * @return 当前应用的版本名称
     */
    @kotlin.jvm.JvmStatic
    @Synchronized
    fun getPackageName(context: Context?): String? {
        try {
            val packageManager = context!!.packageManager
            val packageInfo = packageManager.getPackageInfo(
                    context.packageName, 0)
            return packageInfo.packageName
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * https://stackoverflow.com/questions/41693263/android-webview-err-unknown-url-scheme
     * @param activity
     * @param uri
     * @return
     */
    fun appInstalledOrNot(activity: Activity?, uri: String?): Boolean {
        val pm = activity!!.packageManager
        try {
            pm.getPackageInfo(uri!!, PackageManager.GET_ACTIVITIES)
            return true
        } catch (e: PackageManager.NameNotFoundException) {
        }
        return false
    }
}