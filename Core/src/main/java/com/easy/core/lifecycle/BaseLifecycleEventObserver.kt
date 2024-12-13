package com.easy.core.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

/**
 * @Author : huangqiqiang
 * @Package : com.easy.core.lifecycle
 * @Date  :2024/12/13 10:01
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
interface BaseLifecycleEventObserver : LifecycleEventObserver {

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {

            Lifecycle.Event.ON_CREATE -> {
                onCreate()
            }

            Lifecycle.Event.ON_START -> {
                onStart()
            }

            Lifecycle.Event.ON_RESUME -> {
                onResume()
            }

            Lifecycle.Event.ON_PAUSE -> {
                onPause()
            }

            Lifecycle.Event.ON_STOP -> {
                onStop()
            }

            Lifecycle.Event.ON_DESTROY -> {
                onDestroy()
            }

            else -> {

            }
        }

    }

    fun onDestroy() {


    }

    fun onStop() {


    }

    fun onPause() {


    }

    fun onResume() {


    }

    fun onCreate() {


    }

    fun onStart() {
    }

}