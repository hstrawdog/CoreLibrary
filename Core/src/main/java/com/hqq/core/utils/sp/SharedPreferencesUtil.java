package com.hqq.core.utils.sp;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtil {
    public static synchronized void  putObject(Context context, String key, Object object) {
        //PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences sp = context.getSharedPreferences("preferences", Context.MODE_PRIVATE |Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor editor = sp.edit();
        if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else if (object instanceof String) {
            editor.putString(key, (String) object);
        } else {
            editor.putString(key, object.toString());
        }
        editor.apply();
    }

    public static synchronized Object getObject(Context context, String key, Object defaultObject) {
        try {
            //  PreferenceManager.getDefaultSharedPreferences(context); // 会出现空指针=
            SharedPreferences sp = context.getSharedPreferences("preferences", Context.MODE_PRIVATE |Context.MODE_MULTI_PROCESS);
            if (defaultObject instanceof Boolean) {
                return sp.getBoolean(key, (Boolean) defaultObject);
            } else if (defaultObject instanceof Float) {
                return sp.getFloat(key, (Float) defaultObject);
            } else if (defaultObject instanceof Integer) {
                return sp.getInt(key, (Integer) defaultObject);
            } else if (defaultObject instanceof Long) {
                return sp.getLong(key, (Long) defaultObject);
            } else if (defaultObject instanceof String) {
                return sp.getString(key, (String) defaultObject);
            }
            return null;
        } catch (Exception e) {
            return null;
        }

    }

}
