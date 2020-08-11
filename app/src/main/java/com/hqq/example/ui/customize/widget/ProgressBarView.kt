package com.hqq.example.ui.customize.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.RequiresApi
import com.hqq.core.utils.log.LogUtils.e

/**
 * @version V1.0 <描述当前版本功能>
 * @author: huangqiqiang
 * 在此写用途
 * @FileName: com.hqq.core.app.ui.customize.weight.ProgressBarView.java
 * @emain: 593979591@qq.com
 * @date: 2019-06-23 18:41
</描述当前版本功能> */
class ProgressBarView : View {
    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    var mProgress = 10f

    @ColorInt
    var mUnselectedColor = Color.RED

    @ColorInt
    var mSelectedColor = Color.GREEN

    /**
     * 圆角大小
     */
    var mFilletSize = 10
    var mSlide = true

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val paint = Paint()
        paint.isAntiAlias = true
        paint.color = mUnselectedColor
        canvas.drawRoundRect(0f, 0f, width.toFloat(), height.toFloat(), mFilletSize.toFloat(), mFilletSize.toFloat(), paint)
        paint.color = mSelectedColor
        canvas.drawRoundRect(0f, 0f, width / 100.0f * if (mProgress >= 100) 100f else mProgress, height.toFloat(), mFilletSize.toFloat(), mFilletSize.toFloat(), paint)
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        e(event.action)
        when (event.action) {
            MotionEvent.ACTION_MOVE -> if (mSlide) {
                mProgress = event.x / width * 100
                invalidate()
            }
            else -> {
            }
        }
        return event.action != MotionEvent.ACTION_UP || super.dispatchTouchEvent(event)
    }

    fun setProgress(progress: Float) {
        mProgress = progress
    }

    fun setUnselectedColor(unselectedColor: Int) {
        mUnselectedColor = unselectedColor
    }

    fun setSelectedColor(selectedColor: Int) {
        mSelectedColor = selectedColor
    }

    fun setFilletSize(filletSize: Int) {
        mFilletSize = filletSize
    }
}