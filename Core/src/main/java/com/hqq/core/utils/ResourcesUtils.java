package com.hqq.core.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;

import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;

import com.hqq.core.CoreBuildConfig;

/**
 * @Author : huangqiqiang
 * @Package : com.core.library.utils
 * @FileName :   ResourcesUtils
 * @Date : 2018/12/14 0014  上午 11:18
 * @Descrive :
 * @Email :  qiqiang213@gmail.com
 * 资源辅助类
 */
public class ResourcesUtils {
    public static Resources sResources;

    /**
     * 单利缓存 resources
     *
     * @return
     */
    public static synchronized Resources getResources() {
        if (sResources == null) {
            sResources = CoreBuildConfig.getInstance().getApplication().getResources();
        }
        return sResources;
    }

    public static Resources getResources(Context context) {
        return context.getResources();
    }

    /**
     * 获取字符串
     *
     * @param context 上下文
     * @param resId   资源ID
     */
    public static String getString(Context context, @StringRes int resId) {
        return getResources().getString(resId);
    }

    public static String getString(@StringRes int resId) {
        return getResources().getString(resId);
    }

    /**
     * 获取颜色
     *
     * @param context 上下文
     * @param resId   资源ID
     */
    public static int getColor(Context context, @ColorRes int resId) {
        return ContextCompat.getColor(context, resId);
    }

    /**
     * 同上
     *
     * @param resId
     * @return
     */
    public static int getColor(@ColorRes int resId) {
        return ContextCompat.getColor(CoreBuildConfig.getInstance().getApplication(), resId);
    }

    /**
     * 获取颜色
     *
     * @param context 上下文
     * @param resId   资源ID
     */
    public static ColorStateList getColorStateList(Context context, @ColorRes int resId) {
        if (Build.VERSION.SDK_INT >= 23) {
            return getResources().getColorStateList(resId, context.getTheme());
        }

        return getResources().getColorStateList(resId);
    }

    /**
     * 获取Drawable
     *
     * @param context 上下文
     * @param resId   资源ID
     */
    public static Drawable getDrawable(Context context, @DrawableRes int resId) {
        return ContextCompat.getDrawable(context, resId);
    }

    /**
     * 同上
     *
     * @param resId
     * @return
     */
    public static Drawable getDrawable(@DrawableRes int resId) {
        return ContextCompat.getDrawable(CoreBuildConfig.getInstance().getApplication(), resId);
    }

    /**
     * 获取尺寸资源
     *
     * @param resId 资源ID
     * @return px
     */
    public static float getDimen(@DimenRes int resId) {
        return getResources().getDimension(resId);
    }

    /**
     * 获取尺寸资源
     *
     * @param resId 资源ID
     * @return dp
     */
    public static float getDimen2dp(@DimenRes int resId) {
        return ScreenUtils.px2dip(CoreBuildConfig.getInstance().getApplication(), getResources().getDimension(resId));
    }

    @Deprecated
    public static float getDimen(Context context, @DimenRes int resId) {
        return getResources(context).getDimension(resId);
    }
}
