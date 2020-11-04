package com.hqq.example.ui.customize.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui.customize.widget
 * @Date  : 下午 4:27
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
class VolDiskView : View {

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initView(context, attrs)
    }

    private fun initView(context: Context?, attrs: AttributeSet?) {


    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        centerY = height / 2F
        centerX = width / 2F
        radius = (width - 3 * scaleWidth) / 2

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.let {
            drawScale(it)
        }
    }
    /**
     * 旋钮半径
     */
    var radius: Float = 0F

    /**
     * 控件中心点
     */
    var centerX: Float = 0F
    var centerY: Float = 0F
    /**
     * 刻度线长度
     */
    var scaleWidth: Float = 20F
    /**
     * 刻度间的夹角
     */
    var scaleSpace: Float = 10F
    var paint: Paint = Paint()
    private fun drawScale(canvas: Canvas) {
        paint.color = Color.GRAY
        var scaleCount = 360 / scaleSpace.toInt()
        for (i in 0 until scaleCount) {
            canvas.drawRect(width - scaleWidth, centerY - 4F, width.toFloat(), centerY + 4F, paint)
            canvas.rotate(scaleSpace, centerX, centerY)
        }
        canvas.drawCircle(centerX, centerY, radius, paint)

    }

}