package com.hqq.core

import android.app.Activity
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Bundle
import com.hqq.core.utils.log.LogUtils

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core
 * @FileName :   ActivityLifecycle
 * @Date : 2019/6/6 0006  上午 10:20
 * @Email : qiqiang213@gmail.com
 * @Descrive : 缓存 Activity生命周期栈
 */
class ActivityLifecycle : ActivityLifecycleCallbacks {
    private val activities: MutableList<Activity> = ArrayList()

    /**
     * 获取APP中activity栈中最上层一个activity
     *
     * @return
     */
    val activity: Activity
        get() {
            check(!(activities.size == 0)) { "Core 未初始化" }
            // 获取最上面的 Activity
            for (i in activities.indices.reversed()) {
                val activity = activities[i]
                if (!activity.isFinishing) {
                    return activity
                }
            }
            throw IllegalStateException("未获取到栈内对象 当前Activity 异常")
        }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        activities.add(activity)
        LogUtils.i("ActivityLifecycle -> onActivityCreated" + activity.localClassName)
    }

    override fun onActivityStarted(activity: Activity) {}
    override fun onActivityResumed(activity: Activity) {}
    override fun onActivityPaused(activity: Activity) {}
    override fun onActivityStopped(activity: Activity) {}
    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
    override fun onActivityDestroyed(activity: Activity) {
        activities.remove(activity)
        LogUtils.dInfo("ActivityLifecycle -> onActivityDestroyed" + activity.localClassName)
    }

}