package com.hqq.core.utils.shape;

import android.graphics.drawable.GradientDrawable;

import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;

import com.hqq.core.utils.ResourcesUtils;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.utils.shape
 * @FileName :   BaseShapeBuilder
 * @Date : 2019/12/6 0006  下午 2:32
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public class BaseShapeBuilder {
    GradientDrawable mGradientDrawable;


    /**
     * 构建
     *
     * @return
     */
    public GradientDrawable builder() {
        return mGradientDrawable;
    }


    public BaseShapeBuilder() {
        mGradientDrawable = new GradientDrawable();
    }

    /**
     * 线
     *
     * @return
     */
    public BaseShapeBuilder setLine() {
        mGradientDrawable.setShape(GradientDrawable.LINE);
        return this;
    }

    /**
     * 矩形
     *
     * @return
     */
    public BaseShapeBuilder setRectangle() {
        mGradientDrawable.setShape(GradientDrawable.RECTANGLE);
        return this;
    }

    /**
     * 圆形
     *
     * @return
     */
    public BaseShapeBuilder setOval() {
        mGradientDrawable.setShape(GradientDrawable.OVAL);
        return this;
    }

    /**
     * 颜色
     *
     * @param colorId color文件中的id
     * @return
     */
    public BaseShapeBuilder setColor(@ColorRes int colorId) {
        mGradientDrawable.setColor(ResourcesUtils.getColor(colorId));
        return this;
    }

    /**
     * 圆角大小
     *
     * @param radius px
     * @return
     */
    public BaseShapeBuilder setCornerRadius(float radius) {
        mGradientDrawable.setCornerRadius(radius);
        return this;
    }

    /**
     * 圆角大小
     *
     * @param radiusId 圆角大小 px
     * @return
     */
    public BaseShapeBuilder setCornerRadius(@DimenRes int radiusId) {
        mGradientDrawable.setCornerRadius(ResourcesUtils.getDimen(radiusId));
        return this;
    }

    /**
     * 边框以及颜色
     *
     * @param strokeWidth 边框大小
     * @param strokeColor 边框颜色
     * @return
     */
    public BaseShapeBuilder setStroke(@DimenRes int strokeWidth, @ColorRes int strokeColor) {
        mGradientDrawable.setStroke(strokeWidth, ResourcesUtils.getColor(strokeColor));
        return this;
    }

    /**
     * 边框 颜色透明
     *
     * @param strokeWidth 透明
     * @return
     */
    public BaseShapeBuilder setStroke(@DimenRes int strokeWidth) {
        setStroke(strokeWidth, (android.R.color.transparent));
        return this;
    }


    /**
     * 圆角
     *
     * @param radii 四个角
     * @return
     */
    public BaseShapeBuilder setCornerRadii(float[] radii) {
        mGradientDrawable.setCornerRadii(radii);
        return this;
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
    public BaseShapeBuilder setRadius(float leftTop, float leftBottom, float rightTop, float rightBottom) {
        //左上，右上，右下，左下
        setCornerRadii(new float[]{leftTop, rightTop, rightBottom, leftBottom});
        return this;
    }

    /**
     * 圆角
     *
     * @param leftRadius 左边
     * @return
     */
    public BaseShapeBuilder setLeftRadius(float leftRadius) {
        setCornerRadii(new float[]{leftRadius, 0, 0, leftRadius});
        return this;
    }

    /**
     * 圆角
     *
     * @param rightRadius 右边
     * @return
     */
    public BaseShapeBuilder setRightRadius(float rightRadius) {
        setCornerRadii(new float[]{0, rightRadius, rightRadius, 0});
        return this;
    }


    /**
     * 圆的大小
     *
     * @param size 宽高
     * @return
     */
    public BaseShapeBuilder setSize(int size) {
        mGradientDrawable.setSize(size, size);
        return this;
    }

    /**
     * 设置大小
     *
     * @param width  宽
     * @param height 高
     * @return
     */
    public BaseShapeBuilder setSize(int width, int height) {
        mGradientDrawable.setSize(width, height);
        return this;
    }


}
