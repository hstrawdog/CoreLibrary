package com.example.accessibility.kuaishou

import com.example.accessibility.App
import com.hqq.core.CoreConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @Author : huangqiqiang
 * @Package : com.example.accessibility
 * @Date : 16:01
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class KuaiShouJob {
    /**
     * 开始第一个流程  打开 应用
     */
    fun start() {
        App.accessibilityInterface= KuaiShouAccessibility()
        CoreConfig.applicationContext.also {
            it.startActivity(it.packageManager.getLaunchIntentForPackage("com.kuaishou.nebula"))
            CoroutineScope(Dispatchers.IO).launch {
                delay(500)
            }
        }
    }
}