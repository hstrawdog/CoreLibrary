/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

/*
 * Create on 2016-11-18 上午9:34
 * FileName: AppManager.java
 * Author: huang qiqiang
 * Contact:
 */

/*
 * Create on 2016-11-18 上午9:34
 * FileName: AppManager.java
 * Author: huang qiqiang
 * Contact:
 */

/*
 * Create on 2016-11-18 上午9:34
 * FileName: AppManager.java
 * Author: huang qiqiang
 * Contact:
 */

package com.hqq.album;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.hqq.album.activity.AlbumDetailActivity;
import com.hqq.album.activity.AlbumDirectoryActivity;
import com.hqq.album.activity.AlbumFolderActivity;
import com.hqq.album.common.SelectOptions;

import java.util.Stack;


/**
 * @version V1.0 <描述当前版本功能>
 * @FileName:AppManager.java
 * @author: 黄其强
 * @date: 2016/11/18  9:34
 */
public class AppManager {

    private static Stack<Activity> activityStack;
    private static AppManager instance;

    private AppManager() {
    }

    /**
     * 单一实例
     */
    public synchronized static AppManager getAppManager() {
        if (instance == null) {
            synchronized (AppManager.class) {
                instance = new AppManager();
            }
        }
        return instance;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
//		//应用即将全部关闭，清理缓存
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        finishAllActivity(false);
    }

    public void finishAllActivity(boolean isCallBack) {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                Activity activity = activityStack.get(i);
                if (isCallBack) {
                    if (activity instanceof AlbumDirectoryActivity ||  activity instanceof AlbumFolderActivity) {
                        Intent intent = new Intent();
                        intent.putParcelableArrayListExtra("data", SelectOptions.getInstance().getSelectLocalMedia());
                        activity.setResult(Activity.RESULT_OK, intent);
                    }
                }
                activity.finish();
                activity = null;
            }
        }
        activityStack.clear();
    }

    public void finishAllActivityAndCallBack() {
        finishAllActivity(true);
    }
}