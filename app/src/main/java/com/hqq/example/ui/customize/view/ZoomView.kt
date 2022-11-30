package com.hqq.example.ui.customize.view

import android.app.Activity
import android.content.Context
import android.graphics.*
import android.os.Vibrator
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.hqq.core.utils.ResourcesUtils
import com.hqq.example.R
import java.math.BigDecimal


/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui.customize.view
 * @Date : 17:22
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class ZoomView : View {
    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}


    val bigPoint = Paint().apply {
        color = Color.RED
        style = Paint.Style.FILL
    }

    /**
     *  0-15   5为一个大点
     *   微距 多一个
     *
     */
    private val leftPointSize by lazy {
        if (hasMacro) {
            15
        } else {
            10
        }
    }

    val leftPointSpacing = 30f

    val leftPointX = 60f

    /**
     *  是否是微距 状态
     */
    val hasMacro = true

    /**
     *  点击的位置
     */
    val onClickDownPoint = PointF(0f, 0f)

    /**
     *  右边显示的 矩形 范围
     */
    val rightRectF by lazy {
        RectF(leftPointX * 3f, 20f, leftPointX * 5f + 10f, height - 30f)
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawLeftPoint(canvas)
        drawRightPoint(canvas)


    }


    /**
     *    右边
     * @param canvas Canvas
     */
    private fun drawRightPoint(canvas: Canvas) {
        //  透明的背景
        drawRoundRect(canvas)

        //正常显示6个点  微距 显示7个点

        val size = if (hasMacro) {
            7 + 1
        } else {
            6 + 1
        }
        val spacing = rightRectF.height() / size
        val pointSize = ((height - 60) / spacing).toInt()


        var y = if (size > 7) {
            rightRectF.height() - (currSelect * spacing + spacing)

        } else {
            rightRectF.height() - (currSelect * spacing)
        }


        // 大圆 的位置
        val rectF = RectF(rightRectF.centerX() - leftPointSpacing * 2,
            (y - leftPointSpacing * 2f).toFloat(),
            rightRectF.centerX() + leftPointSpacing * 2,
            (y + leftPointSpacing * 2f).toFloat())

        // 小点点
        for (i in 0 until pointSize) {
            var x = rightRectF.centerX()
            var y = spacing + spacing * i
            if (rectF.contains(x, y)) {
            } else {
                canvas.drawCircle(rightRectF.centerX(), spacing + spacing * i, 3f, bigPoint)
            }
        }


        canvas.drawCircle(rightRectF.centerX(), y.toFloat(), leftPointSpacing * 2, Paint().apply {
            color = Color.RED
            style = Paint.Style.STROKE
        })


        var result: Double = BigDecimal(currSelect).setScale(1, BigDecimal.ROUND_HALF_UP).toDouble()
        drawStrTip("${result}X", canvas, rightRectF.centerX(), y.toFloat())

    }


    var array = ArrayList<Float>()

    /**
     *   左边 可以点的区域
     */
    var hasLeftClickRectF = RectF(0f, 0f, 0f, 0f)

    /**
     *   初始化点
     */
    var isInit = false

    /**
     *  当前选择 1
     */
    var currSelect = 1.0

    /**
     *   绘制 左边的点点
     * @param canvas Canvas
     */
    private fun drawLeftPoint(canvas: Canvas) {


        val leftTop = (rightRectF.height() - leftPointSpacing * leftPointSize) / 2f
        val leftBottom = (rightRectF.height() + leftPointSpacing * leftPointSize) / 2f
        hasLeftClickRectF =
            RectF(leftPointX - leftPointSpacing * 1.6f, leftTop - leftPointX / 2, leftPointX + leftPointSpacing * 1.6f, leftBottom + leftPointX / 2)
        // 默认选中  第三个
//        if (!isInit) {
//            onClickDownPoint.x = leftPointX
//            onClickDownPoint.y = leftTop + leftPointSpacing * 10
//            isInit = true
//        }

        array.clear()
        val selectRectF = getSelectRectF(leftTop)


        for (i in 0..leftPointSize) {
            // 当前点的 x y
            val x = leftPointX
            val y = leftTop + leftPointSpacing * i
            if (i % 5 == 0) {
                // 大的点  点击判断 占用  2.5的距离
                drawLeftBigPoint(x, y, i, canvas, selectRectF)
            } else {
                drawLeftSmallPoint(selectRectF, x, y, canvas)
            }
        }
    }

    /**
     *  绘制 大点
     * @param x Float
     * @param y Float
     * @param i Int
     * @param canvas Canvas
     * @param leftTop Float
     */
    private fun drawLeftBigPoint(x: Float, y: Float, i: Int, canvas: Canvas, leftTop: RectF) {
        if (leftTop.contains(x, y)) {
            var result: Double = BigDecimal(currSelect).setScale(1, BigDecimal.ROUND_HALF_UP).toDouble()
            drawStrTip(result.toString() + "X", canvas, x, y)
        } else {
            canvas.drawCircle(x, y, 5f, bigPoint.apply { color = Color.RED })
        }

    }

    /**
     *  绘制小点
     * @param selectRectF RectF
     * @param x Float
     * @param y Float
     * @param canvas Canvas
     */
    private fun drawLeftSmallPoint(selectRectF: RectF, x: Float, y: Float, canvas: Canvas) {
        if (selectRectF.contains(x, y)) {
        } else {
            canvas.drawCircle(x, y, 3f, bigPoint.apply { color = Color.RED })
        }
    }

    /**
     *  绘制 文本
     * @param str String
     * @param canvas Canvas
     * @param x Float
     * @param y Float
     */
    private fun drawStrTip(str: String, canvas: Canvas, x: Float, y: Float) {
        val mPaint = Paint()
        val fontMetrics = Paint.FontMetrics()
        mPaint.color = Color.RED
        mPaint.isAntiAlias = true
        mPaint.textSize = ResourcesUtils.getDimen(R.dimen.x26)
        mPaint.getFontMetrics(fontMetrics)
        val offsetX = mPaint.measureText(str) / 2
        var offsetY = (Math.abs(fontMetrics.ascent)) / 2
        canvas.drawText(str, x - offsetX, y + offsetY, mPaint)
        // 绘制圆
        canvas.drawCircle(x, y, leftPointSpacing * 2, Paint().apply {
            color = Color.RED
            style = Paint.Style.STROKE
            strokeWidth = 5f
            isAntiAlias = true
        })
    }

    /**
     *   获取 选择的点
     * @param leftTop Float
     * @return RectF
     */
    private fun getSelectRectF(leftTop: Float): RectF {
        var selectRectF = RectF(0f, 0f, 0f, 0f)
        var selectPosition = 0;
        for (i in 0..leftPointSize) {
            var x = leftPointX
            var y = leftTop + leftPointSpacing * i
            if (i % 5 == 0) {
                var floatFB = RectF(x - leftPointSpacing * 1.6f,
                    y - leftPointSpacing * 5 / 2 + 1f,
                    x + leftPointSpacing * 1.6f,
                    y + leftPointSpacing * 5 / 2 - 1)

                selectRectF = floatFB
                if (currSelect > 2.5 && selectPosition == 0) {
                    break
                } else if (currSelect > 1.5 && selectPosition == 1) {
                    break
                } else if (currSelect > 0.5 && selectPosition == 2) {
                    break
                } else if (currSelect > 0 && selectPosition == 3) {
                    break
                }
                 selectPosition++

//                if (floatFB.contains(onClickDownPoint.x, onClickDownPoint.y)) {
//                    selectRectF = floatFB
//                    break
//                }
            }
        }
        return selectRectF
    }

    /**
     *  绘制右边的 背景
     * @param canvas Canvas
     */
    private fun drawRoundRect(canvas: Canvas) {
        // 圆角矩形
        val paint = Paint().apply {
            color = Color.parseColor("#1A000000")
            style = Paint.Style.FILL
        }
        canvas.drawRoundRect(rightRectF, 10f, 10f, paint)
    }


    var isMove = false
    var isOnClick = false
    var mIsLongPressed = false
    var mLastMotionX = 0f
    var mLastMotionY = 0f
    var lastDownTime = 0L
    var lastCurr = 0.0
    override fun onTouchEvent(event: MotionEvent): Boolean {

        when (event.action) {
            // 按下
            MotionEvent.ACTION_DOWN -> {
                //  记录按下的点

                if (hasLeftClickRectF.contains(event.x, event.y)) {

                    mLastMotionX = event.x
                    mLastMotionY = event.y
                    lastDownTime = System.currentTimeMillis()
                    isMove = false
                    isOnClick = true
                    clickLeftPosition(event)
                    lastCurr = currSelect
                }else{
                    return false
                }


            }
            MotionEvent.ACTION_MOVE -> {

                var size = event.y - mLastMotionY
                var move = size / hasLeftClickRectF.height() * 7
                currSelect = lastCurr - move

                if (!hasMacro && currSelect < 1) {
                    currSelect = 1.0
                } else if (hasMacro && currSelect <= 0) {
                    currSelect = 0.1
                } else if (currSelect > 6) {
                    currSelect = 6.0
                }
                invalidate()
            }

            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                mIsLongPressed = false
                invalidate()
            }
        }
        return true
    }

    /**
     *  点击左边
     * @param event MotionEvent
     */
    private fun clickLeftPosition(event: MotionEvent) {
        if (hasLeftClickRectF.contains(event.x, event.y)) {
            onClickDownPoint.x = event.x
            onClickDownPoint.y = event.y
        }
        //  判断 点击的是哪个点
        var selectPosition = 0
        val leftTop = (rightRectF.height() - leftPointSpacing * leftPointSize) / 2f
        for (i in 0..leftPointSize) {
            var x = leftPointX
            var y = leftTop + leftPointSpacing * i
            if (i % 5 == 0) {
                var floatFB = RectF(x - leftPointSpacing * 1.6f,
                    y - leftPointSpacing * 5 / 2 + 1f,
                    x + leftPointSpacing * 1.6f,
                    y + leftPointSpacing * 5 / 2 - 1)
                if (floatFB.contains(onClickDownPoint.x, onClickDownPoint.y)) {
                    selectPosition
                    break
                }
                selectPosition++
            }

        }
        when (selectPosition) {
            0 -> {
                currSelect = 6.0
            }
            1 -> {
                currSelect = 2.0
            }
            2 -> {
                currSelect = 1.0
            }
            3 -> {
                currSelect = 0.5
            }
        }
    }


    /**
     * * 判断是否有长按动作发生 * @param lastX 按下时X坐标 * @param lastY 按下时Y坐标 *
     *
     * @param thisX
     * 移动时X坐标 *
     * @param thisY
     * 移动时Y坐标 *
     * @param lastDownTime
     * 按下时间 *
     * @param thisEventTime
     * 移动时间 *
     * @param longPressTime
     * 判断长按时间的阀值
     */
    fun isLongPressed(lastX: Float, lastY: Float, thisX: Float, thisY: Float, lastDownTime: Long, thisEventTime: Long, longPressTime: Long): Boolean {
        val offsetX = Math.abs(thisX - lastX)
        val offsetY = Math.abs(thisY - lastY)
        val intervalTime = thisEventTime - lastDownTime
        return if (offsetX <= 10 && offsetY <= 10 && intervalTime >= longPressTime) {
            true
        } else false
    }


}
