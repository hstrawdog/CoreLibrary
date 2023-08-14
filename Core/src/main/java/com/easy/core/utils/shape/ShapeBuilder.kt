package com.easy.core.utils.shape

import android.graphics.drawable.GradientDrawable
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes

/**
 * @Author : huangqiqiang
 * @Package : com.easy.core.utils.shape
 * @FileName :   ShapeBuilder
 * @Date : 2019/12/6 0006  下午 4:59
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
object ShapeBuilder {
    /**
     * 绘制圆角矩形 drawable
     *
     * @param fillColor 图形填充色
     * @param radius    图形圆角半径
     * @return 圆角矩形
     */
    @JvmStatic
    fun drawRoundRect(@ColorRes fillColor: Int, @DimenRes radius: Int): GradientDrawable? {
        return BaseShapeBuilder()
                .setRectangle()
                .setColor(fillColor)
                .setCornerRadius(radius)
                .builder()
    }

    /**
     * 绘制圆角矩形 drawable
     *
     * @param fillColor   图形填充色
     * @param radius      图形圆角半径
     * @param strokeWidth 边框的大小
     * @param strokeColor 边框的颜色
     * @return 圆角矩形
     */
    @JvmStatic
    fun drawRoundRect(fillColor: Int, radius: Int, strokeWidth: Int, strokeColor: Int): GradientDrawable? {
        return BaseShapeBuilder()
                .setRectangle()
                .setColor(fillColor)
                .setCornerRadius(radius)
                .setStroke(strokeWidth, strokeColor)
                .builder()
    }

    /**
     * 绘制圆角矩形 drawable
     *
     * @param fillColor   图形填充色
     * @param radii       图形圆角半径
     * @param strokeWidth 边框的大小
     * @param strokeColor 边框的颜色
     * @return 圆角矩形
     */
    @JvmStatic
    fun drawRoundRect(fillColor: Int, radii: FloatArray?, strokeWidth: Int, strokeColor: Int): GradientDrawable? {
        return BaseShapeBuilder()
                .setRectangle()
                .setColor(fillColor)
                .setCornerRadii(radii)
                .setStroke(strokeWidth, strokeColor)
                .builder()
    }

    /**
     * 绘制圆形
     *
     * @param fillColor   图形填充色
     * @param size        图形的大小
     * @param strokeWidth 边框的大小
     * @param strokeColor 边框的颜色
     * @return 圆形
     */
    @JvmStatic
    fun drawCircle(fillColor: Int, size: Int, strokeWidth: Int, strokeColor: Int): GradientDrawable? {
        return BaseShapeBuilder()
                .setOval()
                .setSize(size, size)
                .setColor(fillColor)
                .setStroke(strokeWidth, strokeColor)
                .builder()
    }
}