package com.example.accessibility

import android.app.Application

/**
 * @Author : huangqiqiang
 * @Package : com.example.accessibility
 * @Date : 11:24
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class App : Application() {
    companion object {
        var accessibilityInterface: AccessibilityInterface? = null
    }

    override fun onCreate() {
        super.onCreate()
    }
}