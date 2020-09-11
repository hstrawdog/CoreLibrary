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
    /**
     * Log的开关<br></br>
     * true为开启<br></br>
     * false为关闭<br></br>
     */
    /**
     * Log 输出标签
     */
    var TAG = BuildConfig.LIBRARY_PACKAGE_NAME
    fun i(`object`: Any?) {
        if (CoreConfig.instance.isDebug) {
            if (`object` == null) {
                i("标签" + TAG + "的打印内容为空！")
            }
            Log.i(TAG, `object`.toString())
        }
    }

    /**
     * DEBUG 类型日志
     *
     * @param object
     */
    fun d(`object`: Any?) {
        d(TAG, `object`)
    }

    /**
     * DEBUG 类型日志
     *
     * @param tag    日志标识
     * @param object
     */
    fun d(tag: String, `object`: Any?) {
        if (CoreConfig.instance.isDebug) {
            if (`object` == null) {
                d("标签" + tag + "的打印内容为空！")
            }
            Log.d(tag, `object`.toString())
        }
    }

    fun e(`object`: Exception?) {
        if (CoreConfig.instance.isDebug) {
            if (`object` == null) {
                d("标签" + TAG + "的打印内容为空！")
            }
            Log.e(TAG, TAG, `object`)
        }
    }

    /**
     * ERROR 类型日志
     *
     * @param object
     */
    fun e(`object`: Any?) {
        e(TAG, `object`)
    }

    /**
     * ERROR 类型日志
     *
     * @param tag    日志标识
     * @param object
     */
    @kotlin.jvm.JvmStatic
    fun e(tag: String, `object`: Any?) {
        if (CoreConfig.instance.isDebug) {
            if (`object` == null) {
                e("标签" + tag + "的打印内容为空！")
            }
            val sobject = `object`.toString().trim { it <= ' ' }
            if (sobject.length > 4 * 1000) {
                var i = 0
                while (i < sobject.length) {
                    if (i + 4000 < sobject.length) {
                        Log.e(tag + i, sobject.substring(i, i + 4 * 1000))
                    } else {
                        Log.e(tag + i, sobject.substring(i, sobject.length))
                    }
                    i += 4 * 1000
                }
            } else {
                Log.e(tag, sobject)
            }
        }
    }

    fun v(`object`: Any?) {
        if (CoreConfig.instance.isDebug) {
            if (`object` == null) {
                v("标签" + TAG + "的打印内容为空！")
            }
            Log.v(TAG, `object`.toString())
        }
    }

    fun w(`object`: Any?) {
        if (CoreConfig.instance.isDebug) {
            if (`object` == null) {
                w("标签" + TAG + "的打印内容为空！")
            }
            Log.w(TAG, `object`.toString())
        }
    }
}