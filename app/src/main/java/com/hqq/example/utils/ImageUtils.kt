package com.hqq.example.utils

import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import com.hqq.core.CoreConfig.Companion.applicationContext
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.utils
 * @Date : 17:55
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class ImageUtils {
    /**
     * 设置 tint 颜色
     *
     * @param imageView
     * @param id 颜色资源id
     * @param imageId  图片资源id
     */
    fun setImageTint(imageView: ImageView, id: Int, imageId: Int) {
        val up = ContextCompat.getDrawable(applicationContext, imageId)
        val drawableUp = DrawableCompat.wrap(up!!)
        DrawableCompat.setTint(drawableUp, ContextCompat.getColor(applicationContext, id))
        imageView.setImageDrawable(drawableUp)
    }

    /**
     *
     * @param imageView ImageView
     * @param isRectangle Boolean 1 原色显示 0灰度显示
     */
    fun setImageRectangle(imageView: ImageView, isRectangle: Boolean) {

        val matrix = ColorMatrix()
        matrix.setSaturation(if (isRectangle) 1f else 0f)
        var filter = ColorMatrixColorFilter(matrix)
        imageView.setColorFilter(filter)
    }
}