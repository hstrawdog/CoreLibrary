package com.easy.example.ui.customize.view

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Shader
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatSeekBar
import com.easy.example.R

/**
 * @Author : huangqiqiang
 * @Package : com.easy.example.ui.customize.view
 * @Date :2024/12/6 15:49
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class PrismaticSeekBar @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = -1
) : AppCompatSeekBar(context, attrs, defStyleAttr) {

    companion object {
        const val TYPE_NORMAL = 0
        const val TYPE_COLORFUL = 1
        const val MAX_DISTANCE_FOR_CLICK = 5
    }

    private val mPaint: Paint = Paint().apply { isAntiAlias = true }
    private var mBackgroundPath: Path? = null
    private var mProgressPath: Path? = null
    private var mThumbPath: Path? = null
    private var mProgressGradient: LinearGradient? = null
    private var mThumbGradientCache: LinearGradient? = null
    private var mAreaOffsetX = 0f
    private var mAreaOffsetHalfX = 0f

    private val THUMB_WIDTH = context.resources.getDimensionPixelSize(R.dimen.x50)
    private val COLOR_BACKGROUND_NORMAL = context.resources.getColor(R.color.blue)
    private val COLOR_PROGRESS_NORMAL = context.resources.getColor(R.color.red)

    private var mSeekBarType = 0
    private var mCurrentProgressPos = 0f
    private var mTouchPos = 0f
    private var mIsClick = false
    private var mIsTouch = false
    private var mDownX = 0f
    private var mDownY = 0f
    private var mTempX = 0f
    private var mTempY = 0f

    init {
        init(context, attrs)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        initPath()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        mOnTouchListener.onTouch(this, event)
        return super.onTouchEvent(event)
    }

    private val mOnTouchListener = OnTouchListener { view, event ->
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                mIsTouch = true
                mDownX = event.x
                mDownY = event.y
                trackTouchEvent(event, true)
            }
            MotionEvent.ACTION_MOVE -> {
                mIsTouch = true
                mTempX = event.x
                mTempY = event.y
                trackTouchEvent(event, true)
            }
            MotionEvent.ACTION_UP -> {
                mTempX = event.x
                mTempY = event.y
                if (Math.abs(mTempX - mDownX) < MAX_DISTANCE_FOR_CLICK &&
                    Math.abs(mTempY - mDownY) < MAX_DISTANCE_FOR_CLICK) {
                    onClick(event)
                }
                trackTouchEvent(event, false)
                mIsTouch = false
            }
            MotionEvent.ACTION_CANCEL -> {
                mIsTouch = false
                trackTouchEvent(event, false)
            }
        }
        true
    }

    private fun onClick(event: MotionEvent) {
        mIsClick = true
        mTouchPos = event.x
        invalidate()
    }

    private fun trackTouchEvent(event: MotionEvent, mIsTouch: Boolean) {
        if (!mIsTouch) {
            mCurrentProgressPos = mTouchPos
        }
        mTouchPos = event.x
        invalidate()
    }

    private fun initPath() {
        val width = measuredWidth
        val height = measuredHeight
        val halfHeight = height / 2
        mAreaOffsetX = (height / Math.sqrt(3.0)).toFloat()
        mAreaOffsetHalfX = (height / (2 * Math.sqrt(3.0))).toFloat()

        mBackgroundPath = Path().apply {
            moveTo(0f, height.toFloat())
            lineTo(mAreaOffsetHalfX, halfHeight.toFloat())
            lineTo((width - mAreaOffsetHalfX), halfHeight.toFloat())
            lineTo((width - mAreaOffsetX), height.toFloat())
            close()
            fillType = Path.FillType.WINDING
        }

        mProgressPath = Path()
        mThumbPath = Path()
        mCurrentProgressPos = measuredWidth * progress / max.toFloat()
    }

    private fun init(context: Context, attrs: AttributeSet?) {
//        val a: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.PrismaticSeekBar)
//        a?.run {
//            try {
//                mSeekBarType = getInt(R.styleable.PrismaticSeekBar_seekBarType, 0)
//            } catch (e: UnsupportedOperationException) {
//                e.printStackTrace()
//            } catch (e: Resources.NotFoundException) {
//                e.printStackTrace()
//            } finally {
//                recycle()
//            }
//        }
    }

    override fun setProgress(progress: Int) {
        super.setProgress(progress)
        mCurrentProgressPos = measuredWidth * progress / max.toFloat()
        if (mIsClick) {
            mIsClick = false
            mCurrentProgressPos = mTouchPos
        }
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // Draw background
        mPaint.reset()
        mPaint.isAntiAlias = true
        mPaint.color = COLOR_BACKGROUND_NORMAL
        canvas.drawPath(mBackgroundPath!!, mPaint)
        // Draw progress
        val progressRight: Float = if (mIsTouch) {
            mCurrentProgressPos = mTouchPos
            mTouchPos
        } else {
            mCurrentProgressPos
        }
        drawProgress(canvas, progressRight, measuredHeight, mPaint)

        // Draw thumb
        val position = progress * 50 / max
        val progressRatio = progress / max.toFloat()
        val gradientRight = (measuredWidth * progressRatio).toInt()
        drawThumb(canvas, progressRight, measuredHeight, mPaint, position, gradientRight)
    }

    private fun drawProgress(canvas: Canvas, currentPosition: Float, height: Int, paint: Paint) {
        if (currentPosition > mAreaOffsetX) {
            paint.reset()
            paint.isAntiAlias = true
            paint.color = if (mSeekBarType == TYPE_NORMAL) COLOR_PROGRESS_NORMAL else {
                if (mProgressGradient == null) {
                    mProgressGradient = LinearGradient(0f, 0f, width.toFloat(), 0f,
                        intArrayOf(0xFF67A7F6.toInt(), 0xFFFEAF6F.toInt()), null, Shader.TileMode.CLAMP)
                }
                paint.shader = mProgressGradient
                paint.color
            }

            mProgressPath!!.reset()
            val rightPosX = if (currentPosition > measuredWidth) measuredWidth.toFloat() else currentPosition
            val leftPosX = if ((rightPosX - mAreaOffsetX) > 0) (rightPosX - mAreaOffsetX) else 0f
            mProgressPath!!.moveTo(0f, height.toFloat())
            mProgressPath!!.lineTo(mAreaOffsetX, 0f)
            mProgressPath!!.lineTo(rightPosX, 0f)
            mProgressPath!!.lineTo(leftPosX, height.toFloat())
            mProgressPath!!.close()
            canvas.drawPath(mProgressPath!!, paint)
        }
    }

    private fun drawThumb(canvas: Canvas, currentPosition: Float, height: Int, paint: Paint, position: Int, gradientRight: Int) {
        if (currentPosition > mAreaOffsetX) {
            paint.reset()
            paint.isAntiAlias = true

            if (mThumbGradientCache == null) {
                mThumbGradientCache = LinearGradient(0f, 0f, THUMB_WIDTH.toFloat(), 0f,
                    intArrayOf(0x00FFFFFF, 0xFFFFFFFF.toInt()), null, Shader.TileMode.CLAMP)
            }
            paint.shader = mThumbGradientCache

            mThumbPath!!.reset()
            val rightPosX = if (currentPosition > measuredWidth) measuredWidth.toFloat() else currentPosition

            if (rightPosX > THUMB_WIDTH) {
                canvas.save()
                canvas.translate(rightPosX - THUMB_WIDTH, 0f)
                mThumbPath!!.moveTo(THUMB_WIDTH.toFloat(), 0f)
                mThumbPath!!.lineTo(THUMB_WIDTH - mAreaOffsetX, height.toFloat())
                mThumbPath!!.lineTo(0f, height.toFloat())
                mThumbPath!!.lineTo(mAreaOffsetX, 0f)
                mThumbPath!!.close()
                canvas.drawPath(mThumbPath!!, paint)
                canvas.restore()
            } else {
                mThumbPath!!.moveTo(rightPosX, 0f)
                mThumbPath!!.lineTo(rightPosX - mAreaOffsetX, height.toFloat())
                mThumbPath!!.lineTo(0f, height.toFloat())
                mThumbPath!!.lineTo(mAreaOffsetX, 0f)
                mThumbPath!!.close()
                canvas.drawPath(mThumbPath!!, paint)
            }
        }
    }
}
