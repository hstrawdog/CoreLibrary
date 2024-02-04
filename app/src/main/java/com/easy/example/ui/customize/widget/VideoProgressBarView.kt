package com.easy.example.ui.customize.widget

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.easy.example.R

/**
 * @Author : huangqiqiang
 * @Package : com.easy.example.ui.customize.widget
 * @Date : 17:38
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class VideoProgressBarView : View {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    /**
     *  开始点的数值
     */
    var _startPosition = 0
        set(value) {
            field = value
            invalidate()
        }

    /**
     *  开始与结束的 空间
     */
    var spacingPoint = 10
        set(value) {
            field = value
            invalidate()
        }

    /**
     *  最大值
     */
    var maxPoint = 100
        set(value) {
            field = value
            invalidate()
        }

    /**
     *    滑块的高度
     */
    var barHeight = 20f

    /**
     *  设置 当前进度
     */
    var currPoint = 0
        set(value) {
            field = value
            invalidate()

        }

    var padding = 20f

    var b = BitmapFactory.decodeResource(resources, R.mipmap.iv_subscript)

    var _videoProgressBarViewListener: VideoProgressBarViewListener? = null

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawRoundRect(RectF(padding, padding, width.toFloat() - padding, barHeight + padding), padding, 10f, Paint().apply {
            color = Color.WHITE
        })
        var proportion = (width.toFloat() - padding * 2) / maxPoint.toFloat()

        var f =
            RectF(padding + _startPosition.toFloat() * proportion, padding, padding + (_startPosition + spacingPoint).toFloat() * proportion, barHeight + padding)


        canvas.drawRoundRect(f, 10f, 10f, Paint().apply {
            color = Color.RED
        })


        canvas.drawCircle(padding + currPoint * proportion, padding + barHeight / 2, barHeight / 2 + 3, Paint().apply {
            color = Color.RED

        })


        canvas.drawBitmap(b, padding + _startPosition.toFloat() * (proportion) - b.width / 2, barHeight + padding, Paint())

        canvas.drawBitmap(b, padding + (_startPosition + spacingPoint).toFloat() * (proportion) - b.width / 2, barHeight + padding, Paint())


    }

    var isClick = false
    override fun onTouchEvent(event: MotionEvent): Boolean {

        when (event.action) {

            MotionEvent.ACTION_DOWN -> {
                var proportion = (width.toFloat() - padding * 2) / maxPoint.toFloat()

                var x = padding + _startPosition.toFloat() * proportion - b.width

                var endx = padding + (_startPosition + spacingPoint).toFloat() * proportion

                var rect = RectF(x, padding, endx, barHeight + b.height + padding)
                isClick = rect.contains(event.x, event.y)
                return true
            }

            MotionEvent.ACTION_MOVE -> {
                if (isClick) {
                    _startPosition = (event.x / (width / maxPoint)).toInt()
                    if (_startPosition <= 0) {
                        _startPosition = 0
                    } else if (_startPosition > maxPoint - spacingPoint) {
                        _startPosition = maxPoint - spacingPoint
                    }
                    _videoProgressBarViewListener?.onChange(_startPosition)
                }
                return true

            }

            MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> {
                isClick = false
                _videoProgressBarViewListener?.onMoveUp()

            }

        }
        return super.onTouchEvent(event)
    }


    interface VideoProgressBarViewListener {
        fun onChange(i: Int)
        fun onMoveUp()
    }

}