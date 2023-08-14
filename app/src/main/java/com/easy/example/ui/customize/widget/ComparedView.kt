package com.easy.example.ui.customize.widget

import android.content.Context
import android.graphics.*
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewTreeObserver
import androidx.core.graphics.toRect
import androidx.core.graphics.values
import com.easy.core.utils.ScreenUtils
import com.easy.core.utils.image.BitmapUtils
import com.easy.core.utils.log.LogUtils
import com.easy.example.R


/**
 * @Author : huangqiqiang
 * @Package : com.easy.example.ui.customize.widget
 * @Date : 14:48
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class ComparedView : View, View.OnTouchListener {
    constructor(context: Context?) : super(context) {
        initData()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initData()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    ) {
        initData()

    }

    /**
     *  画笔
     */
    var mPaint = Paint()

    /**
     * 起始点
     */
    var pointX = 0

    /**
     *  按钮图片
     */
    var menuBitmap: Bitmap = BitmapFactory.decodeResource(resources, R.mipmap.ic_gq_fenge)

    /**
     * 美化后的图片
     * 需要与原图大小保持一致
     * 在上一步进行缩放
     */
    var beautifyBitmap: Bitmap? = null

    /**
     * 美化前的图片
     */
    var originalBitmap: Bitmap? = null
        set(value) {
            field = value
            if (field == null) {
                matrixNow.reset()
                mInitializationMatrix.reset()
            } else {
                var top = ((height - originalBitmap!!.height).toFloat() / 2f)
                val l = Math.max(0f, (width - beautifyBitmap!!.width) / 2f)
                matrixNow?.setTranslate(l, top)
                mInitializationMatrix.setTranslate(l, top)
                invalidate()
            }


        }


    /**
     *  美化后图片的实际大小
     */
    var beautifyBitmapHeight = 0f

    var beautifyBitmapWidth = 0f

    /**
     *  x 移动距离
     */
    var moveX = 0f

    /**
     * 按下的X
     */
    var downX = 0f

    /**
     * 按下的Y
     */
    var downY = 0f;

    /**
     *  是否点击的是滑动按钮
     */
    var isContain = false

    /**
     *  -1  不显示
     *  0 识别状态
     *  1  对比状态
     */
    var status = -1
        set(value) {
            field = value
            invalidate()
        }


    init {
        status = 1
        originalBitmap = BitmapUtils.zoomImg(
            BitmapFactory.decodeResource(resources, R.mipmap.a1), ScreenUtils.getScreenWidth()
        )
        beautifyBitmap = BitmapUtils.zoomImg(
            BitmapFactory.decodeResource(resources, R.mipmap.a2), ScreenUtils.getScreenWidth()
        )
    }


    init {
        mPaint.setAntiAlias(true)
        mPaint.setStyle(Paint.Style.FILL)
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (status != -1) {

            // 位置线
            var menuY = (height / 2 - menuBitmap.height).toFloat()
            val menuX = width / 2 - menuBitmap.width / 2 + moveX + menuBitmap.width / 2
            _menuX = menuX
            _menuY = menuY

            if (status == 1) {
                val topf = floatArrayOf(_menuX, 0f)
                val bottomf = floatArrayOf(0f, originalBitmap!!.height.toFloat())

                matrixNow.mapPoints(topf)
                matrixNow.mapPoints(bottomf)
                drawBitmap(canvas, _menuX.toInt())


                var top = Math.max(topf[1], 0f)
                var bottom = Math.min(bottomf[1], height.toFloat())

                _menuY = (bottom - top) / 2 + top

                drawMenu(canvas, top, bottom)
                drawCanvasText(_menuX.toInt(), canvas, top)
            }

        }

    }

    /**
     *  图片
     * @param canvas Canvas
     * @param centerX Int
     */
    private fun drawBitmap(canvas: Canvas, centerX: Int) {
        // 原始图
        if (originalBitmap != null && beautifyBitmap != null) {


            drawOriginalImage(centerX, canvas)

            drawBeautifyBitmap(centerX, canvas)


        }

    }

    private fun drawBeautifyBitmap(centerX: Int, canvas: Canvas) {

        val newR = Math.min(originY2BitmapY(centerX.toFloat()), beautifyBitmap!!.width.toFloat())
        var src = Rect(0, 0, newR.toInt(), beautifyBitmap!!.height)
        // 显示的大小
        var dst = RectF(0f, 0f, centerX.toFloat(), beautifyBitmap!!.height.toFloat())
        matrixNow.mapRect(dst)
        dst.right = centerX.toFloat()
        canvas.drawBitmap(beautifyBitmap!!, src, dst, Paint())

    }

    private fun drawOriginalImage(centerX: Int, canvas: Canvas) {

        val l = 0f
        val r = beautifyBitmap!!.width + l
        val t = 0f
        val b = beautifyBitmap!!.height

        var dst = RectF(centerX.toFloat(), t, r, b.toFloat())

        var src = RectF(
            originY2BitmapY(centerX.toFloat()), 0f, r.toFloat(), originalBitmap!!.height.toFloat()
        )
        //            canvas.translate(l.toFloat(), 0f)

        LogUtils.e("-----------------")
        LogUtils.e(" src ${src}")
        LogUtils.e(" dst ${dst}")
        //            matrixNow.mapRect(src)
        matrixNow.mapRect(dst)
        dst.left = centerX.toFloat()

        LogUtils.e(
            "matrixNow  :       ${
                matrixNow.values()
                    .toList()
                    .toString()
            }"
        )
        LogUtils.e(" src ${src}")
        LogUtils.e(" dst ${dst}")
        canvas.drawBitmap(originalBitmap!!, src.toRect(), dst, Paint())
    }

    /**
     * 根据屏幕坐标点 反映射出 像素坐标
     * @return Float
     */
    private fun originY2BitmapY(x: Float): Float {
        var newMatrix = Matrix()
        var newMatrix2 = Matrix()
        newMatrix2.set(matrixNow)
        newMatrix2.invert(newMatrix)
        var f = floatArrayOf(x, 0f)
        newMatrix.mapPoints(f)
        return f[0]
    }


    var _menuX = 0f
    var _menuY = 0f

    /**
     *  滑动按钮
     * @param canvas Canvas
     * @param menuX Float
     * @param menuY Int
     */
    private fun drawMenu(canvas: Canvas, top: Float, bottom: Float) {

        canvas.drawLine(_menuX, top, _menuX, bottom, Paint().apply {
            setColor(Color.parseColor("#F73838"))
            mPaint.setStyle(Paint.Style.FILL)
            strokeWidth = 3f

        })
        canvas.drawBitmap(menuBitmap, _menuX - menuBitmap.width / 2, _menuY, Paint())
    }

    /**
     * 对比文字
     * @param centerX Int
     * @param canvas Canvas
     */
    private fun drawCanvasText(centerX: Int, canvas: Canvas, top: Float) {
        var x1 = 0
        if (originalBitmap != null) {
            x1 = Math.max(0, (width - originalBitmap!!.width) / 2)
        }


        //优化后
        if (centerX > ((width - originalBitmap!!.width) / 2) + 80) {
            var x = Math.max(x1.toFloat(), 0f)

            canvas.drawRect(Rect(x.toInt(), top.toInt(), (x.toInt() + 80), (top + 40).toInt()), Paint().apply {
                setColor(Color.parseColor("#80000000"))
                style = Paint.Style.FILL
            })
            canvas.drawText("优化后", x, top + 25f, TextPaint().apply {
                color = Color.WHITE
                textSize = 24f
            })
        }
        //优化前
        canvas.drawRect(Rect(centerX, top.toInt(), (centerX + 80), (top + 40).toInt()), Paint().apply {
            setColor(Color.parseColor("#80000000"))
            style = Paint.Style.FILL
        })
        canvas.drawText("优化前", centerX.toFloat(), top + 25f, TextPaint().apply {
            color = Color.WHITE
            textSize = 24f
        })
    }


    private fun containMenu(downX: Float, downY: Float): Boolean {
        var rect = RectF(
            _menuX.toFloat() - menuBitmap.width,
            _menuY.toFloat(),
            _menuX + menuBitmap.height,
            (_menuY + menuBitmap.height).toFloat()
        )

        return rect.contains(downX, downY)

    }


    private val support_touch = true //支持触摸事件

    private var mode = 0 // 初始状态

    private val MODE_DRAG = 1 //平移

    private val MODE_ZOOM = 2 //缩放


    private val MAX_SCALE = 4f
    val MIN_SCALE = 1f //最大放大倍数，最小放大倍数

    /**
     * //total_scale缩放范围2-1，当小于1回弹到1；当大于2回弹到2
     */
    var total_scale = MIN_SCALE

    var current_scale: kotlin.Float = 0f

    private val matrixNow: Matrix = Matrix()
    private val matrixBefore = Matrix()
    private val mInitializationMatrix = Matrix() //初始缩放值


    private val actionDownPoint = PointF() //点击点

    private val dragPoint = PointF() //平移点

    private val startPoint = PointF() //滑动点

    private val mInitializationScalePoint = PointF() //初始缩放点

    private val mCurrentScalePoint = PointF(0f, 0f) //当前缩放点

    private var startDis //滑动开始距离
            = 0f

    /** 两个手指的中间点  */
    private var midPoint = PointF(0f, 0f)


    private fun initData() {
        if (support_touch) {
            setOnTouchListener(this)
        }


        this.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                viewTreeObserver.removeOnGlobalLayoutListener(this)
                val viewLocation = IntArray(2)
                getLocationInWindow(viewLocation)
                val viewX = viewLocation[0] // x 坐标；有bug，在viewpage中。
                val viewY = viewLocation[1] // y 坐标
                mInitializationScalePoint[(width / 2).toFloat()] = (viewY + height / 2).toFloat() //初始化缩放位置
                Log.i(
                    "yangxun", "控件 宽：" + mInitializationScalePoint.x + "高：" + mInitializationScalePoint.y
                )
            }
        })
    }


