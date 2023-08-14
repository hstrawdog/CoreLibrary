package com.easy.core.lifecycle


import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry

/**
 * @Author : huangqiqiang
 * @Package : com.qq.readbook
 * @Date : 下午 1:25
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
abstract class BaseService : Service(), LifecycleOwner {
    var mLifecycleRegistry = LifecycleRegistry(this)
    override fun onCreate() {
        super.onCreate()
        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
    }

    override fun onStart(intent: Intent, startId: Int) {
        super.onStart(intent, startId)
        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START)
    }

    override fun onBind(intent: Intent): IBinder? {
        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
        return null
    }

    override fun onUnbind(intent: Intent): Boolean {
        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        super.onDestroy()
    }

    override fun getLifecycle(): Lifecycle {
        return mLifecycleRegistry
    }
}