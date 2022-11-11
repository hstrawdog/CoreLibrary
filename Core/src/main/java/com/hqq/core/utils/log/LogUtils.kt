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
        if (CoreConfig.get().isDebug) {
            e("$TAG", any)
        }
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
     * E 类型日志
     */
    @kotlin.jvm.JvmStatic
    fun dInfo(any: Any?) {
        var tag = "Info"
        if (CoreConfig.get().isDebug) {
            doLog(tag, "┌────────────────────────────────────────────────────────────────────────────────────────────────────────────────", "w")

            if (any == null) {
                e("标签 : 内容为空！")
            } else {
                doLog(tag, any, "w")
            }
            doLog(tag, "├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄", "w")

            val stackTrace = Throwable().stackTrace
            if (stackTrace.size > 1) {
                for (index in 1 until (if (stackTrace.size > 5) 5 else stackTrace.size)) {
                    val targetElement = stackTrace[index]
                    val head = "${Thread.currentThread().name}  |      ${targetElement.getClassName()}.${targetElement.getMethodName()}(${
                        getFileName(targetElement)
                    }:${targetElement.getLineNumber()})            "
                    doLog(tag, "|      $head     ", "w")
                }
            }
            doLog(tag, "└────────────────────────────────────────────────────────────────────────────────────────────────────────────────", "w")
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

    /**
     *  获取类名
     * @param targetElement StackTraceElement
     * @return String
     */
    private fun getFileName(targetElement: StackTraceElement): String {
        val fileName = targetElement.fileName
        if (fileName != null) return fileName
        // If name of file is null, should add
        // "-keepattributes SourceFile,LineNumberTable" in proguard file.
        var className = targetElement.className
        val classNameInfo = className.split("\\.".toRegex()).toTypedArray()
        if (classNameInfo.size > 0) {
            className = classNameInfo[classNameInfo.size - 1]
        }
        val index = className.indexOf('$')
        if (index != -1) {
            className = className.substring(0, index)
        }
        return "$className.java"
    }

}