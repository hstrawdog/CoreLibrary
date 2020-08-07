package com.hqq.core.utils

import android.app.Activity
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import java.util.*

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.utils
 * @FileName :   BundleUtils
 * @Date : 2019/5/29 0029  下午 5:11
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
object BundleUtils {
    /******************************** Bundle空判断 避免取值中直接奔溃    */
    /**
     * 获取bandle
     *
     * @param object
     * @return
     */
    fun getBundle(`object`: Any?): Bundle? {
        if (`object` is Fragment) {
            return `object`.arguments
        } else if (`object` is Activity) {
            return `object`.intent.extras
        }
        return null
    }

    /**
     * @param arguments
     * @param key
     * @param defaultValue
     * @return
     */
    fun getValue(arguments: Bundle?, key: String?, defaultValue: Any): Any {
        if (arguments != null) {
            if (defaultValue is Int) {
                return arguments.getInt(key, defaultValue)
            } else if (defaultValue is String) {
                return arguments.getString(key, defaultValue)
            }
        }
        return defaultValue
    }

    /**
     * 同上
     *
     * @param fragment
     * @param key
     * @param defaultValue
     * @return
     */
    fun getValue(fragment: Fragment, key: String?, defaultValue: Any): Any {
        return getValue(fragment.arguments, key, defaultValue)
    }

    /**
     * 同上
     *
     * @param activity
     * @param key
     * @param defaultValue
     * @return
     */
    fun getVaue(activity: Activity, key: String?, defaultValue: Any): Any {
        return getValue(activity.intent.extras, key, defaultValue)
    }

    /**
     * 获取Int 类型
     *
     * @param arguments
     * @param key
     * @param defaultValue
     * @return
     */
    fun getInt(arguments: Bundle?, key: String?, defaultValue: Int): Int {
        return arguments?.getInt(key, defaultValue) ?: defaultValue
    }

    /**
     * 获取字符串
     *
     * @param arguments
     * @param key
     * @param defaultValue
     * @return
     */
    fun getString(arguments: Bundle?, key: String?, defaultValue: String): String {
        return if (arguments != null) {
            arguments.getString(key, defaultValue)
        } else defaultValue
    }

    /**
     * 同上
     *
     * @param arguments
     * @param key
     * @return
     */
    fun getString(arguments: Bundle?, key: String?): String? {
        return if (arguments != null) {
            arguments.getString(key)
        } else ""
    }

    /**
     * 同上
     *
     * @param fragment
     * @param key
     * @return
     */
    fun getString(fragment: Fragment, key: String?): String? {
        return getString(fragment.arguments, key)
    }

    /**
     * 序列化对象
     *
     * @param arguments
     * @param key
     * @param <T>
     * @return
    </T> */
    fun <T : Parcelable?> getParcelable(arguments: Bundle?, key: String?): T? {
        return arguments?.getParcelable(key)
    }

    /**
     * 序列化数组对象
     *
     * @param arguments
     * @param key
     * @param <T>
     * @return
    </T> */
    fun <T : Parcelable?> getParcelableArrayList(arguments: Bundle?, key: String?): ArrayList<T>? {
        return arguments?.getParcelableArrayList(key)
    }
}