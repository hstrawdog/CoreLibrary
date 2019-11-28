package com.hqq.core.utils;

import android.app.Activity;
import android.content.Intent;
import android.util.ArrayMap;
import android.util.SparseArray;

import java.util.Random;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.utils
 * @FileName :   ActivityResultUtils
 * @Date : 2019/6/3 0003  上午 11:58
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 * <p>
 * -  Activity 如何维护相同的两个界面 activity 关闭时候
 * -  句柄是否会出现相同的 如果不相同是否可以考虑下使用句柄作为key
 * -   是否可以考虑下使用注解来维护requestCode
 */
public class ActivityResultUtils {

    ArrayMap<Activity, SparseArray<ActivityForResult>> mActivitySparseArrayArrayMap = new ArrayMap<>();
    Random codeGenerator = new Random();

    public void startActivity(Activity activity, Intent intent, ActivityForResult activityForResult) {
        int requestCode = codeGenerator.nextInt(0x0000FFFF);
        activity.startActivityForResult(intent, requestCode);
        mActivitySparseArrayArrayMap.get(activity).append(requestCode, activityForResult);

    }

    interface ActivityForResult {
        void onActivityResult();
    }


}



