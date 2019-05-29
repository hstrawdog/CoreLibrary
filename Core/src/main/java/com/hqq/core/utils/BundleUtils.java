package com.hqq.core.utils;

import android.os.Bundle;
import android.os.Parcelable;

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
    /******************************** getArguments  空判断  ****************************************/
    protected int getArguments2Int(Bundle arguments, String key, int defaultValue) {
        if (arguments != null) {
            return arguments.getInt(key, defaultValue);
        }
        return defaultValue;
    }

    protected <T extends Parcelable> T getArguments2Parcelable(Bundle arguments, String key) {
        if (arguments != null) {
            return arguments.getParcelable(key);
        }
        return null;
    }

    protected <T extends Parcelable> ArrayList<T> arguments2ParcelableArrayList(Bundle arguments, String key) {
        if (arguments != null) {
            return arguments.getParcelableArrayList(key);
        }
        return null;
    }

    protected String arguments2getString(Bundle arguments, String key, String defaultValue) {
        if (arguments != null) {
            return arguments.getString(key, defaultValue);
        }
        return defaultValue;
    }

    protected Object argumentsValue(Bundle arguments, String key, Object defaultValue) {
        if (arguments != null) {

            if (defaultValue instanceof Integer) {
                return arguments.getInt(key, (Integer) defaultValue);
            } else if (defaultValue instanceof String) {
                return arguments.getString(key, (String) defaultValue);
            }
        }
        return defaultValue;
    }
}
