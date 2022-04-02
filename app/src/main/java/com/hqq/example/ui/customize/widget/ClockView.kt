package com.hqq.example.ui.customize.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.hqq.core.utils.log.LogUtils.dInfo
import java.util.*

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui.customize.widget
 * @FileName :   ClockView
 * @Date : 2019/11/15 0015  下午 4:42
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
class ClockView : View {
    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val paint = drawBg(canvas)
        paint.color = Color.RED
        drawSecond(canvas, paint)
        drawMinute(canvas, paint)
        drawHour(canvas, paint)
        postInvalidateDelayed(1000)
    }

    private fun drawMinute(canvas: Canvas, paint: Paint) {
        val minute = Calendar.getInstance()[Calendar.MINUTE]
        canvas.save()
        canvas.rotate(6 * minute.toFloat(), 0f, width / 2.toFloat())
        canvas.drawLine(0f, height / 2 + 30.toFloat(), 0f, height / 2 - 80.toFloat(), paint)
        canvas.restore()
    }

    private fun drawHour(canvas: Canvas, paint: Paint) {
        val hour = Calendar.getInstance()[Calendar.HOUR]
        dInfo("--------------------   $hour")
        canvas.save()
        canvas.rotate(30 * hour.toFloat(), 0f, width / 2.toFloat())
        canvas.drawLine(0f, height / 2 + 30.toFloat(), 0f, height / 2 - 60.toFloat(), paint)
        canvas.restore()
    }

    private fun drawSecond(canvas: Canvas, paint: Paint) {
        val second = Calendar.getInstance()[Calendar.SECOND]
        dInfo("--------------------   $second")
        canvas.save()
        canvas.rotate(6 * second.toFloat(), 0f, width / 2.toFloat())
        canvas.drawLine(0f, height / 2 + 30.toFloat(), 0f, height / 2 - 180.toFloat(), paint)
        canvas.restore()
    }

    private fun drawBg(canvas: Canvas): Paint {
        val paint = Paint()
        paint.color = Color.GRAY
        paint.strokeWidth = 5.0f
        paint.isAntiAlias = true //设置抗锯齿
        canvas.translate(width / 2.toFloat(), 0f)
        canvas.save()
        for (i in 0..3) {
            canvas.drawLine(0f, 0f, 0f, 100f, paint)
            canvas.rotate(90f, 0f, width / 2.toFloat())
        }
        for (i in 0..11) {
            canvas.drawLine(0f, 0f, 0f, 40f, paint)
            canvas.rotate(30f, 0f, width / 2.toFloat())
        }
        for (i in 0..59) {
            canvas.drawLine(0f, 0f, 0f, 3f, paint)
            canvas.rotate(6f, 0f, width / 2.toFloat())
        }
        canvas.drawCircle(0f, height / 2.toFloat(), 20f, paint)
        canvas.restore()
        return paint
    }
}