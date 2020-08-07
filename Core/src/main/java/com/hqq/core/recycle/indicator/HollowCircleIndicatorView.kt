package com.hqq.core.recycle.indicator

import android.content.Context
import android.graphics.Paint
import android.util.AttributeSet

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.recycler.indicator
 * @FileName :   HollowCircleIndicatorImpl
 * @Date  : 2019/4/23 0023
 * @Email :  qiqiang213@gmail.com
 * @Descrive : TODO
 */
class HollowCircleIndicatorView : CircleIndicatorView {
    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    /**
     * 边框 默认 3px
     */
    var mStrokeWidth = 3
    override fun init() {
        super.init()
        mPaintPageFill.strokeWidth = mStrokeWidth.toFloat()
        mPaintPageFill.style = Paint.Style.STROKE
    }

    var strokeWidth: Int
        get() = mStrokeWidth
        set(strokeWidth) {
            mStrokeWidth = strokeWidth
            mPaintPageFill.strokeWidth = mStrokeWidth.toFloat()
            postInvalidate()
        }

}