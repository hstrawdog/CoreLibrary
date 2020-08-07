package com.hqq.core.widget.divider

import androidx.annotation.ColorInt

/**
 * @Author : huangqiqiang
 * @Package : com.core.library.widget.divider
 * @FileName :   SideLine
 * @Date : 2018/7/5 0005  下午 5:14
 * @Descrive : TODO
 * @Email :  qiqiang213@gmail.com
 */
class SideLine(isHave: Boolean, @ColorInt color: Int, widthDp: Float, startPaddingDp: Float, endPaddingDp: Float) {
    var isHave = false

    /**
     * A single color value in the form 0xAARRGGBB.
     */
    var color: Int

    /**
     * 单位px
     */
    var widthDp: Float

    /**
     * startPaddingDp,分割线起始的padding，水平方向左为start，垂直方向上为start
     * endPaddingDp,分割线尾部的padding，水平方向右为end，垂直方向下为end
     */
    var startPaddingDp: Float
    var endPaddingDp: Float

    init {
        this.isHave = isHave
        this.color = color
        this.widthDp = widthDp
        this.startPaddingDp = startPaddingDp
        this.endPaddingDp = endPaddingDp
    }
}