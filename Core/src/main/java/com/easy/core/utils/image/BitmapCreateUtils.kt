package com.easy.core.utils.image

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView

/**
 * @Author : huangqiqiang
 * @Package : com.easy.core.utils.image
 * @Date : 10:44
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
object BitmapCreateUtils {
    /**
     * 该方式原理主要是：View组件显示的内容可以通过cache机制保存为bitmap
     * @param view View
     * @return Bitmap?
     */
    @JvmStatic
    fun createBitmapFromView(view: View): Bitmap? {
        var bitmap: Bitmap? = null
        //开启view缓存bitmap
        view.isDrawingCacheEnabled = true
        //设置view缓存Bitmap质量
        view.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
        //获取缓存的bitmap
        val cache = view.drawingCache
        if (cache != null && !cache.isRecycled) {
            bitmap = Bitmap.createBitmap(cache)
        }
        //销毁view缓存bitmap
        view.destroyDrawingCache()
        //关闭view缓存bitmap
        view.isDrawingCacheEnabled = false
        return bitmap
    }

    /**
     *  view 2 bitmap 第二种方式
     * @param view View
     * @return Bitmap?
     */
    @JvmStatic
    fun createBitmapFromView2(view: View): Bitmap? {
        //是ImageView直接获取
        if (view is ImageView) {
            val drawable: Drawable = (view as ImageView).getDrawable()
            if (drawable is BitmapDrawable) {
                return drawable.bitmap
            }
        }
        view.clearFocus()
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        if (bitmap != null) {
            val canvas = Canvas(bitmap)
            view.draw(canvas)
            canvas.setBitmap(null)
        }
        return bitmap
    }

}
