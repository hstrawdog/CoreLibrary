package com.easy.example.ui.customize.widget

import android.animation.ValueAnimator
import android.app.Activity
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.Scroller
import com.easy.core.utils.ScreenUtils.getScreenWidth
import com.easy.core.utils.log.LogUtils.dInfo
import com.easy.core.utils.log.LogUtils.e

/**
 * @Author : huangqiqiang
 * @Package : com.easy.example.ui.customize.widget
 * @FileName :   ScrollView
 * @Date : 2019/12/20 0020  下午 4:17
 * @Email : qiqiang213@gmail.com
 * @Descrive : 测试view 的内容滑动 以及边界判断
 */
class ViewScrollView : View {
    constructor(context: Context?) : super(context) {
        mScroller = Scroller(context)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        mScroller = Scroller(context)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        mScroller = Scroller(context)
    }

    var mScroller: Scroller
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val paint = Paint()
        paint.strokeWidth = 6f
        paint.isAntiAlias = true
        paint.color = Color.RED
        canvas.drawLine(0f, 0f, width.toFloat(), height.toFloat(), paint)


//        LogUtils.dInfo("800",""+getWidth());
//        LogUtils.dInfo("getMeasuredWidth()",""+getMeasuredWidth());
//        LogUtils.dInfo("getMeasuredWidth()",""+getMeasuredWidth());
//        LogUtils.dInfo("getMeasuredWidth()",""+getMinimumWidth());
//        LogUtils.dInfo("onDraw",""+ScreenUtils.dip2px(getContext(),800));
        dInfo("onDraw"+ scrollX)
        // LogUtils.dInfo("getScreenWidth", "" + ScreenUtils.getScreenWidth(getContext()));
    }

    var distanceX = 0f
    var mKeyX = 0f
    var mKeyX1 = 0f


    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                e("getRight"+ right)
                e("ACTION_DOWN -----------" + event.rawX)
                mKeyX = event.x
                mKeyX1 = event.x
            }
            MotionEvent.ACTION_MOVE -> {
                e("ACTION_MOVE -----------" + event.rawX)
                distanceX = mKeyX - event.rawX
                scrollBy(distanceX.toInt(), 0)
                if (scrollX < 0) {
                    scrollTo(0, 0)
                } else {
                    if (scrollX > width - getScreenWidth(context as Activity)) {
                        scrollTo(width - getScreenWidth(context as Activity), 0)
                    }
                }
                mKeyX = event.rawX
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                e("---------------------------------------------------------------------------------------------------------------------")
                e("ACTION_CANCEL"+ mKeyX1 + event.x)
                e("distanceX"+ distanceX)
                e("getRawX -----------" + event.rawX)
                e("getX"+ x)
                e("getLeft"+ left)
                e("getRight"+ right)
                e("getTranslationX"+ translationX)
                e("getScrollX"+ scrollX)
                postDelayed({
                    e("---------------------------------------------------------------------------------------------------------------------")
                    e("getScrollX"+ scrollX)
                    val getScrollX = scrollX
                    val animator = ValueAnimator.ofInt(0, scrollX)
                    animator.addUpdateListener { animation ->
                        val scrollX = animation.animatedValue as Int
                        e("scrollX   $scrollX")
                        e("getScrollX()-scrollX   "+ getScrollX + scrollX)
                        scrollTo(getScrollX - scrollX, 0)
                    }
                    animator.duration = 5000
                    animator.start()
                }, 2 * 1000.toLong())
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
}