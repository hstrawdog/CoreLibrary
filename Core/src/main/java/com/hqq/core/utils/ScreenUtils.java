package com.hqq.core.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

import java.lang.reflect.Field;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.album.utils
 * @FileName :   ScreenUtils
 * @Date : 2018/6/20 0020  下午 5:56
 * @Email :  593979591@qq.com
 * @Descrive :屏幕相关 单位换算  dp px 转换 状态栏高度
 */
public class ScreenUtils {
    /**
     * dp2px
     */
    /**
     * @param context context
     * @param dpValue dp
     * @return int px
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px转dp
     *
     * @param context context
     * @param pxValue px
     * @return int dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 屏幕宽度
     *
     * @param context context
     * @return int
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
        return localDisplayMetrics.widthPixels;
    }

    /**
     * 屏幕高度
     *
     * @param context context
     * @return int
     */
    public static int getScreenHeight(Context context) {
        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
        return localDisplayMetrics.heightPixels - getStatusBarHeight(context);
    }

    /**
     * 状态栏高度
     *
     * @param context context
     * @return int
     */
    public static int getStatusBarHeight(Context context) {
        Class<?> c;
        Object obj;
        Field field;
        int x, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }

    /**
     * 获取状态栏高度
     *
     * @param context context
     * @return
     */
    public static int getStatusBarHeight4Resources(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

}
