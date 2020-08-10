package com.hqq.example.ui.customize.widget

import android.animation.FloatEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.RequiresApi
import com.hqq.core.utils.ResourcesUtils.getDimen
import com.hqq.example.R

/**
 * @version V1.0 <描述当前版本功能>
 * @author: huangqiqiang
 * 在此写用途
 * @FileName: com.hqq.core.app.ui.activity.customize.weight.ProgressBarView.java
 * @emain: 593979591@qq.com
 * @date: 2019-06-23 18:41
</描述当前版本功能> */
class BargainProgressBarView : View {
    var mProgress = 1f

    @ColorInt
    var mUnselectedColor = Color.RED

    @ColorInt
    var mSelectedColor = Color.GREEN

    /**
     * 圆角大小
     */
    var mFilletSize = 10
    private var mHeight = 0f
    private var mBitmap: Bitmap? = null

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        mHeight = getDimen(R.dimen.x16)
        mFilletSize = getDimen(R.dimen.x8).toInt()
        mBitmap = BitmapFactory.decodeResource(resources, R.mipmap.ic_ax)
        setProgress(100f)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, mBitmap!!.height)
        setMeasuredDimension(widthMeasureSpec, mBitmap!!.height)
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val paint = Paint()
        paint.isAntiAlias = true
        canvas.save()
        canvas.translate(0f, (height - mHeight) / 2)
        paint.color = mUnselectedColor
        canvas.drawRoundRect(0 + mBitmap!!.width / 2.toFloat(), 0f, width - mBitmap!!.width / 2.toFloat(), mHeight, mFilletSize.toFloat(), mFilletSize.toFloat(), paint)
        paint.color = mSelectedColor
        canvas.drawRoundRect(0 + mBitmap!!.width / 2.toFloat(), 0f, mBitmap!!.width / 2 + (width - mBitmap!!.width / 2) / 100.0f * (if (mProgress >= 100) 100 else mProgress), mHeight, mFilletSize.toFloat(), mFilletSize.toFloat(), paint)
        canvas.restore()
        var left: Float = width / 100.0f * (if (mProgress >= 100) 100 else mProgress - mBitmap!!.width / 2)
        if (left < 1) {
            left = 1f
        } else if (left + mBitmap!!.width >= width) {
            left = width - mBitmap!!.width.toFloat()
        }
        canvas.drawBitmap(mBitmap!!, left, 1f, paint)
    }

    fun setProgress(progress: Float) {
        if (mProgress > progress) {
            mProgress = 1f
        }
        val valueAnimator = ValueAnimator.ofObject(FloatEvaluator(), mProgress, progress)
        valueAnimator.addUpdateListener { animation ->
            mProgress = animation.animatedValue as Float
            invalidate()
        }
        valueAnimator.duration = 3000
        valueAnimator.start()
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