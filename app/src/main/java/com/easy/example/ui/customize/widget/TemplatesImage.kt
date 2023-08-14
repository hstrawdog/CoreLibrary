package com.easy.example.ui.customize.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import com.easy.core.utils.log.LogUtils.dInfo
import com.easy.core.utils.log.LogUtils.e
import com.easy.example.R
import com.easy.example.ui.customize.widget.imageedit.DrawableSticker
import java.util.*

/**
 * @Author : huangqiqiang
 * @Package : com.easy.example.ui.customize.widget
 * @FileName :   TemplatesImage
 * @Date : 2020/1/10 0010  下午 1:35
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
class TemplatesImage : View {
    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    var mClipPath = Path()
    var distanceX = 0f
    var distanceY = 0f
    var mKeyX = 0f
    var mKeyY = 0f
    var mFrame = width / 8.toFloat()
    var mCurrentMatrix: Matrix? = null
    private var borderPaint: Paint? = null
    private fun init() {
        mCurrentMatrix = Matrix()
        mClipPath.moveTo(0f, 0f)
        mClipPath.lineTo(300f, 0f)
        mClipPath.lineTo(300f, 300f)
        mClipPath.lineTo(0f, 300f)
        mClipPath.lineTo(0f, 0f)
        mDrawableSticker =
            com.easy.example.ui.customize.widget.imageedit.DrawableSticker(ContextCompat.getDrawable(context, R.mipmap.ic_content))
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        mFrame = width / 8.toFloat()
        dInfo("onLayout" + width)
    }

    var mDrawableSticker: com.easy.example.ui.customize.widget.imageedit.DrawableSticker? = null
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        initClipPath()
        drawBg(canvas)
        drawClipBg(canvas)
        mDrawableSticker!!.draw(canvas)
        val dst = FloatArray(8)
        mDrawableSticker!!.matrix
                .mapPoints(dst, bitmapSize)
        dInfo(Arrays.toString(dst))
        val x1 = dst[0]
        val y1 = dst[1]
        val x2 = dst[2]
        val y2 = dst[3]
        val x3 = dst[4]
        val y3 = dst[5]
        val x4 = dst[6]
        val y4 = dst[7]
        borderPaint = Paint()
        borderPaint!!.isAntiAlias = true
        borderPaint!!.color = Color.RED
        //        borderPaint.setAlpha(128);
        borderPaint!!.strokeWidth = 6f
        canvas.drawLine(x1, y1, x2, y2, borderPaint!!)
        canvas.drawLine(x1, y1, x3, y3, borderPaint!!)
        canvas.drawLine(x2, y2, x4, y4, borderPaint!!)
        canvas.drawLine(x4, y4, x3, y3, borderPaint!!)
    }

    private val bitmapSize: FloatArray
        private get() = floatArrayOf(
                0f, 0f, mDrawableSticker
                ?.getWidth()!!.toFloat(), 0f, 0f, mDrawableSticker!!.height.toFloat(), mDrawableSticker!!.width.toFloat(), mDrawableSticker!!.height
                .toFloat())

    var isTounch = false
    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                dInfo("getRight"+ right)
                dInfo("ACTION_DOWN -----------" + event.x)
                mKeyX = event.x
                mKeyY = event.y
                if (mDrawableSticker!!.contains(mKeyX, mKeyY)) {
                    isTounch = true
                    mCurrentMatrix!!.set(mDrawableSticker!!.matrix
                    )
                }
            }
            MotionEvent.ACTION_MOVE -> {
                dInfo("ACTION_MOVE -----------" + event.x)
                distanceX = event.x - mKeyX
                distanceY = event.y - mKeyY
                //                mKeyX = event.getX();
//                mKeyY = event.getY();
                val xx = (width - mFrame * 2) / width
                val yy = (height - mFrame * 2) / height
                dInfo("ACTION_MOVE --------distanceX---$distanceX")
                dInfo("ACTION_MOVE --------distanceY---$distanceY")
                dInfo("-------------------     $xx            $yy")
                if (isTounch) {
                    mDrawableSticker!!.matrix
                            .set(mCurrentMatrix)
                    mDrawableSticker!!.matrix
                            .postTranslate(distanceX, distanceY)
                    invalidate()
                }
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                isTounch = false
                dInfo("---------------------------------------------------------------------------------------------------------------------")
                e("distanceX"+ distanceX)
                e("getRawX -----------" + event.rawX)
                e("getX"+ x)
                e("getLeft"+ left)
                e("getRight"+ right)
                e("getTranslationX"+ translationX)
                e("getScrollX"+ scrollX)
            }
            else -> {
            }
        }
        return super.dispatchTouchEvent(event)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> return true
            else -> {
            }
        }
        return super.onTouchEvent(event)
    }

    private fun initClipPath() {
        mClipPath.reset()
        mClipPath.moveTo(mFrame, mFrame)
        mClipPath.lineTo(width - mFrame, mFrame)
        mClipPath.lineTo(width - mFrame, height - mFrame)
        mClipPath.lineTo(mFrame, height - mFrame)
        mClipPath.lineTo(mFrame, mFrame)
    }

    private fun drawClipBg(canvas: Canvas) {
        val paint = Paint()
        paint.color = Color.RED
        paint.strokeWidth = 5f
        paint.style = Paint.Style.STROKE
        canvas.drawPath(mClipPath, paint)
        canvas.clipPath(mClipPath)
        refreshDrawableState()
    }

    private fun drawBg(canvas: Canvas) {
        canvas.save()
        val bitmap = BitmapFactory.decodeResource(resources, R.mipmap.bg_sty)
        val matrix = Matrix()
        matrix.setScale(java.lang.Float.valueOf(width.toFloat()) / bitmap.width, java.lang.Float.valueOf(height.toFloat()) / bitmap.height)
        canvas.drawBitmap(bitmap, matrix, null)
        canvas.restore()
    }
}