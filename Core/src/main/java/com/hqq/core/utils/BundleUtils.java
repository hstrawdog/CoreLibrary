package com.hqq.core.utils;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.utils
 * @FileName :   BundleUtils
 * @Date : 2019/5/29 0029  下午 5:11
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public class BundleUtils {
    /******************************** Bundle空判断 避免取值中直接奔溃   ****************************************/

    /**
     * 获取bandle
     *
     * @param object
     * @return
     */
    public static Bundle getBundle(Object object) {
        if (object instanceof Fragment) {
            return ((Fragment) object).getArguments();
        } else if (object instanceof Activity) {
            return ((Activity) object).getIntent().getExtras();
        }

        return null;
    }

    /**
     * @param arguments
     * @param key
     * @param defaultValue
     * @return
     */
    public static Object getValue(Bundle arguments, String key, Object defaultValue) {
        if (arguments != null) {

            if (defaultValue instanceof Integer) {
                return arguments.getInt(key, (Integer) defaultValue);
            } else if (defaultValue instanceof String) {
                return arguments.getString(key, (String) defaultValue);
            }
        }
        return defaultValue;
    }

    /**
     * 同上
     *
     * @param fragment
     * @param key
     * @param defaultValue
     * @return
     */
    public static Object getValue(Fragment fragment, String key, Object defaultValue) {
        return getValue(fragment.getArguments(), key, defaultValue);
    }

    /**
     * 同上
     *
     * @param activity
     * @param key
     * @param defaultValue
     * @return
     */
    public static Object getVaue(Activity activity, String key, Object defaultValue) {
        return getValue(activity.getIntent().getExtras(), key, defaultValue);
    }

    /**
     * 获取Int 类型
     *
     * @param arguments
     * @param key
     * @param defaultValue
     * @return
     */
    public static int getInt(Bundle arguments, String key, int defaultValue) {
        if (arguments != null) {
            return arguments.getInt(key, defaultValue);
        }
        return defaultValue;
    }

    /**
     * 获取字符串
     *
     * @param arguments
     * @param key
     * @param defaultValue
     * @return
     */
    public static String getString(Bundle arguments, String key, String defaultValue) {
        if (arguments != null) {
            return arguments.getString(key, defaultValue);
        }
        return defaultValue;
    }

    /**
     * 同上
     *
     * @param arguments
     * @param key
     * @return
     */
    public static String getString(Bundle arguments, String key) {
        if (arguments != null) {
            return arguments.getString(key);
        }
        return "";
    }

    /**
     * 同上
     *
     * @param fragment
     * @param key
     * @return
     */
    public static String getString(Fragment fragment, String key) {

        return getString(fragment.getArguments(), key);
    }

    /**
     * 序列化对象
     *
     * @param arguments
     * @param key
     * @param <T>
     * @return
     */
    public static <T extends Parcelable> T getParcelable(Bundle arguments, String key) {
        if (arguments != null) {
            return arguments.getParcelable(key);
        }
        return null;
    }

    /**
     * 序列化数组对象
     *
     * @param arguments
     * @param key
     * @param <T>
     * @return
     */
    public static <T extends Parcelable> ArrayList<T> getParcelableArrayList(Bundle arguments, String key) {
        if (arguments != null) {
            return arguments.getParcelableArrayList(key);
        }
        return null;
    }


}
