package com.hqq.core.utils

import android.app.Activity
import android.content.Intent
import android.util.ArrayMap
import android.util.SparseArray
import java.util.*

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.utils
 * @FileName :   ActivityResultUtils
 * @Date : 2019/6/3 0003  上午 11:58
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 *
 *
 * -  Activity 如何维护相同的两个界面 activity 关闭时候
 * -  句柄是否会出现相同的 如果不相同是否可以考虑下使用句柄作为key
 * -   是否可以考虑下使用注解来维护requestCode
 */
class ActivityResultUtils {
    var mActivitySparseArrayArrayMap = ArrayMap<Activity, SparseArray<ActivityForResult>>()
    var codeGenerator = Random()
    fun startActivity(activity: Activity, intent: Intent?, activityForResult: ActivityForResult?) {
        val requestCode = codeGenerator.nextInt(0x0000FFFF)
        activity.startActivityForResult(intent, requestCode)
        mActivitySparseArrayArrayMap[activity]!!.append(requestCode, activityForResult)
    }

    interface ActivityForResult {
        fun onActivityResult()
    }
}