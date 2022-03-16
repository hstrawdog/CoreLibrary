package com.hqq.example.ui.customize.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.graphics.values
import android.graphics.DashPathEffect


/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui.customize.widget
 * @Date : 11:24
 * @Email : qiqiang213@gmail.com
 * @Describe :  记录 手指滑动图片的路径
 */
class BrushView : View {
    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    /**
     *  默认大小
     *
     */
    var defSize = 10f

    /**
     * 背景矩阵
     */
    var bgMatrix: Matrix? = Matrix()

    /**
     *  视图上的画笔
     */
    var viewPaint = Paint().apply {
        style = Paint.Style.STROKE
        setColor(Color.RED)
        isAntiAlias = true
    }

    /**
     * 图片真实画笔  红色
     */
    var realityPaint = Paint().apply {
        style = Paint.Style.STROKE
        setColor(Color.WHITE)
        strokeWidth = defSize
        isAntiAlias = true
    }

    /**
     * 当前路径对象(屏幕上的路径)
     */
    var currentPath: Path = Path()
    var realityPath: Path = Path()

    //只有当移动的距离大于4px时,才在屏幕上绘图
    private val TOUCH_TOLERANCE = 4f
    private var mViewX = 0f
    private var mViewY = 0f

    /**
     *  回调
     */
    var onUpListener: OnUpListener? = null

    /**
     *  1  画笔
     *  2 矩形
     */
    var model = 1

    /**
     *  虚线
     */
    val mPaintCircle = Paint().apply {

        style = Paint.Style.STROKE
        isAntiAlias = true
        setStrokeWidth(3f)
        color = Color.parseColor("#FF3434")
        pathEffect = DashPathEffect(floatArrayOf(15f, 4f), 0f)
    }

    val rectPaint = Paint().apply {
        //设置颜色
        setColor(Color.parseColor("#4dF15454"))
        //设置画笔类型
        setStyle(Paint.Style.FILL);
    }

    /**
     * 按下的点
     */
    var downPoint: PointF? = null

    /**
     *  移动的点 也就是终点
     */
    var movePoint: PointF? = null

    @SuppressLint("NewApi")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (model == 1) {

            canvas.drawPath(currentPath, viewPaint)
        } else {

            if (downPoint != null && movePoint != null) {

                canvas.drawRect(
                    downPoint!!.x, downPoint!!.y, movePoint!!.x, movePoint!!.y, rectPaint
                )
                canvas.drawLine(downPoint!!.x, downPoint!!.y, movePoint!!.x, downPoint!!.y, mPaintCircle)
                canvas.drawLine(downPoint!!.x, downPoint!!.y, downPoint!!.x, movePoint!!.y, mPaintCircle)
                canvas.drawLine(downPoint!!.x, movePoint!!.y, movePoint!!.x, movePoint!!.y, mPaintCircle)
                canvas.drawLine(movePoint!!.x, downPoint!!.y, movePoint!!.x, movePoint!!.y, mPaintCircle)
            }

        }

    }


    override fun onTouchEvent(event: MotionEvent): Boolean {

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                // 重置大小
                currentPath = Path()
                realityPath = Path()
                currentPath.moveTo(event.x, event.y)
                mViewX = event.x
                mViewY = event.y
                var f = getRealityPoint(event.x, event.y)
                realityPath.moveTo(f[0], f[1])

                downPoint = PointF(event.x, event.y)
                movePoint = null
            }
            MotionEvent.ACTION_MOVE -> {
                touchMove(event.x, event.y)
                movePoint = PointF(event.x, event.y)

            }
            MotionEvent.ACTION_CANCEL -> {
//                currentPath.lineTo(event.x, event.y)
//                realityPath.close()

            }
            MotionEvent.ACTION_UP -> {
                // 松开 手指 就需要走一下流程
                onUpListener?.onUp()
            }
        }
        invalidate()
        return true
    }

    /**
     *   矩阵反映射获取真实的 位置
     * @param x Float
     * @param y Float
     * @return FloatArray
     */
    private fun getRealityPoint(x: Float, y: Float): FloatArray {
        val f = floatArrayOf(x, y)
        val newMatrix = Matrix()
        val newMatrix2 = Matrix()
        newMatrix2.set(bgMatrix)
        newMatrix2.invert(newMatrix)
        newMatrix.mapPoints(f)
        return f
    }

    /**
     *  移动手指
     * @param x Float
     * @param y Float
     */
    private fun touchMove(x: Float, y: Float) {
        val dx: Float = Math.abs(x - mViewX)
        val dy: Float = Math.abs(y - mViewY)
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            //二次方贝塞尔曲线

            currentPath.quadTo(mViewX, mViewY, (x + mViewX) / 2, (y + mViewY) / 2)

            var f = getRealityPoint(mViewX, mViewY)
            var f1 = getRealityPoint((x + mViewX), (y + mViewY))
            realityPath.quadTo(f[0], f[1], f1[0] / 2, f1[1] / 2)

            mViewX = x
            mViewY = y
        }
    }

    /**
     *  清除 数据
     */
    fun clean() {
        downPoint = null
        movePoint = null
        currentPath = Path()
        realityPath = Path()
        invalidate()
    }

    /**
     *  获取真实的黑白图
     * @param width Int
     * @param height Int
     * @return Bitmap
     */
    fun getComparedBitmap(width: Int, height: Int): Bitmap {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        canvas.drawColor(Color.BLACK)

        if (model == 1) {

            canvas.drawPath(realityPath, realityPaint)
        } else {
            if (downPoint != null && movePoint != null) {
                var f = getRealityPoint(downPoint!!.x, downPoint!!.y)
                var f2 = getRealityPoint(movePoint!!.x, movePoint!!.y)
                canvas.drawRect(
                    f[0], f[1], f2[0], f2[2], realityPaint
                )
            }

        }



        return bitmap;
    }

    /**
     *  设置  画笔大小
     * @param i Int
     */
    fun setFontSize(i: Int) {
        viewPaint.strokeWidth = i.toFloat()
        // x 的缩放比例
        val scan = bgMatrix?.values()?.get(0)

        if (scan != null) {
            realityPaint.strokeWidth = i.toFloat() / scan.toFloat()
        }
        invalidate()
    }

    interface OnUpListener {
        fun onUp()
    }

}