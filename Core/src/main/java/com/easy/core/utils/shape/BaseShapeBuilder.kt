package com.easy.core.utils.shape

import android.R
import android.graphics.drawable.GradientDrawable
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import com.easy.core.utils.ResourcesUtils

/**
 * @Author : huangqiqiang
 * @Package : com.easy.core.utils.shape
 * @FileName :   BaseShapeBuilder
 * @Date : 2019/12/6 0006  下午 2:32
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class BaseShapeBuilder {
    var mGradientDrawable: GradientDrawable

    /**
     * 构建
     *
     * @return
     */
    fun builder(): GradientDrawable {
        return mGradientDrawable
    }

    /**
     * 线
     *
     * @return
     */
    fun setLine(): BaseShapeBuilder {
        mGradientDrawable.shape = GradientDrawable.LINE
        return this
    }

    /**
     * 矩形
     *
     * @return
     */
    fun setRectangle(): BaseShapeBuilder {
        mGradientDrawable.shape = GradientDrawable.RECTANGLE
        return this
    }

    /**
     * 圆形
     *
     * @return
     */
    fun setOval(): BaseShapeBuilder {
        mGradientDrawable.shape = GradientDrawable.OVAL
        return this
    }

    /**
     * 颜色
     *
     * @param colorId color文件中的id
     * @return
     */
    fun setColor(@ColorRes colorId: Int): BaseShapeBuilder {
        mGradientDrawable.setColor(ResourcesUtils.getColor(colorId))
        return this
    }

    /**
     * 圆角大小
     *
     * @param radius px
     * @return
     */
    fun setCornerRadius(radius: Float): BaseShapeBuilder {
        mGradientDrawable.cornerRadius = radius
        return this
    }

    /**
     * 圆角大小
     *
     * @param radiusId 圆角大小 px
     * @return
     */
    fun setCornerRadius(@DimenRes radiusId: Int): BaseShapeBuilder {
        mGradientDrawable.cornerRadius = ResourcesUtils.getDimen(radiusId)
        return this
    }

    /**
     * 边框以及颜色
     *
     * @param strokeWidth 边框大小
     * @param strokeColor 边框颜色
     * @return
     */
    fun setStroke(@DimenRes strokeWidth: Int, @ColorRes strokeColor: Int): BaseShapeBuilder {
        mGradientDrawable.setStroke(strokeWidth, ResourcesUtils.getColor(strokeColor))
        return this
    }

    /**
     * 边框 颜色透明
     *
     * @param strokeWidth 透明
     * @return
     */
    fun setStroke(@DimenRes strokeWidth: Int): BaseShapeBuilder {
        setStroke(strokeWidth, R.color.transparent)
        return this
    }

    /**
     * 圆角
     *
     * @param radii 四个角
     * @return
     */
    fun setCornerRadii(radii: FloatArray?): BaseShapeBuilder {
        mGradientDrawable.cornerRadii = radii
        return this
    }

    /**
     * 四个圆角
     *
     * @param leftTop     左上
     * @param leftBottom  左下
     * @param rightTop    右上
     * @param rightBottom 右下
     * @return
     */
    fun setRadius(leftTop: Float, leftBottom: Float, rightTop: Float, rightBottom: Float): BaseShapeBuilder {
        //左上，右上，右下，左下
        setCornerRadii(floatArrayOf(leftTop, rightTop, rightBottom, leftBottom))
        return this
    }

    /**
     * 圆角
     *
     * @param leftRadius 左边
     * @return
     */
    fun setLeftRadius(leftRadius: Float): BaseShapeBuilder {
        setCornerRadii(floatArrayOf(leftRadius, 0f, 0f, leftRadius))
        return this
    }

    /**
     * 圆角
     *
     * @param rightRadius 右边
     * @return
     */
    fun setRightRadius(rightRadius: Float): BaseShapeBuilder {
        setCornerRadii(floatArrayOf(0f, rightRadius, rightRadius, 0f))
        return this
    }

    /**
     * 圆的大小
     *
     * @param size 宽高
     * @return
     */
    fun setSize(size: Int): BaseShapeBuilder {
        mGradientDrawable.setSize(size, size)
        return this
    }

    /**
     * 设置大小
     *
     * @param width  宽
     * @param height 高
     * @return
     */
    fun setSize(width: Int, height: Int): BaseShapeBuilder {
        mGradientDrawable.setSize(width, height)
        return this
    }

    init {
        mGradientDrawable = GradientDrawable()
    }
}