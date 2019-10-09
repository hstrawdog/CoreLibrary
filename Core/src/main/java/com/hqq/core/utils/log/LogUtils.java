package com.hqq.core.utils.log;


import android.util.Log;

import com.hqq.core.BuildConfig;
import com.hqq.core.CoreBuildConfig;

/**
 * @Author : huangqiqiang
 * @Package : com.qi.core.utils.log
 * @FileName :   LogUtils
 * @Date : 2019/3/13 0013
 * @Email :  qiqiang213@gmail.com
 * @Descrive :    logcat 打印的长度  4*1024  这边用的 4*1000
 */
public class LogUtils {
    /**
     * Log的开关<br>
     * true为开启<br>
     * false为关闭<br>
     */
    /**
     * Log 输出标签
     */
    public static String TAG = BuildConfig.APPLICATION_ID;

    public static void i(Object object) {
        if (CoreBuildConfig.getInstance().isDebug()) {
            if (object == null) {
                i("标签" + TAG + "的打印内容为空！");
            }
            Log.i(TAG, object.toString());
        }
    }

    /**
     * DEBUG 类型日志
     *
     * @param object
     */
    public static void d(Object object) {
        d(TAG, object);
    }

    /**
     * DEBUG 类型日志
     *
     * @param tag    日志标识
     * @param object
     */
    public static void d(String tag, Object object) {
        if (CoreBuildConfig.getInstance().isDebug()) {
            if (object == null) {
                d("标签" + tag + "的打印内容为空！");
            }
            Log.d(tag, object.toString());
        }
    }

    /**
     * ERROR 类型日志
     *
     * @param object
     */
    public static void e(Object object) {
        e(TAG, object);
    }

    /**
     * ERROR 类型日志
     *
     * @param tag    日志标识
     * @param object
     */
    public static void e(String tag, Object object) {
        if (CoreBuildConfig.getInstance().isDebug()) {
            if (object == null) {
                e("标签" + tag + "的打印内容为空！");
            }
            String sobject = object.toString().trim();
            if (sobject.length() > 4 * 1000) {
                for (int i = 0; i < sobject.length(); i += 4 * 1000) {
                    if (i + 4000 < sobject.length()) {
                        Log.e(tag + i, sobject.substring(i, i + 4 * 1000));
                    } else {
                        Log.e(tag + i, sobject.substring(i, sobject.length()));
                    }
                }
            } else {
                Log.e(tag, sobject);
            }
        }
    }

    public static void v(Object object) {
        if (CoreBuildConfig.getInstance().isDebug()) {
            if (object == null) {
                v("标签" + TAG + "的打印内容为空！");
            }
            Log.v(TAG, object.toString());
        }
    }

    public static void w(Object object) {
        if (CoreBuildConfig.getInstance().isDebug()) {
            if (object == null) {
                w("标签" + TAG + "的打印内容为空！");
            }
            Log.w(TAG, object.toString());
        }
    }


}
