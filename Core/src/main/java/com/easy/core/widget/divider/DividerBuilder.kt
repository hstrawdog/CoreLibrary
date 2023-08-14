package com.easy.core.widget.divider

import android.content.Context
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import  com.easy.core.R

/**
 * @Author : huangqiqiang
 * @Package : com.core.library.widget.divider
 * @FileName :   DividerBuilder
 * @Date : 2018/7/5 0005  下午 5:16
 * @Email :  qiqiang213@gmail.com
 * @Descrive :
 */
class DividerBuilder {
    private var leftSideLine: SideLine? = null
    private var topSideLine: SideLine? = null
    private var rightSideLine: SideLine? = null
    private var bottomSideLine: SideLine? = null
    fun setLeftSideLine(isHave: Boolean, @ColorInt color: Int, widthDp: Float, startPaddingDp: Float, endPaddingDp: Float): DividerBuilder {
        leftSideLine = SideLine(isHave, color, widthDp, startPaddingDp, endPaddingDp)
        return this
    }

    fun setLeftSideLine(@ColorInt color: Int, widthDp: Float): DividerBuilder {
        return setLeftSideLine(true, color, widthDp, 0f, 0f)
    }

    fun setLeftSideLine(context: Context, color: Int, widthDp: Int): DividerBuilder {
        return setLeftSideLine(true, ContextCompat.getColor(context, color), context.resources.getDimension(widthDp), 0f, 0f)
    }

    fun setLeftSideLine(context: Context, widthDp: Int): DividerBuilder {
        return setLeftSideLine(true, ContextCompat.getColor(context, R.color.transparent), context.resources.getDimension(widthDp), 0f, 0f)
    }

    fun setTopSideLine(isHave: Boolean, @ColorInt color: Int, widthDp: Float, startPaddingDp: Float, endPaddingDp: Float): DividerBuilder {
        topSideLine = SideLine(isHave, color, widthDp, startPaddingDp, endPaddingDp)
        return this
    }

    fun setTopSideLine(isHave: Boolean, @ColorInt color: Int, widthDp: Float): DividerBuilder {
        return setTopSideLine(isHave, color, widthDp, 0f, 0f)
    }

    fun setTopSideLine(@ColorInt color: Int, widthDp: Float): DividerBuilder {
        return setTopSideLine(true, color, widthDp, 0f, 0f)
    }

    fun setTopSideLine(context: Context, color: Int, widthDp: Int): DividerBuilder {
        return setTopSideLine(true, ContextCompat.getColor(context, color), context.resources.getDimension(widthDp), 0f, 0f)
    }

    fun setTopSideLine(context: Context, widthDp: Int): DividerBuilder {
        return setTopSideLine(true, ContextCompat.getColor(context, R.color.transparent), context.resources.getDimension(widthDp), 0f, 0f)
    }

    fun setRightSideLine(isHave: Boolean, @ColorInt color: Int, widthDp: Float, startPaddingDp: Float, endPaddingDp: Float): DividerBuilder {
        rightSideLine = SideLine(isHave, color, widthDp, startPaddingDp, endPaddingDp)
        return this
    }

    fun setRightSideLine(@ColorInt color: Int, widthDp: Float): DividerBuilder {
        return setRightSideLine(true, color, widthDp, 0f, 0f)
    }

    fun setRightSideLine(context: Context, color: Int, widthDp: Int): DividerBuilder {
        return setRightSideLine(true, ContextCompat.getColor(context, color), context.resources.getDimension(widthDp), 0f, 0f)
    }

    fun setRightSideLine(context: Context, widthDp: Int): DividerBuilder {
        return setRightSideLine(true, ContextCompat.getColor(context, R.color.transparent), context.resources.getDimension(widthDp), 0f, 0f)
    }

    fun setBottomSideLine(isHave: Boolean, @ColorInt color: Int, widthDp: Float, startPaddingDp: Float, endPaddingDp: Float): DividerBuilder {
        bottomSideLine = SideLine(isHave, color, widthDp, startPaddingDp, endPaddingDp)
        return this
    }

    fun setBottomSideLine(@ColorInt color: Int, widthDp: Float): DividerBuilder {
        return setBottomSideLine(true, color, widthDp, 0f, 0f)
    }

    fun setBottomSideLine(context: Context, color: Int, widthDp: Int): DividerBuilder {
        return setBottomSideLine(true, ContextCompat.getColor(context, color), context.resources.getDimension(widthDp), 0f, 0f)
    }

    fun setBottomSideLine(context: Context, widthDp: Int): DividerBuilder {
        return setBottomSideLine(true, ContextCompat.getColor(context, R.color.transparent), context.resources.getDimension(widthDp), 0f, 0f)
    }

    fun create(): Divider {
        //提供一个默认不显示的sideline，防止空指针
        val defaultSideLine = SideLine(false, -0x99999a, 0f, 0f, 0f)
        leftSideLine = if (leftSideLine != null) leftSideLine else defaultSideLine
        topSideLine = if (topSideLine != null) topSideLine else defaultSideLine
        rightSideLine = if (rightSideLine != null) rightSideLine else defaultSideLine
        bottomSideLine = if (bottomSideLine != null) bottomSideLine else defaultSideLine
        return Divider(leftSideLine, topSideLine, rightSideLine, bottomSideLine)
    }
}