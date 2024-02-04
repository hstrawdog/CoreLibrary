package com.example.accessibility

import android.app.Activity
import android.app.Application
import android.os.Bundle

/**
 * Created by StoneHui on 2018/10/30.
 * <p>
 * Activity 生命周期回调适配器。
 */
open class ActivityLifecycleCallbacksAdapter : Application.ActivityLifecycleCallbacks {


    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
    }

    override fun onActivityStarted(activity: Activity) {
    }

    override fun onActivityResumed(activity: Activity) {
    }

    override fun onActivityPaused(activity: Activity) {
    }

    override fun onActivityStopped(activity: Activity) {
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    override fun onActivityDestroyed(activity: Activity) {
    }

}