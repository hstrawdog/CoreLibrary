package com.easy.core

import android.app.Activity
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Bundle
import com.easy.core.utils.log.LogUtils

/**
 * @Author : huangqiqiang
 * @Package :com.easy.core
 * @FileName :   ActivityLifecycle
 * @Date : 2019/6/6 0006  上午 10:20
 * @Email : qiqiang213@gmail.com
 * @Describe : 缓存 Activity生命周期栈
 */
class ActivityLifecycle : ActivityLifecycleCallbacks {
    val activities: MutableList<Activity> = ArrayList()

    /**
     * 获取APP中activity栈中最上层一个activity
     *
     * @return
     */
    val activity: Activity?
        get() {
            check(activities.size != 0) {
                LogUtils.e("activities 0 或者 Core 未初始化")
            }
            // 获取最上面的 Activity
            for (i in activities.indices.reversed()) {
                val activity = activities[i]
                if (!activity.isFinishing) {
                    return activity
                }
            }
            return null
        }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        LogUtils.i("ActivityLifecycle -> onActivityCreated  " + activity.localClassName)
        activities.add(activity)
    }

    override fun onActivityStarted(activity: Activity) {}
    override fun onActivityResumed(activity: Activity) {}
    override fun onActivityPaused(activity: Activity) {}
    override fun onActivityStopped(activity: Activity) {}
    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
    override fun onActivityDestroyed(activity: Activity) {
        LogUtils.i("ActivityLifecycle -> onActivityDestroyed    " + activity.localClassName)
        activities.remove(activity)
    }

}