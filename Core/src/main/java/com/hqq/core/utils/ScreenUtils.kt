package com.hqq.core.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.graphics.Bitmap
import android.os.Build
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.Display
import android.view.Surface
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
     * 设置屏幕为横屏
     *
     * 还有一种就是在Activity中加属性android:screenOrientation="landscape"
     *
     * 不设置Activity的android:configChanges时，切屏会重新调用各个生命周期，切横屏时会执行一次，切竖屏时会执行两次
     *
     * 设置Activity的android:configChanges="orientation"时，切屏还是会重新调用各个生命周期，切横、竖屏时只会执行一次
     *
     * 设置Activity的android:configChanges="orientation|keyboardHidden|screenSize"（4.0以上必须带最后一个参数）时
     * 切屏不会重新调用各个生命周期，只会执行onConfigurationChanged方法
     *
     * @param activity activity
     */
    @SuppressLint("SourceLockedOrientationActivity")
    @JvmStatic
    fun setLandscape(activity: Activity) {
        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }

    /**
     * 设置屏幕为竖屏
     *
     * @param activity activity
     */
    @SuppressLint("SourceLockedOrientationActivity")
    @JvmStatic
    fun setPortrait(activity: Activity) {
        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    /**
     * 判断是否横屏
     *
     * @param context 上下文
     * @return `true`: 是<br></br>`false`: 否
     */
    @JvmStatic
    fun isLandscape(context: Context): Boolean {
        return context.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    }

    /**
     * 判断是否竖屏
     *
     * @param context 上下文
     * @return `true`: 是<br></br>`false`: 否
     */
    @JvmStatic
    fun isPortrait(context: Context): Boolean {
        return context.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
    }

    /**
     * 获取屏幕旋转角度
     *
     * @param activity activity
     * @return 屏幕旋转角度
     */
    @JvmStatic
    fun getScreenRotation(activity: Activity): Int {
        return when (activity.windowManager.defaultDisplay.rotation) {
            Surface.ROTATION_0 -> 0
            Surface.ROTATION_90 -> 90
            Surface.ROTATION_180 -> 180
            Surface.ROTATION_270 -> 270
            else -> 0
        }
    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     *
     * 需要用到上面获取状态栏高度getStatusBarHeight的方法
     *
     * @param activity activity
     * @return Bitmap
     */
    @JvmStatic
    fun captureWithoutStatusBar(activity: Activity): Bitmap {
        val view = activity.window.decorView
        view.isDrawingCacheEnabled = true
        view.buildDrawingCache()
        val bmp = view.drawingCache
        val statusBarHeight = ScreenUtils.getStatusBarHeight(activity)
        val width = ScreenUtils.getScreenWidth(activity)
        val height = ScreenUtils.getScreenHeight(activity)
        val ret = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height - statusBarHeight)
        view.destroyDrawingCache()
        return ret
    }

    /**
     * 获取DisplayMetrics对象
     *
     * @param context 应用程序上下文
     * @return
     */
    @JvmStatic
    fun getDisplayMetrics(context: Context): DisplayMetrics {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics
    }

    /**
     * 获取当前屏幕截图，包含状态栏
     *
     * @param activity activity
     * @return Bitmap
     */
    @JvmStatic
    fun captureWithStatusBar(activity: Activity): Bitmap {
        val view = activity.window.decorView
        view.isDrawingCacheEnabled = true
        view.buildDrawingCache()
        val bmp = view.drawingCache

        val width = ScreenUtils.getScreenWidth(activity)
        val height = ScreenUtils.getScreenHeight(activity)
        val ret = Bitmap.createBitmap(bmp, 0, 0, width, height)
        view.destroyDrawingCache()
        return ret
    }

    /**
     * 得到屏幕的宽
     *
     * @param context 实体
     * @return 设备屏幕的宽度
     */
    @JvmStatic
    fun getScreenWidth(context: Context): Int {
        val wm = (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager)
        return wm.defaultDisplay.width
    }

    /**
     * 得到设备屏幕的宽度
     */
    @JvmStatic
    fun getScreenWidths(context: Context): Int {
        return context.resources.displayMetrics.widthPixels
    }

    /**
     * 得到设备屏幕的高度
     */
    @JvmStatic
    fun getScreenHeights(context: Context): Int {
        return context.resources.displayMetrics.heightPixels
    }

    /**
     * 得到设备的密度
     */
    @JvmStatic
    fun getScreenDensity(context: Context): Float {
        return context.resources.displayMetrics.density
    }

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
    fun getScreenWidth(activity: Activity = CoreConfig.get().currActivity!!): Int {
        val localDisplayMetrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(localDisplayMetrics)
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
    fun getAllScreenHeight(): Int {
        return ScreenHeight.getFullActivityHeight(CoreConfig.applicationContext)
    }

    /**
     * 兼容API获取屏幕信息
     * @param context
     * @return
     */
    fun getDisplay(context: Context): Display? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            getDisplayApiR(context)
        } else {
            getDisplayApiL(context)
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private fun getDisplayApiL(context: Context): Display? {
        val wm: WindowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        return wm.getDefaultDisplay()
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    private fun getDisplayApiR(context: Context): Display? {
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

    /**
     * 底部导航栏的高度
     * @param context Context
     * @return Int
     */
    fun getNavigationBarHeight(context: Context): Int {
        val resources = context.resources
        val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        return if (resourceId != 0) resources.getDimensionPixelSize(resourceId) else 0
    }
}