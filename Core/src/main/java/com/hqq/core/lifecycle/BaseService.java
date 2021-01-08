package com.hqq.core.lifecycle;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;

/**
 * @Author : huangqiqiang
 * @Package : com.qq.readbook
 * @Date : 下午 1:25
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
public abstract class BaseService extends Service  implements LifecycleOwner {
     LifecycleRegistry mLifecycleRegistry = new LifecycleRegistry(this);
    @Override
    public void onCreate() {
        super.onCreate();
        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
    }


    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START);
    }


    @Override
    public IBinder onBind(Intent intent) {
        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME);
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);
        super.onDestroy();
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return mLifecycleRegistry;
    }

}
