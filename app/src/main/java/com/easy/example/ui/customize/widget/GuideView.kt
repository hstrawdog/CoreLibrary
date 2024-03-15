package com.easy.example.ui.customize.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.easy.core.utils.ResourcesUtils.getDimen
import com.easy.core.utils.ScreenUtils.getStatusBarHeight
import com.easy.example.R

/**
 * @Author : huangqiqiang
 * @Package : com.easy.example.ui.customize.widget
 * @FileName :   GuideView
 * @Date : 2020/5/12 0012  下午 2:01
 * @Email : qiqiang213@gmail.com
 * @Describe : 引导页
 */
class GuideView : View {
    /**
     * 目标View 用于定位透明区域
     */
    var mTargetView: View? = null
    private val mCurtainColor = -0x78000000

    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.xfermode = null
        paint.color = mCurtainColor
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)
        // 设置一下  重叠的部分才能是透明的
        setLayerType(LAYER_TYPE_HARDWARE, paint)
        if (mTargetView != null) {
            val rect = Rect()
            mTargetView!!.getDrawingRect(rect)
            val viewLocation = IntArray(2)
            mTargetView!!.getLocationOnScreen(viewLocation)
            paint.color = Color.WHITE
            paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
            val targetBound = Rect()
            targetBound.left = viewLocation[0]
            targetBound.top = viewLocation[1]
            targetBound.right += targetBound.left
            targetBound.bottom += targetBound.top
            //计算偏移量(对外)
            canvas.drawOval(RectF(viewLocation[0].toFloat(),
                    viewLocation[1] - getStatusBarHeight(context) - getDimen(R.dimen.x88),
                    (rect.right + viewLocation[0]).toFloat(),
                    rect.bottom + viewLocation[1] - getStatusBarHeight(context) - getDimen(R.dimen.x88)
            ), paint)
        }
    }

    fun setTargetView(targetView: View?): GuideView {
        mTargetView = targetView
        postInvalidate()
        return this
    }
}