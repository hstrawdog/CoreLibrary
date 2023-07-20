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

    @JvmStatic
    fun v(any: Any?) {
        if (CoreConfig.get().isDebug) {
            if (any == null) {
                v("标签" + TAG + "的打印内容为空！")
            } else {
                doLog("v", TAG, any)
            }
        }
    }

    /**
     * DEBUG 类型日志
     *œ
     * @param object
     */
    @JvmStatic
    fun d(any: Any?) {
        d(TAG, any)
    }

    /**
     * DEBUG 类型日志
     *
     * @param tag    日志标识
     * @param object
     */
    @JvmStatic
    fun d(tag: String, any: Any?) {
        if (CoreConfig.get().isDebug) {
            if (any == null) {
                d("标签" + tag + "的打印内容为空！")
            } else {
                doLog("d", tag, any)
            }
        }
    }

    /**
     * E 类型日志
     */
    @JvmStatic
    fun dInfo(any: Any?) {
        var tag = "Info"
        if (CoreConfig.get().isDebug) {
            doLog(tag, "w", "┌────────────────────────────────────────────────────────────────────────────────────────────────────────────────")
            if (any == null) {
                e("标签 : 内容为空！")
            } else {
                doLog("w", tag, any)
            }
            doLog(tag, "w", "├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄")
            val stackTrace = Throwable().stackTrace
            if (stackTrace.size > 1) {
                for (index in 1 until (if (stackTrace.size > 5) 5 else stackTrace.size)) {
                    val targetElement = stackTrace[index]
                    val head =
                        "${Thread.currentThread().name}  |      ${targetElement.getClassName()}.${targetElement.getMethodName()}(${
                            getFileName(targetElement)
                        }:${targetElement.getLineNumber()})            "
                    doLog("w", tag, "|      $head     ")
                }
            }
            doLog(tag, "w", "└────────────────────────────────────────────────────────────────────────────────────────────────────────────────")
        }
    }

    @JvmStatic
    fun e4Debug(any: Any?) {
        if (CoreConfig.get().isDebug) {
            e("$TAG", any)
        }
    }

    @JvmStatic
    fun i(any: Any?) {
        if (CoreConfig.get().isDebug) {
            if (any == null) {
                i("标签" + TAG + "的打印内容为空！")
            } else {
                doLog("i", TAG, any)
            }
        }
    }

    @JvmStatic
    fun i(tag: String = TAG, any: Any?) {
        if (CoreConfig.get().isDebug) {
            if (any == null) {
                i("标签" + TAG + "的打印内容为空！")
            } else {
                doLog("i", TAG, any)
            }
        }
    }

    @JvmStatic
    fun w(any: Any?) {
        if (CoreConfig.get().isDebug) {
            if (any == null) {
                doLog("w", TAG, "标签" + TAG + "的打印内容为空！")
            } else {
                doLog("w", TAG, any)
            }
        }
    }

    /**
     * ERROR 类型日志
     *
     * @param object
     */
    @JvmStatic
    fun e(any: Any?) {
        e(TAG, any)
    }

    /**
     * E 类型错误日志
     */
    @JvmStatic
    fun e(exception: Exception?) {
        if (CoreConfig.get().isDebug) {
            Log.e(TAG, TAG, exception)
        }
    }

    /**
     * E 类型日志
     */
    @JvmStatic
    fun e(tag: String, any: Any?) {
        if (CoreConfig.get().isDebug) {
            if (any == null) {
                e("标签 : $tag 的打印内容为空！")
            } else {
                doLog("e", tag, any)
            }

        }
    }

    /**
     *  判断日志是否超过长度
     */
    @JvmStatic
    private fun doLog(level: String, tag: String, any: Any) {
        val log = any.toString()
            .trim()
        if (log.length > LOG_MAX_LENGTH) {
            doLog4Length(level, tag, log)
        } else {
            printLog(level, tag, log)
        }
    }

    /**
     *  处理超过限制长度的日志
     */
    @JvmStatic
    private fun doLog4Length(level: String, str: String, log: String) {
        val strLength: Int = log.length
        var start = 0
        var end: Int = LOG_MAX_LENGTH
        for (i in 0..99) {
            //剩下的文本还是大于规定长度则继续重复截取并输出
            if (strLength > end) {
                printLog(level, str + i, log.substring(start, end))
                start = end
                end += LOG_MAX_LENGTH
            } else {
                printLog(level, str + i, log.substring(start, strLength))
                break
            }
        }
    }

    /**
     *  打印日志
     */
    @JvmStatic
    private fun printLog(level: String, tag: String, msg: String) {
        when (level) {
            "e" -> {
                Log.e(tag, msg)
            }

            "d" -> {
                Log.d(tag, msg)
            }

            "w" -> {
                Log.w(tag, msg)
            }

            "v" -> {
                Log.v(tag, msg)
            }

            "i" -> {
                Log.i(tag, msg)

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
        val classNameInfo = className.split("\\.".toRegex())
            .toTypedArray()
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