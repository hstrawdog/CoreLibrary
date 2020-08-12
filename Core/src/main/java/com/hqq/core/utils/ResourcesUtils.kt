package com.hqq.core.utils

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.hqq.core.CoreBuildConfig

/**
 * @Author : huangqiqiang
 * @Package : com.core.library.utils
 * @FileName :   ResourcesUtils
 * @Date : 2018/12/14 0014  上午 11:18
 * @Descrive :
 * @Email :  qiqiang213@gmail.com
 * 资源辅助类
 */
object ResourcesUtils {
    var sResources: Resources? = null

    /**
     * 单利缓存 resources
     *
     * @return
     */
    @get:Synchronized
    val resources: Resources?
        get() {
            if (sResources == null) {
                sResources = CoreBuildConfig.instance.application!!.applicationContext.getResources()
            }
            return sResources
        }

    fun getResources(context: Context): Resources {
        return context.resources
    }

    /**
     * 获取字符串
     *
     * @param context 上下文
     * @param resId   资源ID
     */
    fun getString(context: Context?, @StringRes resId: Int): String {
        return resources!!.getString(resId)
    }

    fun getString(@StringRes resId: Int): String {
        return resources!!.getString(resId)
    }

    /**
     * 获取颜色
     *
     * @param context 上下文
     * @param resId   资源ID
     */
    fun getColor(context: Context?, @ColorRes resId: Int): Int {
        return ContextCompat.getColor(context!!, resId)
    }

    /**
     * 同上
     *
     * @param resId
     * @return
     */
    @kotlin.jvm.JvmStatic
    fun getColor(@ColorRes resId: Int): Int {
        return ContextCompat.getColor(CoreBuildConfig.instance.application!!.applicationContext, resId)
    }

    /**
     * 获取颜色
     *
     * @param context 上下文
     * @param resId   资源ID
     */
    fun getColorStateList(context: Context, @ColorRes resId: Int): ColorStateList {
        return if (Build.VERSION.SDK_INT >= 23) {
            resources!!.getColorStateList(resId, context.theme)
        } else resources!!.getColorStateList(resId)
    }

    /**
     * 获取Drawable
     *
     * @param context 上下文
     * @param resId   资源ID
     */
    fun getDrawable(context: Context?, @DrawableRes resId: Int): Drawable? {
        return ContextCompat.getDrawable(context!!, resId)
    }

    /**
     * 同上
     *
     * @param resId
     * @return
     */
    fun getDrawable(@DrawableRes resId: Int): Drawable? {
        return ContextCompat.getDrawable(CoreBuildConfig.instance.application!!.applicationContext, resId)
    }

    /**
     * 获取尺寸资源
     *
     * @param resId 资源ID
     * @return px
     */
    fun getDimen(@DimenRes resId: Int): Float {
        return resources!!.getDimension(resId)
    }

    /**
     * 获取尺寸资源
     *
     * @param resId 资源ID
     * @return dp
     */
    fun getDimen2dp(@DimenRes resId: Int): Float {
        return ScreenUtils.px2dip(CoreBuildConfig.instance.application, resources!!.getDimension(resId)).toFloat()
    }

    @Deprecated("")
    fun getDimen(context: Context, @DimenRes resId: Int): Float {
        return getResources(context).getDimension(resId)
    }
}