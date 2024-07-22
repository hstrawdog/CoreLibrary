package com.easy.core.recycle.indicator

import android.content.Context
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt

/**
 * @Author : huangqiqiang
 * @Package : com.easy.core.recycle.indicator
 * @Date : 01:02
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
abstract class IndicatorView : View, Indicator {
    constructor(context: Context?) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    /**
     * 默认非选中的画笔
     */
    protected val mPaintPageFill = Paint(Paint.ANTI_ALIAS_FLAG)

    /**
     * 选中的画笔
     */
    protected val mPaintFill = Paint(Paint.ANTI_ALIAS_FLAG)

    /**
     * 默认颜色
     */
    @ColorInt
    var mDefColor = -0x1

    /**
     * 选中颜色
     */
    @ColorInt
    var mSelectColor = -0x78a7d2

    /**
     * 类型  0 : 左  1 : 中间   2 : 右边
     */
    var mModel = 2


    /**
     * 小圆点的 总数
     */
    var mColumn = 0

    /**
     * 当前选中的 小圆点
     */
    var mCurrItem = 0



    override fun setCurrentItem(item: Int) {
        mCurrItem = item
        invalidate()
    }

    override fun notifyDataSetChanged() {
        postInvalidate()
    }

    override fun setPageColumn(column: Int) {
        mColumn = column
        setCurrentItem(0)
    }



    fun setModel(model: Int) {
        mModel = model
    }

    fun setSelectColor(selectColor: Int) {
        mSelectColor = selectColor
        mPaintFill.color = mSelectColor
    }

    fun setDefColor(defColor: Int) {
        mDefColor = defColor
        mPaintPageFill.color = mDefColor
    }

}
