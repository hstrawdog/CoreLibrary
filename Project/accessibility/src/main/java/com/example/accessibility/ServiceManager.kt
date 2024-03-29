package com.example.accessibility

import android.accessibilityservice.AccessibilityServiceInfo
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.view.accessibility.AccessibilityManager
import com.easy.core.utils.log.LogUtils

/**
 * Created by StoneHui on 2019-11-28.
 * <p>
 * 无障碍服务管理
 */
object ServiceManager {

    /**
     * 打开服务的回调。
     */
    interface OnOpenServiceListener {
        /**
         * 打开服务的回调。
         *
         * @param isOpening 服务是否开启。
         */
        fun onResult(isOpening: Boolean)
    }

    /**
     * 检查服务是否开启。
     */
    fun isServiceEnabled(context: Context): Boolean {
        (context.getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager)
            .getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_ALL_MASK)
            .filter { it.id.equals("${context.packageName}/.JobAccessibilityService") }
            .let { return it.isNotEmpty() }
    }

    /**
     * 打开服务。
     *
     * @param listener 打开服务结果监听。
     */
    fun openService(context: Context, listener: (Boolean) -> Unit) {
        if (isServiceEnabled(context)) {
            listener(true)
            return
        }
        //打开系统无障碍设置界面
        val accessibleIntent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
        context.startActivity(accessibleIntent)

        LogUtils.e("1")
        (context.applicationContext as Application).registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacksAdapter() {
            override fun onActivityResumed(activity: Activity) {
                LogUtils.e("2")
                if (context::class.java == activity::class.java) {
                    LogUtils.e("3")
                    (context.applicationContext as Application).unregisterActivityLifecycleCallbacks(this)
                    listener(isServiceEnabled(context))
                }
            }
        })

    }

    /**
     * 打开服务。
     *
     * @param listener 打开服务结果监听。
     */
    fun openService(context: Context, listener: OnOpenServiceListener) {
        openService(context) { listener.onResult(it) }
    }

}