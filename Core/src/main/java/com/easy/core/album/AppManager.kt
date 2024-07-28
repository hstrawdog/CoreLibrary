/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 *//*
 * Create on 2016-11-18 上午9:34
 * FileName: AppManager.java
 * Author: huang qiqiang
 * Contact:
 *//*
 * Create on 2016-11-18 上午9:34
 * FileName: AppManager.java
 * Author: huang qiqiang
 * Contact:
 *//*
 * Create on 2016-11-18 上午9:34
 * FileName: AppManager.java
 * Author: huang qiqiang
 * Contact:
 */
package com.easy.core.album

import android.app.Activity
import com.easy.core.album.common.SelectOptions
import java.util.Stack

/**
 * @version V1.0 <描述当前版本功能>
 * @FileName:AppManager.java
 * @author: 黄其强
 * @date: 2016/11/18  9:34
</描述当前版本功能> */
class AppManager private constructor() {
    companion object {
        private var instance: AppManager? = null
        /**
         * 单一实例
         */
        @get:Synchronized
        val appManager: AppManager?
            get() {
                if (instance == null) {
                    synchronized(AppManager::class.java) { instance = AppManager() }
                }
                return instance
            }
    }


    var activityStack: Stack<Activity> = Stack()

    /**
     * 添加Activity到堆栈
     */
    fun addActivity(activity: Activity?) {
        if (activityStack == null) {
            activityStack = Stack()
        }
        activityStack.add(activity)
    }

    /**
     * 结束指定的Activity
     */
    fun finishActivity(activity: Activity?) {
//		//应用即将全部关闭，清理缓存
        var activity = activity
        if (activity != null) {
            activityStack.remove(activity)
        }
    }

    /**
     * 结束所有Activity
     */
    @JvmOverloads
    fun finishAllActivity(isCallBack: Boolean = false) {
        // 回调数据
        if (isCallBack) {
            SelectOptions.instance.call?.onSelectLocalMedia(SelectOptions.instance.selectLocalMedia)
        }
        // 关闭所有activity
        for (activity in activityStack) {
            activity?.finish()
        }
        activityStack.clear()
    }

    fun finishAllActivityAndCallBack() {
        finishAllActivity(true)
    }

}