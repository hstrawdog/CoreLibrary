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
package com.hqq.album

import android.app.Activity
import android.content.Intent
import com.hqq.album.activity.AlbumDirectoryActivity
import com.hqq.album.activity.AlbumFolderActivity
import com.hqq.album.common.SelectOptions
import java.util.Stack

/**
 * @version V1.0 <描述当前版本功能>
 * @FileName:AppManager.java
 * @author: 黄其强
 * @date: 2016/11/18  9:34
</描述当前版本功能> */
class AppManager private constructor() {
    /**
     * 添加Activity到堆栈
     */
    fun addActivity(activity: Activity?) {
        if (activityStack == null) {
            activityStack = Stack()
        }
        activityStack!!.add(activity)
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    fun finishActivity() {
        val activity = activityStack!!.lastElement()
        finishActivity(activity)
    }

    /**
     * 结束指定的Activity
     */
    fun finishActivity(activity: Activity?) {
//		//应用即将全部关闭，清理缓存
        var activity = activity
        if (activity != null) {
            activityStack!!.remove(activity)
            activity.finish()
            activity = null
        }
    }

    /**
     * 结束所有Activity
     */
    @JvmOverloads
    fun finishAllActivity(isCallBack: Boolean = false) {
        var i = 0
        val size = activityStack!!.size
        while (i < size) {
            if (null != activityStack!![i]) {
                var activity = activityStack!![i]
                if (isCallBack) {
                    if (activity is AlbumDirectoryActivity || activity is AlbumFolderActivity) {
                        val intent = Intent()
                        intent.putParcelableArrayListExtra("data", SelectOptions.instance.selectLocalMedia)
                        activity.setResult(Activity.RESULT_OK, intent)
                    }
                }
                activity?.finish()
            }
            i++
        }
        activityStack!!.clear()
    }

    fun finishAllActivityAndCallBack() {
        finishAllActivity(true)
    }

    companion object {
        private var activityStack: Stack<Activity?>? = null
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
}