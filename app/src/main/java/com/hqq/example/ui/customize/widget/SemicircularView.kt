package com.hqq.example.ui.customize.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.hqq.core.utils.log.LogUtils.e

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui.customize.widget
 * @FileName :   Semicircular
 * @Date : 2020/7/6 0006  上午 11:02
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
class SemicircularView : View {
    private var radius = 100f
    private val mBackgroundArcColor = -0x666667

    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthSpecMode = MeasureSpec.getMode(widthMeasureSpec)
        var widthSpecSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSpecMode = MeasureSpec.getMode(heightMeasureSpec)
        var heightSpecSize = MeasureSpec.getSize(heightMeasureSpec)
        e("widthSpecMode      $widthSpecMode")
        e("widthSpecSize      $widthSpecSize")
        e("heightSpecMode     $heightSpecMode")
        e("heightSpecSize     $heightSpecSize")
        if (widthSpecMode == MeasureSpec.EXACTLY || heightSpecMode == MeasureSpec.EXACTLY) {
            if (widthSpecSize < heightSpecSize) {
                heightSpecSize = widthSpecSize
            } else {
                widthSpecSize = heightSpecSize
            }
        }
        radius = (widthSpecSize - paddingLeft - paddingRight) / 2.toFloat()
        setMeasuredDimension(widthSpecSize, heightSpecSize)
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        drawOvl(canvas, Color.RED)
    }

    private fun drawOvl(canvas: Canvas, color: Int) {
        val mXCenter = width / 2.toFloat()
        val mYCenter = height.toFloat()
        val ovl = RectF()
        ovl.left = mXCenter - radius
        ovl.top = mYCenter / 2 - radius
        ovl.right = mXCenter + radius
        ovl.bottom = mYCenter / 2 - radius + radius * 2
        val mPaint = Paint()
        mPaint.isAntiAlias = true
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = 10f
        //开启显示边缘为圆形
        mPaint.strokeCap = Paint.Cap.ROUND

        // 背景
        mPaint.color = mBackgroundArcColor
        canvas.drawArc(ovl, -180f, -180f, false, mPaint)
        mPaint.color = color
        canvas.drawArc(ovl, -180f, 30f, false, mPaint)
    }
}