package com.hqq.example.ui.customize.widget

import android.content.Context
import android.graphics.BitmapFactory
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.ImageView
import android.widget.LinearLayout
import com.hqq.core.utils.ResourcesUtils.getColor
import com.hqq.core.utils.log.LogUtils.e
import com.hqq.example.R

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.app.ui.customize.weight
 * @FileName :   RatingBarView
 * @Date : 2019/6/24 0024  下午 4:34
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
class RatingBarView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr) {
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val options = BitmapFactory.Options()
        BitmapFactory.decodeResource(resources, mDefPic, options)
        //获取图片的宽高
        mHeight = if (options.outHeight < mHeight) options.outHeight else mHeight
        mWidth = if (options.outWidth < mWidth) options.outWidth else mWidth
        if (measuredHeight != 0 && measuredHeight < mHeight) {
            mWidth = (measuredHeight.toFloat() / mHeight * mWidth).toInt()
            mHeight = measuredHeight
            initView()
        }
    }

    var mDefSelectPic = R.mipmap.ic_star_red
    var mDefPic = R.mipmap.ic_star_gray
    var current = 0
    private var mWidth = 50
    private var mHeight = 50
    var mMarginRight = 10
    var mIsSlide = false
    private fun initView() {
        setBackgroundColor(getColor(context, R.color.white))
        removeAllViews()
        val params = LayoutParams(mWidth, mHeight)
        for (i in 0..4) {
            val imageView = ImageView(context)
            if (i <= current) {
                imageView.setImageResource(mDefSelectPic)
            } else {
                imageView.setImageResource(mDefPic)
            }
            if (i < 5) {
                params.setMargins(0, 0, mMarginRight, 0)
            }
            addView(imageView, params)
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val index: Float
        val length: Float
        if (orientation == HORIZONTAL) {
            index = ev.x
            length = width.toFloat()
        } else {
            index = ev.y
            length = height.toFloat()
        }
        if (mIsSlide) {
            current = (5 * index / length).toInt()
            e(" index " + current + "------" + index)
            redraw()
        }
        return ev.action != MotionEvent.ACTION_UP || super.dispatchTouchEvent(ev)
    }

    private fun redraw() {
        for (i in 0..4) {
            val view = getChildAt(i)
            if (i <= current) {
                (view as ImageView).setImageResource(mDefSelectPic)
            } else {
                (view as ImageView).setImageResource(mDefPic)
            }
        }
    }

    fun setDefSelectPic(defSelectPic: Int) {
        mDefSelectPic = defSelectPic
        initView()
    }

    fun setSelect(select: Int) {
        current = select
    }

    fun setSlide(slide: Boolean) {
        mIsSlide = slide
    }

    fun setWidth(width: Int) {
        mWidth = width
    }

    fun setHeight(height: Int) {
        mHeight = height
    }

    fun setMarginRight(marginRight: Int) {
        mMarginRight = marginRight
    }

    init {
        val array = context.obtainStyledAttributes(attrs, R.styleable.RatingBar)
        mIsSlide = array.getBoolean(R.styleable.RatingBar_is_slide, false)
        mMarginRight = array.getDimension(R.styleable.RatingBar_padding_child, 10f).toInt()
        array.recycle()
        initView()
    }
}