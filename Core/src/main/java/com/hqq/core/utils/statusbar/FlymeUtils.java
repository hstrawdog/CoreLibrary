package com.hqq.core.utils.statusbar;

import android.os.Build;
import android.text.TextUtils;

import com.hqq.core.utils.log.LogUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Author : huangqiqiang
 * @Package : com.core.library.utils
 * @FileName :   FlymeUtils
 * @Date : 2018/9/12 0012  下午 2:02
 * @Descrive : TODO
 * @Email :
 */
public class FlymeUtils {
    /**
     * 5.1 的手机 判断不到
     *
     * @return
     */
    public static boolean isFlyme() {
        try {
            // Invoke Build.hasSmartBar()
            final Method method = Build.class.getMethod("hasSmartBar");
            return method != null;
        } catch (final Exception e) {
            return false;
        }
    }

    public static boolean isMeizuFlymeOS() {
        /* 获取魅族系统操作版本标识*/
        String meizuFlymeOSFlag = getSystemProperty("ro.build.display.id", "");
        if (TextUtils.isEmpty(meizuFlymeOSFlag)) {
            return false;
        } else if (meizuFlymeOSFlag.contains("flyme") || meizuFlymeOSFlag.toLowerCase().contains("flyme")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 用来判断  5.1  的手机
     * <p>
     * https://blog.csdn.net/qq_36230279/article/details/80534665
     * 获取系统属性
     * <h3>Version</h3> 1.0
     * <h3>CreateTime</h3> 2016/6/18,9:35
     * <h3>UpdateTime</h3> 2016/6/18,9:35
     * <h3>CreateAuthor</h3> vera
     * <h3>UpdateAuthor</h3>
     * <h3>UpdateInfo</h3> (此处输入修改内容,若无修改可不写.)
     *
     * @param key          ro.build.display.id
     * @param defaultValue 默认值
     * @return 系统操作版本标识
     */
    private static String getSystemProperty(String key, String defaultValue) {
        try {
            Class<?> clz = Class.forName("android.os.SystemProperties");
            Method get = clz.getMethod("get", String.class, String.class);
            return (String) get.invoke(clz, key, defaultValue);
        } catch (ClassNotFoundException e) {
            LogUtils.e("SystemUtil=================>" + e.getMessage());
            return null;
        } catch (NoSuchMethodException e) {
            LogUtils.e("SystemUtil=================>" + e.getMessage());
            return null;
        } catch (IllegalAccessException e) {
            LogUtils.e("SystemUtil=================>" + e.getMessage());
            return null;
        } catch (IllegalArgumentException e) {
            LogUtils.e("SystemUtil=================>" + e.getMessage());
            return null;
        } catch (InvocationTargetException e) {
            LogUtils.e("SystemUtil=================>" + e.getMessage());
            return null;
        }
    }
}
