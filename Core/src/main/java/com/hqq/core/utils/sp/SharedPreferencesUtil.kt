package com.hqq.core.utils.sp

import android.content.Context

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.utils.sp
 * @FileName :   SharedPreferencesUtil.kt
 * @Date  : 2020/9/1 0001  下午 5:20
 * @Email :  qiqiang213@gmail.com
 * @Descrive :
 */
object SharedPreferencesUtil {
    @Synchronized
    fun putObject(context: Context, key: String?, `object`: Any) {
        //PreferenceManager.getDefaultSharedPreferences(context);
        val sp = context.applicationContext.getSharedPreferences("preferences", Context.MODE_PRIVATE or Context.MODE_MULTI_PROCESS)
        val editor = sp.edit()
        if (`object` is Boolean) {
            editor.putBoolean(key, `object`)
        } else if (`object` is Float) {
            editor.putFloat(key, `object`)
        } else if (`object` is Int) {
            editor.putInt(key, `object`)
        } else if (`object` is Long) {
            editor.putLong(key, `object`)
        } else if (`object` is String) {
            editor.putString(key, `object`)
        } else {
            editor.putString(key, `object`.toString())
        }
        editor.apply()
    }

    @Synchronized
    fun getObject(context: Context, key: String?, defaultObject: Any?): Any? {
        return try {
            //  PreferenceManager.getDefaultSharedPreferences(context); // 会出现空指针=
            val sp = context.applicationContext.getSharedPreferences("preferences", Context.MODE_PRIVATE or Context.MODE_MULTI_PROCESS)
            if (defaultObject is Boolean) {
                return sp.getBoolean(key, (defaultObject as Boolean?)!!)
            } else if (defaultObject is Float) {
                return sp.getFloat(key, (defaultObject as Float?)!!)
            } else if (defaultObject is Int) {
                return sp.getInt(key, (defaultObject as Int?)!!)
            } else if (defaultObject is Long) {
                return sp.getLong(key, (defaultObject as Long?)!!)
            } else if (defaultObject is String) {
                return sp.getString(key, defaultObject as String?)
            }
            null
        } catch (e: Exception) {
            null
        }
    }
}