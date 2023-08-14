package com.easy.core.utils.statusbar

import android.annotation.SuppressLint
import android.text.TextUtils

/**
 * @Author : huangqiqiang
 * @Package :  com.easy.core.utils.statusbar
 * @FileName :   OSUtils
 * @Date : 2019/6/4 0004  下午 5:14
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
object OSUtils {
    private const val KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name"
    private const val KEY_DISPLAY = "ro.build.display.id"

    /**
     * 判断miui版本是否大于等于6
     * Is miui 6 later boolean.
     *
     * @return the boolean
     */
    val isMIUI6Later: Boolean
        get() {
            val version = mIUIVersion
            val num: Int
            return if (!version.isEmpty()) {
                try {
                    num = Integer.valueOf(version.substring(1))
                    num >= 6
                } catch (e: NumberFormatException) {
                    false
                }
            } else {
                false
            }
        }

    /**
     * 获得miui的版本
     * Gets miui version.
     *
     * @return the miui version
     */
    val mIUIVersion: String
        get() = if (isMIUI) getSystemProperty(KEY_MIUI_VERSION_NAME, "") else ""

    /**
     * 判断是否为miui
     * Is miui boolean.
     *
     * @return the boolean
     */
    val isMIUI: Boolean
        get() {
            val property = getSystemProperty(KEY_MIUI_VERSION_NAME, "")
            return !TextUtils.isEmpty(property)
        }

    private fun getSystemProperty(key: String, defaultValue: String): String {
        try {
            @SuppressLint("PrivateApi") val clz = Class.forName("android.os.SystemProperties")
            val method = clz.getMethod("get", String::class.java, String::class.java)
            return method.invoke(clz, key, defaultValue) as String
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return defaultValue
    }

    /**
     * 判断flymeOS的版本是否大于等于4
     * Is flyme os 4 later boolean.
     *
     * @return the boolean
     */
    val isFlymeOS4Later: Boolean
        get() {
            val version = flymeOSVersion
            val num: Int
            return if (!version.isEmpty()) {
                try {
                    num = if (version.toLowerCase().contains("os")) {
                        Integer.valueOf(version.substring(9, 10))
                    } else {
                        Integer.valueOf(version.substring(6, 7))
                    }
                    num >= 4
                } catch (e: NumberFormatException) {
                    false
                }
            } else false
        }

    /**
     * 得到flymeOS的版本
     * Gets flyme os version.
     *
     * @return the flyme os version
     */
    val flymeOSVersion: String
        get() = if (isFlymeOS) getSystemProperty(KEY_DISPLAY, "") else ""

    /**
     * 判断是否为flymeOS
     * Is flyme os boolean.
     *
     * @return the boolean
     */
    val isFlymeOS: Boolean
        get() = flymeOSFlag.toLowerCase().contains("flyme")

    private val flymeOSFlag: String
        private get() = getSystemProperty(KEY_DISPLAY, "")
}