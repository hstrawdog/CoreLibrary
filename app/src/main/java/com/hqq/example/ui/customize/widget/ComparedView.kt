package com.hqq.example.ui.customize.widget

import android.R.attr
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.graphics.RectF
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.R.attr.button

import android.animation.ValueAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.text.TextPaint
import android.view.MotionEvent

import android.view.animation.LinearInterpolator
import com.hqq.example.R


/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui.customize.widget
 * @Date : 14:48
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class ComparedView : View {
    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {

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
     *  美化后图片的实际大小
     */
    var beautifyBitmapHeight=0f
    var beautifyBitmapWidth=0f

    /**
     * 美化前的图片
     */
    var originalBitmap: Bitmap? = null


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
        mPaint.setAntiAlias(true)
        mPaint.setStyle(Paint.Style.FILL)
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (status != -1) {

            // 位置线
            val menuY = height / 2 - menuBitmap.height
            val menuX = width / 2 - menuBitmap.width / 2 + moveX
            // 中线
            val centerX = (menuX + menuBitmap.width / 2).toInt()
            if (status == 1) {
                drawBitmap(canvas, centerX)
            }
            // 显示扫描
//            if (status == 0) {
//                drawAnimation(canvas)
//            }

            if (status == 1) {
                var top = 0f
                var bottom = 0f
                if (originalBitmap != null) {
                    top = ((height - originalBitmap!!.height).toFloat() / 2f).toFloat()
                    bottom = (height - originalBitmap!!.height) / 2 + originalBitmap!!.height.toFloat()
                }
                drawMenu(canvas, menuX, menuY, top, bottom)
                drawCanvasText(centerX, canvas, top)
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
            val x1 = Math.max(0, (width - originalBitmap!!.width) / 2)
            val y1 = (height - originalBitmap!!.height) / 2

            canvas.drawBitmap(originalBitmap!!, x1.toFloat(), y1.toFloat(), Paint())

            val l = Math.max(0, (width - beautifyBitmap!!.width) / 2)
            val r = beautifyBitmap!!.width + l
            val t = (height - beautifyBitmap!!.height) / 2
            val b = beautifyBitmap!!.height + t


            val newR = Math.min(centerX - l, beautifyBitmap!!.width)

            var r1 = Rect(0, 0, newR, beautifyBitmap!!.height)

            // 显示的大小
            var r2 = Rect(l, t, centerX, b)

            canvas.drawBitmap(beautifyBitmap!!, r1, r2, Paint())
        }

    }

    /**
     * 动画
     * @param canvas Canvas
     */
    private fun drawAnimation(canvas: Canvas) {
        // 扫描动画
        val shader2 =
            LinearGradient(width / 2f, pointX.toFloat(), width / 2f, 300f + pointX.toFloat(), Color.parseColor("#00FC4848"), Color.parseColor("#FC4848"), Shader.TileMode.MIRROR)
        mPaint.setShader(shader2)
        val rectFRight = RectF(0f, pointX.toFloat(), width.toFloat(), 300f + pointX.toFloat())
        canvas.drawRect(rectFRight, mPaint)
    }

    /**
     *  滑动按钮
     * @param canvas Canvas
     * @param menuX Float
     * @param menuY Int
     */
    private fun drawMenu(canvas: Canvas, menuX: Float, menuY: Int, top: Float, bottom: Float) {
        canvas.drawLine((menuX + menuBitmap.width / 2).toFloat(), top, (menuX + menuBitmap.width / 2).toFloat(), bottom, Paint().apply {
            setColor(Color.parseColor("#F73838"))
            mPaint.setStyle(Paint.Style.FILL)
            strokeWidth = 3f

        })
        canvas.drawBitmap(menuBitmap, menuX.toFloat(), menuY.toFloat(), Paint())
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

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (status != -1) {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    downX = event.x
                    downY = event.y
                    isContain = containMenu(downX, downY)
                    return true
                }
                MotionEvent.ACTION_MOVE -> {
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

                    }
                    return true
                }

                MotionEvent.ACTION_CANCEL -> {
                    isContain = false
                }
            }
        }
        return super.onTouchEvent(event)
    }

    private fun containMenu(downX: Float, downY: Float): Boolean {
        var menuY = height / 2 - menuBitmap.height / 2
        var menuX = width / 2 - menuBitmap.width / 2 + moveX
        var rect = RectF(menuX.toFloat(), menuY.toFloat(), menuX + menuBitmap.height, (menuY + menuBitmap.height).toFloat())

        return rect.contains(downX, downY)

    }

    /**
     *  开始执行动画
     */
    fun startRectAnimation() {

//        val valueAnimator = ValueAnimator.ofInt(0, height)
////              整个事件段是5秒
//        //              整个事件段是5秒
//        valueAnimator.duration = 3000
////              数字均匀变化，也可设置其他的变化方式，先快后慢，先慢后快等......
//        valueAnimator.interpolator = LinearInterpolator()
//        //              监听每次改变时的值
//        valueAnimator.addUpdateListener { animation -> //                      拿到每一次变化的值
//            val value = animation.animatedValue as Int
//            //                      把只设置到按钮上
//            pointX = value
//            invalidate()
//        }
//        valueAnimator.repeatCount = Animation.INFINITE
//        valueAnimator.start()

    }


}