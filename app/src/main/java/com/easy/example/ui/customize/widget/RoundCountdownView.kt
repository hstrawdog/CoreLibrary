package com.easy.example.ui.customize.widget

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import androidx.constraintlayout.widget.ConstraintLayout
import com.easy.core.utils.ResourcesUtils.getDimen
import com.easy.core.utils.log.LogUtils.dInfo
import com.easy.core.utils.log.LogUtils.e
import com.easy.example.R

/**
 * @Author : huangqiqiang
 * @Package : com.easy.example.ui.customize.widget
 * @FileName :   RoundCountdownView
 * @Date : 2019/12/24 0024  下午 5:06
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
class RoundCountdownView : View {
    /**
     * 进度
     */
    private var mSweepAngle = 0f

    /**
     * 开始角度
     */
    private val mStarAngle = 270f

    /**
     * 总时间
     */
    private val mCountdown = 60

    /**
     * 剩余时间
     */
    private var mNowCountdown = mCountdown

    /**
     * 动画
     */
    var mAnimatorSet = ValueAnimator.ofFloat(0f, 360.toFloat())

    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        if (attrs != null) {
        }
        setBackgroundResource(R.color.color_b36d61)
        startCountdownAnimation()
    }

    /**
     * 执行动画
     */
    private fun startCountdownAnimation() {
        mAnimatorSet.interpolator = LinearInterpolator()
        mAnimatorSet.addUpdateListener { animation ->
            mSweepAngle = animation.animatedValue as Float
            mNowCountdown = ((360 - mSweepAngle) / (360 / mCountdown)).toInt()
            dInfo("onAnimationUpdate"+ mNowCountdown)
            invalidate()
        }
        mAnimatorSet.duration = mCountdown * 1000.toLong()
        mAnimatorSet.start()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (parent is ConstraintLayout) {
            var params = layoutParams
            if (params != null) {
                params.height = 0
                params.width = 0
            } else {
                params = ViewGroup.LayoutParams(0, 0)
            }
            layoutParams = params
        }
        val wSpecMode = MeasureSpec.getMode(widthMeasureSpec)
        var wSpecSize = MeasureSpec.getSize(widthMeasureSpec)
        val hSpecMode = MeasureSpec.getMode(heightMeasureSpec)
        var hSpecSize = MeasureSpec.getSize(heightMeasureSpec)
        dInfo("wSpecSize"+ wSpecSize)
        //        if (wSpecMode == MeasureSpec.AT_MOST && hSpecMode == MeasureSpec.AT_MOST) {
//            setMeasuredDimension(300, 300);
//        } else if (wSpecMode == MeasureSpec.AT_MOST) {
//            setMeasuredDimension(300, hSpecSize);
//        } else if (hSpecMode == MeasureSpec.AT_MOST) {
//            setMeasuredDimension(300, 300);
//        }
        wSpecSize = 600
        hSpecSize = 600
        setMeasuredDimension(wSpecSize, hSpecSize)
        dInfo("onMeasure getMeasuredWidth"+ measuredWidth)
        dInfo("onMeasure getMeasuredHeight"+ measuredHeight)
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        dInfo("getWidth"+ width)
        canvasText(canvas)
        canvasBg(canvas)
        cancasArc(canvas)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mAnimatorSet.cancel()
    }

    private fun canvasText(canvas: Canvas) {
        val mPaint = Paint()
        val fontMetrics = Paint.FontMetrics()
        mPaint.getFontMetrics(fontMetrics)
        mPaint.textSize = getDimen(R.dimen.x30)
        val offset = (fontMetrics.descent + fontMetrics.ascent) / 2
        val centerX = (width / 2 - mPaint.measureText(mNowCountdown.toString() + "秒") / 2).toInt()
        val centerY = height / 2
        canvas.drawText(mNowCountdown.toString() + "秒", centerX.toFloat(), centerY - offset, mPaint)
    }

    private fun cancasArc(canvas: Canvas) {
        val paint1 = Paint()
        paint1.isAntiAlias = true
        paint1.color = Color.BLUE
        paint1.style = Paint.Style.STROKE
        paint1.strokeWidth = getDimen(R.dimen.x5)
        canvas.drawArc(0 + mPadding.toFloat(), 0 + mPadding.toFloat(), width - mPadding.toFloat(), width - mPadding.toFloat(), mStarAngle, mSweepAngle, false, paint1)
    }

    var mPadding = 5
    private fun canvasBg(canvas: Canvas) {
        val paint = Paint()
        paint.strokeWidth = 5f
        paint.color = Color.RED
        paint.style = Paint.Style.STROKE
        canvas.drawCircle(width / 2.toFloat(), height / 2.toFloat(), width / 2 - mPadding.toFloat(), paint)
    }
}