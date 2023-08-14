package com.easy.core.lifecycle



import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

/**
 * @Author : huangqiqiang
 * @Package :  com.easy.example.ui.jetpack.livedata
 * @Date : 15:43
 * @Email : qiqiang213@gmail.com
 * @Describe : https://blog.csdn.net/qijingwang/article/details/121765484
 */
class SingleLiveEvent<T> : MutableLiveData<T>() {
    private val mPending = AtomicBoolean(false)

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        if (hasActiveObservers()) {
            Log.w("SingleLiveEvent", "Multiple observers registered but only one will be notified of changes.")
        }
        super.observe(owner) { t ->
            if (mPending.compareAndSet(true, false)) {
                observer.onChanged(t)
            }
        }
    }


    @MainThread
    override fun setValue(t: T?) {
        mPending.set(true)
        super.setValue(t)
    }


    @MainThread
    fun call() {
        value = null
    }


}