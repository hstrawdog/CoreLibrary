package com.easy.core.recycle.indicator

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import com.easy.core.R
import com.easy.core.utils.ResourcesUtils
import com.easy.core.utils.log.LogUtils

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
    var _indicatorWidth = ResourcesUtils.getDimen(R.dimen.x20)

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
//        mColumn = 10
//        canvas.drawColor(ResourcesUtils.getColor(R.color.yellow))
//       var maxWidth = mColumn * _indicatorWidth + (mColumn - 1) * _indicatorWidth / 2
//        mPaintFill.setColor(ResourcesUtils.getColor(R.color.red))
//        mPaintPageFill.setColor(ResourcesUtils.getColor(R.color.blue))

        var height = (height - paintHeight) / 2

        for (i in 0 until mColumn) {
            var left = i * _indicatorWidth + spacing * i
            var right = left + _indicatorWidth
            when (mModel) {
                0 -> {
                    left = i * _indicatorWidth + spacing * i
                    right = left + _indicatorWidth
                }

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

            }

            var rectF = RectF(left, height, right, height + paintHeight)
//            LogUtils.e(rectF)
            if (i == mCurrItem) {
                canvas.drawRoundRect(rectF, cornerSize, cornerSize, mPaintFill)
            } else {
                canvas.drawRoundRect(rectF, cornerSize, cornerSize, mPaintPageFill)

            }


        }


    }
}
