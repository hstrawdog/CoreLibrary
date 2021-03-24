package com.qq.readbook.weight

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

/**
 * @Author : huangqiqiang
 * @Package : com.qq.readbook.weight
 * @Date : 上午 10:24
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class DrawableCenterButton : AppCompatTextView {
    constructor(
        context: Context?, attrs: AttributeSet?,
        defStyle: Int
    ) : super(context!!, attrs, defStyle) {
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs) {
    }

    constructor(context: Context?) : super(context!!) {}

    override fun onDraw(canvas: Canvas) {
        val drawables = compoundDrawables
        if (drawables != null) {
            val drawableLeft = drawables[0]
            if (drawableLeft != null) {
                val textWidth = paint.measureText(text.toString())
                val drawablePadding = compoundDrawablePadding
                var drawableWidth = 0
                drawableWidth = drawableLeft.intrinsicWidth
                val bodyWidth = textWidth + drawableWidth + drawablePadding
                canvas.translate((width - bodyWidth) / 11 * 5, 0f)
            }
        }
        super.onDraw(canvas)
    }
}