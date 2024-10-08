package com.easy.core.recycle.indicator

import android.content.Context
import android.graphics.Canvas
import android.graphics.RectF
import android.util.AttributeSet
import com.easy.core.R
import com.easy.core.utils.ResourcesUtils

/**
 * @Author : huangqiqiang
 * @Package : com.easy.core.recycle.indicator
 * @Date : 01:22
 * @Email : qiqiang213@gmail.com
 * @Describe :
 *  矩形指示器
 *  TODO  需要实现 选中比其他的大
 *
 */
class RectangleIndicatorView : IndicatorView {
    constructor(context: Context?) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // view 的高度  点的高度 +  padding
        var heightMeasureSpec = heightMeasureSpec
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(10 * 2 + 10 + 2, MeasureSpec.EXACTLY)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    /**
     * 宽度
     */
    var _indicatorWidth = ResourcesUtils.getDimen(R.dimen.x10)

    /**
     * 高度
     */
    var paintHeight = ResourcesUtils.getDimen(R.dimen.x5)

    /**
     *  圆角大小
     */
    var cornerSize = ResourcesUtils.getDimen(R.dimen.x2)

    /**
     * 间距
     */
    var spacing = _indicatorWidth / 2



    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)


        var height = (height - paintHeight) / 2
        for (i in 0 until mColumn) {
            var left = 0f
            var right = 0f
            when (mModel) {
                1 -> {
                    var lef = (width - mColumn * (_indicatorWidth + spacing)) / 2
                    left = i * (_indicatorWidth + spacing) + lef
                    right = left + _indicatorWidth
                }

                2 -> {
                    var lef = width - mColumn * (_indicatorWidth + spacing)
                    left = i * (_indicatorWidth + spacing) + lef
                    right = left + _indicatorWidth
                }

                else -> {
                    // 默认0
                    left = i * _indicatorWidth + spacing * i
                    right = left + _indicatorWidth
                }
            }

            if (i == mCurrItem) {
                var rectF = RectF(left, height, right + _indicatorWidth * _selectSpeed, height + paintHeight)
                canvas.drawRoundRect(rectF, cornerSize, cornerSize, mPaintFill)
            } else {
                var rectF = if (i > mCurrItem) {
                    RectF(left + _indicatorWidth * _selectSpeed, height, right + _indicatorWidth * _selectSpeed,
                        height + paintHeight)
                } else {
                    RectF(left, height, right, height + paintHeight)
                }
                canvas.drawRoundRect(rectF, cornerSize, cornerSize, mPaintPageFill)
            }


        }


    }
}
