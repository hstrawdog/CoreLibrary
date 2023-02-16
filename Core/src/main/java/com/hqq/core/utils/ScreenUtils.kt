package com.hqq.core.utils

import android.app.Activity
import android.content.Context
import android.os.Build
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.Display
import android.view.WindowManager
import androidx.annotation.RequiresApi
import com.hqq.core.CoreConfig
import java.lang.reflect.Field


/**
 * @Author : huangqiqiang
 * @Package : com.hqq.album.utils
 * @FileName :   ScreenUtils
 * @Date : 2018/6/20 0020  下午 5:56
 * @Email :  593979591@qq.com
 * @Descrive :屏幕相关 单位换算  dp px 转换 状态栏高度
 */
object ScreenUtils {
    /**
     *
     * @param sp Float
     * @return Float
     */
    @JvmStatic
    fun spToPx(sp: Float): Float {
        return spToPx(CoreConfig.get().application?.applicationContext!!, sp)
    }

    /**
     * sp2px
     * @param context Context
     * @param sp Float
     * @return Float
     */
    @JvmStatic
    fun spToPx(context: Context, sp: Float): Float {
        val metrics = context.resources.displayMetrics
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, metrics)
    }

    /**
     * sp2px
     * @param context Context
     * @param spValue Int
     * @return Float
     */
    @JvmStatic
    fun sp2px(context: Context, spValue: Int): Float {
        val fontScale: Float = context.getResources().getDisplayMetrics().scaledDensity
        return ((spValue * fontScale + 0.5f)).toFloat()
    }

    /**
     *
     * @param spValue Int
     * @return Float
     */
    @JvmStatic
    fun sp2px(spValue: Int): Float {
        return sp2px(CoreConfig.get().application?.applicationContext!!, spValue)
    }

    /**
     *
     * @param dpValue Float
     * @return Int
     */
    @JvmStatic
    fun dip2px(dpValue: Float): Int {
        return dip2px(CoreConfig.get().application.applicationContext!!, dpValue)
    }

    /**
     * @param context context
     * @param dpValue dp
     * @return int px
     */
    fun dip2px(context: Context?, dpValue: Float): Int {
        val scale = context!!.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * px转dp
     *
     * @param context context
     * @param pxValue px
     * @return int dp
     */
    fun px2dip(context: Context?, pxValue: Float): Int {
        val scale = context!!.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

    /**
     * 屏幕宽度
     *
     * @param context context
     * @return int
     */
    @JvmStatic
    fun getScreenWidth(context: Context): Int {
        val localDisplayMetrics = DisplayMetrics()
        (context as Activity).windowManager.defaultDisplay.getMetrics(localDisplayMetrics)
        return localDisplayMetrics.widthPixels
    }

    /**
     * 屏幕高度
     *
     * @param context context
     * @return int 屏幕高度减去 状态栏高度
     */
    @JvmStatic
    fun getScreenHeight(context: Context): Int {
        return getAllScreenHeight() - getStatusBarHeight(context)
    }

    /**
     * 获取屏幕高度
     */
    @JvmStatic
    fun  getAllScreenHeight():Int {
        return ScreenHeight.getFullActivityHeight(CoreConfig.applicationContext)
    }


    /**
     * 兼容API获取屏幕信息
     * @param context
     * @return
     */
    fun getDisplay(context:Context):Display? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            getDisplayApiR(context)
        } else {
            getDisplayApiL(context)
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private fun getDisplayApiL(context:Context):Display? {
        val wm:WindowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        return wm.getDefaultDisplay()
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    private fun getDisplayApiR(context:Context):Display? {
        return context.display
    }


    /**
     * 获取物理宽度
     *
     * @param context
     * @return
     */
    @JvmStatic
    fun getScreenXDPI(context: Context): Float {
        val localDisplayMetrics = DisplayMetrics()
        (context as Activity).windowManager.defaultDisplay.getMetrics(localDisplayMetrics)
        return localDisplayMetrics.xdpi
    }

    /**
     * 获取物理高度
     *
     * @param context
     * @return
     */
    @JvmStatic
    fun getScreenYDPI(context: Context): Float {
        val localDisplayMetrics = DisplayMetrics()
        (context as Activity).windowManager.defaultDisplay.getMetrics(localDisplayMetrics)
        return localDisplayMetrics.ydpi
    }

    /**
     * 获取像素密度
     *
     * @param context
     * @return
     */
    @JvmStatic
    fun getScreenDensityDpi(context: Context): Float {
        val localDisplayMetrics = DisplayMetrics()
        (context as Activity).windowManager.defaultDisplay.getMetrics(localDisplayMetrics)
        return localDisplayMetrics.densityDpi.toFloat()
    }


    @JvmStatic
    fun getStatusBarHeight(): Int {
        return getStatusBarHeight(CoreConfig.applicationContext)
    }
    /**
     * 状态栏高度
     *
     * @param context context
     * @return int
     */
    @JvmStatic
    fun getStatusBarHeight(context: Context): Int {
        val c: Class<*>
        val obj: Any
        val field: Field
        val x: Int
        var statusBarHeight = 0
        try {
            c = Class.forName("com.android.internal.R\$dimen")
            obj = c.newInstance()
            field = c.getField("status_bar_height")
            x = field[obj].toString().toInt()
            statusBarHeight = context.resources.getDimensionPixelSize(x)
        } catch (e1: Exception) {
            e1.printStackTrace()
        }
        return statusBarHeight
    }

    /**
     * 获取状态栏高度
     *
     * @param context context
     * @return
     */
    @JvmStatic
    fun getStatusBarHeight4Resources(context: Context?): Int {
        var result = 0
        val resourceId = context!!.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = context.resources.getDimensionPixelSize(resourceId)
        }
        return result
    }
}