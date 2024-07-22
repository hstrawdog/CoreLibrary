package com.easy.core.recycle.indicator

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet

/**
 * @Author : huangqiqiang
 * @Package : com.qi.core.banner.indicator
 * @FileName :   CircleIndicatorImpl
 * @Date : 2019/1/3 0003  下午 7:40
 * @Describe :
 * @Email :  qiqiang213@gmail.com
 */
open class CircleIndicatorView @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null,
                                                         defStyleAttr: Int = 0) :
    IndicatorView(context, attrs, defStyleAttr) {


    /**
     * 默认点的半径
     */
    var mDefRadius = 10

    /**
     * 点与点的间隔
     */
    var mDefPointPadding = 10


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // view 的高度  点的高度 +  padding
        var heightMeasureSpec = heightMeasureSpec
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(mDefRadius * 2 + mDefPointPadding + 2, MeasureSpec.EXACTLY)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (mColumn <= 0) {
            return
        }
        if (mModel == 0) {
            var cx = mDefRadius
            for (i in 0 until mColumn) {
                canvas.drawCircle(cx.toFloat(), height / 2.toFloat(), mDefRadius.toFloat(), mPaintPageFill)
                cx += pointSize
            }
            canvas.drawCircle(pointSize * mCurrItem + mDefRadius.toFloat(), height / 2.toFloat(), mDefRadius.toFloat(),
                mPaintFill)
        } else if (mModel == 1) {
            var cx = (width - pointSize * mColumn) / 2
            for (i in 0 until mColumn) {
                canvas.drawCircle(cx.toFloat(), height / 2.toFloat(), mDefRadius.toFloat(), mPaintPageFill)
                cx += pointSize
            }
            canvas.drawCircle((width - pointSize * mColumn) / 2 + (pointSize * mCurrItem).toFloat(),
                height / 2.toFloat(), mDefRadius.toFloat(), mPaintFill)
        } else {
            var cx = width - pointSize * mColumn - mDefRadius
            for (i in 0 until mColumn) {
                canvas.drawCircle(cx.toFloat(), height / 2.toFloat(), mDefRadius.toFloat(), mPaintPageFill)
                cx += pointSize
            }
            canvas.drawCircle(width - pointSize * (mColumn - mCurrItem) - mDefRadius.toFloat(), height / 2.toFloat(),
                mDefRadius.toFloat(), mPaintFill)
        }
    }

    fun setDefPointPadding(defPointPadding: Int) {
        mDefPointPadding = defPointPadding
    }

    fun setDefRadius(defRadius: Int) {
        mDefRadius = defRadius
    }





    init {
        setWillNotDraw(false)
        init()
    }

    open fun init() {
        mPaintPageFill.style = Paint.Style.FILL
        mPaintPageFill.color = mDefColor
        mPaintFill.style = Paint.Style.FILL
        mPaintFill.color = mSelectColor
    }

    /**
     * 点的 宽度
     *
     * @return
     */
    private val pointSize: Int
        get() = mDefRadius * 2 + mDefPointPadding

}