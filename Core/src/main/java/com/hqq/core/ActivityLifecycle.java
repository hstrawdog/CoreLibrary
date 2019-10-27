package com.hqq.core;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;


import com.hqq.core.utils.log.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core
 * @FileName :   ActivityLifecycle
 * @Date : 2019/6/6 0006  上午 10:20
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public class ActivityLifecycle implements Application.ActivityLifecycleCallbacks {
    private List<Activity> activities = new ArrayList<>();

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        activities.add(activity);
        LogUtils.d("onActivityCreated",activity.getLocalClassName());
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        activities.remove(activity);
        LogUtils.d("onActivityDestroyed",activity.getLocalClassName());

    }

    /**
     * 获取APP中activity栈中最上层一个activity
     *
     * @return
     */
    protected Activity getActivity() {
        if (null == activities || activities.size() == 0) {
            throw new IllegalStateException("Core 未初始化");
        }
        // 获取最上面的 Activity
        for (int i = activities.size() - 1; i >= 0; i--) {
            Activity activity = activities.get(i);
            if (!activity.isFinishing()) {
                return activity;
            }
        }
        throw new IllegalStateException("未获取到栈内对象 当前Activity 异常");
    }


}