//    override fun onDraw(canvas: Canvas) {
//        canvas.drawColor(Color.BLACK) //清空画布
//        if (matrixNow != null) {
//            canvas.concat(matrixNow)
//            //            canvas.setMatrix(matrixNow);//显示有问题
//        }
//        super.onDraw(canvas)
//    }

    fun setImageMatrix(matrix: Matrix?) {
        matrixNow.set(matrix)
        invalidate()
    }


    fun resetImageMatrix() {
        matrixNow.set(mInitializationMatrix)
        invalidate()
    }

    //最小重置数值
    private fun resetToMinStatus() {
        mCurrentScalePoint[0f] = 0f
        total_scale = MIN_SCALE
    }

    //最大重置数值
    private fun resetToMaxStatus() {
        total_scale = MAX_SCALE
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (status != -1) {


        }
        return super.onTouchEvent(event)
    }

    override fun onTouch(v: View?, event: MotionEvent): Boolean {
        if (total_scale != 1f) {
            parent.requestDisallowInterceptTouchEvent(true) //触摸事件请求拦截
        }
        when (event.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {
                mode = MODE_DRAG
                matrixBefore.set(getImageMatrix())
                matrixNow.set(getImageMatrix())
                dragPoint[event.x] = event.y
                actionDownPoint[event.x] = event.y
                downX = event.x
                downY = event.y
                isContain = containMenu(downX, downY)
            }
            MotionEvent.ACTION_POINTER_DOWN -> {
                parent.requestDisallowInterceptTouchEvent(true) //触摸事件请求拦截
                mode = MODE_ZOOM
                startPoint[event.x] = event.y
                startDis = distance(event)
                /** 计算两个手指间的中间点  */
                if (startDis > 10f) {
                    //记录当前ImageView的缩放倍数
                    matrixBefore.set(getImageMatrix())
                    matrixNow.set(getImageMatrix())
                }
            }
            MotionEvent.ACTION_MOVE ->

                if (isContain) {

                    moveX = moveX + event.x - downX
                    if (originalBitmap != null) {
                        var margin = (width - originalBitmap!!.width) / 2
                        if (moveX < -(width / 2 - margin)) {
                            moveX = -((width / 2 - margin).toFloat())
                        } else if (moveX > (width / 2 - margin)) {
                            moveX = ((width / 2 - margin).toFloat())
                        }
                    }
                    invalidate()
                    downX = event.x
                    downY = event.y
                } else if (mode == MODE_DRAG && total_scale > 1) {
                    val dx = event.x - dragPoint.x
                    val dy = event.y - dragPoint.y
                    dragPoint[event.x] = event.y
                    imgTransport(dx, dy)
                } else if (mode == MODE_ZOOM) { //缩放
                    val endDis = distance(event)
                    midPoint = mid(event)
                    if (endDis > 10f) {
                        current_scale = endDis / startDis //缩放倍数
                        total_scale *= current_scale
                        matrixNow.postScale(
                            current_scale, current_scale, midPoint.x, midPoint.y
                        )
                        invalidate()
                    }
                    startDis = endDis
                }
            MotionEvent.ACTION_UP -> {
                parent.requestDisallowInterceptTouchEvent(false) //触摸事件请求取消拦截
                mode = 0
                if (mode == MODE_DRAG) checkClick(
                    event.x, event.y, actionDownPoint.x, actionDownPoint.y
                )
            }
            MotionEvent.ACTION_POINTER_UP -> {
                checkZoomValid()
                mode = 0
            }
            MotionEvent.ACTION_CANCEL -> {
                isContain = false
            }
        }
        return true
    }

    /**
     * 平移图片
     * @param x
     * @param y
     */
    fun imgTransport(x: Float, y: Float) {
        var x = x
        var y = y
        mCurrentScalePoint[mCurrentScalePoint.x + x] = mCurrentScalePoint.y + y
        if (mCurrentScalePoint.x >= (total_scale - 1) * width / 2) {
            mCurrentScalePoint.x = (total_scale - 1) * width / 2
            x = 0f
        } else {
            if (mCurrentScalePoint.x <= -((total_scale - 1) * width) / 2) {
                mCurrentScalePoint.x = -((total_scale - 1) * width) / 2
                x = 0f
            }
        }
        if (mCurrentScalePoint.y >= (total_scale - 1) * height / 2) {
            mCurrentScalePoint.y = (total_scale - 1) * height / 2
            y = 0f
        } else {
            if (mCurrentScalePoint.y <= -((total_scale - 1) * height) / 2) {
                mCurrentScalePoint.y = -((total_scale - 1) * height) / 2
                y = 0f
            }
        }
        LogUtils.e("mCurrentScalePoint.x:" + mCurrentScalePoint.x + "   x:" + x)
        matrixNow!!.postTranslate(x, y)
        invalidate()
    }

    private fun checkZoomValid(): Boolean {
        if (mode == MODE_ZOOM) {
            if (total_scale > MAX_SCALE) {
                resetToMaxStatus()
                matrixNow.set(mInitializationMatrix)
                matrixNow.postScale(
                    MAX_SCALE, MAX_SCALE, mInitializationScalePoint.x, mInitializationScalePoint.y
                )
                matrixNow.postTranslate(mCurrentScalePoint.x, mCurrentScalePoint.y)
                invalidate()
                return false
            } else if (total_scale < MIN_SCALE) {
                resetToMinStatus()
                matrixNow.set(mInitializationMatrix)
                invalidate()
                return false
            }
            invalidate()
        }
        return true
    }

    private fun distance(event: MotionEvent): Float {
        val dx = event.getX(1) - event.getX(0)
        val dy = event.getY(1) - event.getY(0)
        return Math.sqrt((dx * dx + dy * dy).toDouble())
            .toFloat()
    }

    private fun mid(event: MotionEvent): PointF {
        val midX = (event.getX(1) + event.getX(0)) / 2
        val midY = (event.getY(1) + event.getY(0)) / 2
        return PointF(midX, midY)
    }

    fun checkClick(last_x: Float, last_y: Float, now_x: Float, now_y: Float): Boolean {
        val x_d = Math.abs(last_x - now_x)
        val y_d = Math.abs(last_y - now_y)
        if (x_d < 10 && y_d < 10) { //点击事件
            //处理单击事件
        }
        if (total_scale == 1f) {
            matrixNow!!.set(mInitializationMatrix)
            invalidate()
        }
        return false
    }

    fun getImageMatrix(): Matrix? {
        return matrixNow
    }
}