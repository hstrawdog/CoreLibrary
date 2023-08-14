package com.easy.example.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.graphics.values
import com.easy.core.utils.log.LogUtils


/**
 * @Author : huangqiqiang
 * @Package : com.easy.example.widget
 * @Date : 15:07
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class BlurBackgroundView : View {
    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}


    /**
     *  0
     *  1   圆圈
     *  2  直线
     */
    var model = 1
        set(value) {
            field = value
            invalidate()
        }

    /**
     *  中心点的高度
     */
    var centerY = 0f
    var centerX = 0f
    val defOuterSpacing = 20f

    /**
     *  外间距 /  过度
     *  单位 5
     *
     */
    var outerSpacing = defOuterSpacing

    /**
     *  内间距
     */
    var defSpacing = 50f
    var innerSpacing = defSpacing

    var pain = Paint().apply {
        setColor(Color.WHITE)
        setStrokeWidth(3f)
        setAntiAlias(true)
    }

    //最大的缩放比例
    val SCALE_MAX = 12.0f
    private val SCALE_MIN = 0.5f

    /**
     * 针对控件的坐标系，即控件左上角为原点
     */
    private var moveX = 0.0f
    private var moveY = 0.0f
    private var downX = 0.0f
    private var downY = 0.0f

    // 针对屏幕的坐标系，即屏幕左上角为原点
    private var moveRawX = 0.0f
    private var moveRawY = 0.0f

    /**
     * 当前触摸的点数
     */
    private var pointNum = 0
    private var oldDist = 0.0f
    private var moveDist = 0.0f
    private var _scale = 1f

    /**
     *  变换后的回调
     */
    var blurBackgroundListener: BlurBackgroundListener? = null

    /**
     *  在移动
     */
    var isMove = false

    /**
     *  在缩放
     */
    var isScale = false

    /**
     *  默认背景颜色
     * @return Int
     */
    private fun defBgColor() = Color.parseColor("#33ffffff")

    var bgMatrix: Matrix? = null

    /**
     *  默认是不显示的
     */
    var isShowEdit = false
        set(value) {
            field = value
            invalidate()
        }


    init {

        addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
            override fun onLayoutChange(v: View?, left: Int, top: Int, right: Int, bottom: Int, oldLeft: Int, oldTop: Int, oldRight: Int, oldBottom: Int) {

                removeOnLayoutChangeListener(this)
                LogUtils.e("addOnLayoutChangeListener    centerY        ${height}        centerX ${width}")
                centerY = height / 2f
                centerX = width / 2f
                invalidate()
            }

        });
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (isShowEdit) {
            when (model) {
                1 -> {
                    drawCircle(canvas)
                }
                2 -> {
                    drawLine(canvas)
                }

            }
        }

    }

    /**
     *  绘制横线
     * @param canvas Canvas
     */
    private fun drawLine(canvas: Canvas) {
        pain.apply {
            style = Paint.Style.FILL
        }
        canvas.drawLine(0f, centerY + innerSpacing, width.toFloat(), centerY + innerSpacing, pain)
        canvas.drawLine(0f, centerY + innerSpacing + outerSpacing, width.toFloat(), centerY + innerSpacing + outerSpacing, pain)

        canvas.drawLine(0f, centerY - innerSpacing, width.toFloat(), centerY - innerSpacing, pain)
        canvas.drawLine(0f, centerY - innerSpacing - outerSpacing, width.toFloat(), centerY - innerSpacing - outerSpacing, pain)


        var p = Paint().apply {
            style = Paint.Style.FILL_AND_STROKE
            color = defBgColor()
        }
        canvas.drawRect(0f, 0f, width.toFloat(), centerY - innerSpacing - outerSpacing, p)
        canvas.drawRect(0f, centerY + innerSpacing + outerSpacing, width.toFloat(), height.toFloat(), p)


        canvas.drawRect(0f, centerY - innerSpacing, width.toFloat(), centerY - innerSpacing - outerSpacing, Paint().apply {
            setShader(
                LinearGradient(0f,
                centerY - innerSpacing,
                0f,
                centerY - innerSpacing - outerSpacing,
                intArrayOf(Color.TRANSPARENT, defBgColor()),
                floatArrayOf(0f, 0.5f),
                Shader.TileMode.CLAMP)
            )
            style = Paint.Style.FILL

        })

        canvas.drawRect(0f, centerY + innerSpacing, width.toFloat(), centerY + innerSpacing + outerSpacing, Paint().apply {
            setShader(
                LinearGradient(0f,
                centerY + innerSpacing + outerSpacing,
                0f,
                centerY + innerSpacing,
                intArrayOf(defBgColor(), Color.TRANSPARENT),
                floatArrayOf(0.5f, 1f),
                Shader.TileMode.CLAMP)
            )
            style = Paint.Style.FILL

        })


    }


    /**
     *
     * @param canvas Canvas
     */
    private fun drawCircle(canvas: Canvas) {
        canvas.drawCircle(centerX, centerY, innerSpacing, pain.apply {
            style = Paint.Style.STROKE
        })
        //        // 外圆
        canvas.drawCircle(centerX, centerY, innerSpacing + outerSpacing, pain.apply {
            style = Paint.Style.STROKE
        })

        var paint = Paint().apply {
            style = Paint.Style.FILL
            strokeWidth = 1f;
        }
        // 需要计算比例
        var radialGradient = RadialGradient(centerX,
            centerY,
            innerSpacing + outerSpacing,
            intArrayOf(Color.TRANSPARENT, defBgColor()),
            floatArrayOf(innerSpacing / (innerSpacing + outerSpacing), 1f),
            Shader.TileMode.CLAMP)
        paint.setShader(radialGradient)
        canvas.drawCircle(centerX, centerY, innerSpacing + outerSpacing, paint)

        canvas.saveLayer(0f, 0f, width.toFloat(), height.toFloat(), null)

        canvas.drawColor(defBgColor())
        var p = Paint().apply {
            style = Paint.Style.FILL_AND_STROKE
            color = defBgColor()
        }
        canvas.drawCircle(centerX, centerY, innerSpacing + outerSpacing, p.apply {
            setXfermode(PorterDuffXfermode(PorterDuff.Mode.CLEAR))
        })

        p.setXfermode(null)

        canvas.restore()


//        canvas.restoreToCount(sc)
//        canvas.save()

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {


        when (event.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {
                isMove = true
                isScale = false

                if (height == 0) {
                    centerY = event.y
                }
                pointNum = 1;
                downX = event.getX();
                downY = event.getY();
                isShowEdit = true
            }
            MotionEvent.ACTION_UP -> {
                pointNum = 0;
                downX = 0f;
                downY = 0f;
                isMove = false
                isScale = false
                if (model > 0) {
                    // 通知  变化
                    blurBackgroundListener?.onBlurBackgroundValueListener(true)
                }

            }
            MotionEvent.ACTION_MOVE -> {
                if (pointNum == 1 && isMove) {
                    //只有一个手指的时候才有移动的操作
                    moveX = event.x
                    moveY = event.y
                    moveRawX = event.rawX
                    moveRawY = event.rawY
                    setSelfPivot((downX - event.x), (downY - event.y))
                    downX = event.x
                    downY = event.y
                    //setPivot(getPivotX() + lessX, getPivotY() + lessY);
                } else if (pointNum == 2 && isScale) {
                    //只有2个手指的时候才有放大缩小的操作
                    moveDist = spacing(event)
                    val space: Float = moveDist - oldDist

                    LogUtils.e("       scale  ${space / 50f}               ${moveDist - oldDist}      ")
                    val scale = (space / 50f).toFloat() + _scale

                    if (scale > SCALE_MIN && scale < SCALE_MAX) {
//                        setScale(scale)
                        _scale = scale
                    } else if (scale < SCALE_MIN) {
//                        setScale(SCALE_MIN)
                        _scale = SCALE_MIN
                    }
                    innerSpacing = defSpacing * scale
                    oldDist = spacing(event);

                }

                invalidate()
            }
            MotionEvent.ACTION_POINTER_DOWN -> {
                //两点按下时的距离
                oldDist = spacing(event);
                pointNum += 1;
                isMove = false
                isScale = true
            }
            MotionEvent.ACTION_POINTER_UP -> {
                pointNum -= 1;
                isScale = false
            }

        }



        return true
    }

    /**
     * 触摸使用的移动事件
     *
     * @param lessX x坐标
     * @param lessY y坐标
     */
    private fun setSelfPivot(lessX: Float, lessY: Float) {
        var setPivotX = 0f
        var setPivotY = 0f
        setPivotX = centerX - lessX
        setPivotY = centerY - lessY

        if (setPivotX < 0 && setPivotY < 0) {
            setPivotX = 0f
            setPivotY = 0f
        } else if (setPivotX > 0 && setPivotY < 0) {
            setPivotY = 0f
            if (setPivotX > width) {
                setPivotX = width.toFloat()
            }
        } else if (setPivotX < 0 && setPivotY > 0) {
            setPivotX = 0f
            if (setPivotY > height) {
                setPivotY = height.toFloat()
            }
        } else {
            if (setPivotX > width) {
                setPivotX = width.toFloat()
            }
            if (setPivotY > height) {
                setPivotY = height.toFloat()
            }
        }
//        setPivot(setPivotX, setPivotY)
        LogUtils.e(" centerX $centerX            centerY $centerY   setPivotX $setPivotX      setPivotY $setPivotY      lessX $lessX   lessY $lessY             ")

        centerX = setPivotX
        centerY = setPivotY
        invalidate()
    }

    /**
     * 计算两个点的距离
     *
     * @param event 事件
     * @return 返回数值
     */
    private fun spacing(event: MotionEvent): Float {
        return if (event.pointerCount == 2) {
            val x = event.getX(0).toFloat() - event.getX(1).toFloat()
            val y = event.getY(0) - event.getY(1).toFloat()
            Math.sqrt(((x * x + y * y).toDouble())).toFloat()
        } else {
            0.toFloat()
        }
    }

    /**
     * 设置放大缩小
     *
     * @param scale 缩放值
     */
    fun setScale(scale: Float) {
        scaleX = scale
        scaleY = scale
        invalidate()
    }

    /**
     *  外间距 倍数
     *   n+1
     */
    fun setOuterSpacingMultiple(scale: Int) {
        outerSpacing = defOuterSpacing + (1 + scale)
    }

    /**
     *  重置点
     */
    fun resetCenter() {
        centerX = (width / 2).toFloat()
        centerY = (height / 2).toFloat()
        moveX = 0.0f
        moveY = 0.0f
        downX = 0.0f
        downY = 0.0f
        // 针对屏幕的坐标系，即屏幕左上角为原点
        moveRawX = 0.0f
        moveRawY = 0.0f
        /**
         * 当前触摸的点数
         */
        pointNum = 0
        oldDist = 0.0f
        moveDist = 0.0f
        _scale = 1f
    }

    /**
     *  获取黑白图
     */
    fun getBlackAndWhite(imageWidth: Int, imageHeight: Int): Bitmap? {
        val bitmap = Bitmap.createBitmap(imageWidth, imageHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        canvas.drawColor(Color.BLACK)
        val f = getRealityPoint(centerX, centerY)
//            bgMatrix!!.mapPoints(f)
        if (bgMatrix != null) {
            val scale = Math.min(bgMatrix!!.values()[0], bgMatrix!!.values()[4])
            when (model) {
                1 -> {
                    getBlackAndWhite4Circle(imageWidth, imageHeight, canvas, scale, f)
                }
                2 -> {
                    getBlackAndWhite4Rect(imageWidth, imageHeight, canvas, scale, f)
                }
            }
        }
        return bitmap
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
     *  圆形的 黑白图
     */
    private fun getBlackAndWhite4Circle(imageWidth: Int, imageHeight: Int, canvas: Canvas, scale: Float, f: FloatArray) {


        if (bgMatrix != null) {
            val paint = Paint().apply {
                style = Paint.Style.FILL
                strokeWidth = 1f;
            }
            // 需要计算比例
            val radialGradient = RadialGradient(f[0],
                f[1],
                innerSpacing / scale + outerSpacing / scale,
                intArrayOf(Color.TRANSPARENT, Color.WHITE),
                floatArrayOf((innerSpacing / scale) / ((innerSpacing + outerSpacing) / scale), 1f),
                Shader.TileMode.CLAMP)
            paint.setShader(radialGradient)
            canvas.drawCircle(f[0], f[1], innerSpacing / scale + outerSpacing / scale, paint)

            canvas.saveLayer(0f, 0f, imageWidth.toFloat(), imageHeight.toFloat(), null)

            canvas.drawColor(Color.WHITE)
            val p = Paint().apply {
                style = Paint.Style.FILL_AND_STROKE
                color = defBgColor()
            }
            canvas.drawCircle(f[0], f[1], innerSpacing / scale + outerSpacing / scale, p.apply {
                setXfermode(PorterDuffXfermode(PorterDuff.Mode.CLEAR))
            })

            p.setXfermode(null)

            canvas.restore()

        }
    }

    private fun getBlackAndWhite4Rect(imageWidth: Int, imageHeight: Int, canvas: Canvas, scale: Float, f: FloatArray) {

        if (bgMatrix != null) {

            val p = Paint().apply {
                style = Paint.Style.FILL_AND_STROKE
                color = Color.WHITE
            }
            // 黑色部分
            canvas.drawRect(0f, 0f, imageWidth.toFloat(), f[0] - innerSpacing / scale - outerSpacing / scale, p)
            canvas.drawRect(0f, f[0] + innerSpacing / scale + outerSpacing / scale, imageWidth.toFloat(), height.toFloat(), p)

            // 渐变部分
            canvas.drawRect(0f, f[0] - innerSpacing / scale, imageWidth.toFloat(), f[0] - innerSpacing / scale - outerSpacing / scale, Paint().apply {
                setShader(
                    LinearGradient(0f,
                    f[0] - innerSpacing / scale,
                    0f,
                    f[0] - innerSpacing / scale - outerSpacing / scale,
                    intArrayOf(Color.TRANSPARENT, Color.WHITE),
                    floatArrayOf(0f, 0.5f),
                    Shader.TileMode.CLAMP)
                )
                style = Paint.Style.FILL

            })
            canvas.drawRect(0f, f[0] + innerSpacing / scale, imageWidth.toFloat(), f[0] + innerSpacing / scale + outerSpacing / scale, Paint().apply {
                setShader(
                    LinearGradient(0f,
                    f[0] + innerSpacing / scale + outerSpacing / scale,
                    0f,
                    f[0] + innerSpacing / scale,
                    intArrayOf(Color.WHITE, Color.TRANSPARENT),
                    floatArrayOf(0.5f, 1f),
                    Shader.TileMode.CLAMP)
                )
                style = Paint.Style.FILL

            })
        }
    }

}