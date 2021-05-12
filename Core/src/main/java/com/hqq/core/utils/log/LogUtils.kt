package com.hqq.core.utils.log

import android.util.Log
import com.hqq.core.BuildConfig
import com.hqq.core.CoreConfig

/**
 * @Author : huangqiqiang
 * @Package : com.qi.core.utils.log
 * @FileName :   LogUtils
 * @Date : 2019/3/13 0013
 * @Email :  qiqiang213@gmail.com
 * @Descrive :    logcat 打印的长度  4*1024  这边用的 4*1000
 */
object LogUtils {
    //规定每段显示的长度
    private const val LOG_MAX_LENGTH = 2000

    /**
     * Log 输出标签
     */
    var TAG = BuildConfig.LIBRARY_PACKAGE_NAME

    @kotlin.jvm.JvmStatic
    fun i(any: Any?) {
        if (CoreConfig.get().isDebug) {
            if (any == null) {
                i("标签" + TAG + "的打印内容为空！")
            } else {
                doLog(TAG, any, "i")
            }
        }
    }

    /**
     * DEBUG 类型日志
     *
     * @param object
     */
    @kotlin.jvm.JvmStatic
    fun d(any: Any?) {
        d(TAG, any)
    }

    /**
     * DEBUG 类型日志
     *
     * @param tag    日志标识
     * @param object
     */
    @kotlin.jvm.JvmStatic
    fun d(tag: String, any: Any?) {
        if (CoreConfig.get().isDebug) {
            if (any == null) {
                d("标签" + tag + "的打印内容为空！")
            } else {
                doLog(tag, any, "d")
            }
        }
    }

    @kotlin.jvm.JvmStatic
    fun v(any: Any?) {
        if (CoreConfig.get().isDebug) {
            if (any == null) {
                v("标签" + TAG + "的打印内容为空！")
            } else {
                doLog(TAG, any, "v")
            }
        }
    }

    @kotlin.jvm.JvmStatic
    fun w(any: Any?) {
        if (CoreConfig.get().isDebug) {
            if (any == null) {
                w("标签" + TAG + "的打印内容为空！")
            } else {
                doLog(TAG, any, "w")
            }
        }
    }


    /**
     * ERROR 类型日志
     *
     * @param object
     */
    @kotlin.jvm.JvmStatic
    fun e(any: Any?) {
        e(TAG, any)
    }

    @kotlin.jvm.JvmStatic
    fun e4Debug(any: Any?) {
        e("$TAG debug_log", any)
    }

    /**
     * E 类型错误日志
     */
    @kotlin.jvm.JvmStatic
    fun e(exception: Exception?) {
        if (CoreConfig.get().isDebug) {
            Log.e(TAG, TAG, exception)
        }
    }

    /**
     * E 类型日志
     */
    @kotlin.jvm.JvmStatic
    fun e(tag: String, any: Any?) {
        if (CoreConfig.get().isDebug) {
            if (any == null) {
                e("标签 : " + tag + " 的打印内容为空！")
            } else {
                doLog(tag, any, "e")
            }

        }
    }

    /**
     *  判断日志是否超过长度
     */
    @kotlin.jvm.JvmStatic
    private fun doLog(tag: String, any: Any, s: String) {
        val log = any.toString().trim()
        if (log.length > LOG_MAX_LENGTH) {
            doLog4Length(tag, log, s)
        } else {
            printLog(tag, log, s)
        }
    }

    /**
     *  处理超过限制长度的日志
     */
    @kotlin.jvm.JvmStatic
    private fun doLog4Length(str: String, log: String, tag: String) {
        val strLength: Int = log.length
        var start = 0
        var end: Int = LOG_MAX_LENGTH
        for (i in 0..99) {
            //剩下的文本还是大于规定长度则继续重复截取并输出
            if (strLength > end) {
                printLog(str + i, log.substring(start, end), tag)
                start = end
                end = end + LOG_MAX_LENGTH
            } else {
                printLog(str + i, log.substring(start, strLength), tag)
                break
            }
        }
    }

    /**
     *  打印日志
     */
    @kotlin.jvm.JvmStatic
    private fun printLog(s: String, substring: String, tag: String) {
        when (tag) {
            "e" -> {
                Log.e(s, substring)
            }
            "d" -> {
                Log.d(s, substring)
            }
            "w" -> {
                Log.w(s, substring)
            }
            "v" -> {
                Log.v(s, substring)
            }
            "i" -> {
                Log.i(s, substring)

            }

        }

    }


}