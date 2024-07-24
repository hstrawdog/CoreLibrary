package com.easy.core.widget.ptr

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import java.lang.Math.abs

/**
 * @Author : huangqiqiang
 * @Package : com.rongji.core.widget.ptr
 * @Date : 下午 3:42
 * @Email : qiqiang213@gmail.com
 * @Describe : 处理  横向冲突问题  但效果并不是非常完美
 *   解决的是 父布局 横向滑动的Fragment  与下拉刷新的拦截问题
 *
 */
class PtrFrame4HorizontalConflictLayout : PtrClassicFrameLayout {
    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {}

    /**
     * 横向控制移动的最大坐标
     */
    var maxX = 110

    var startX = 0  //手指碰到屏幕时的 X坐标
    var startY = 0 //手机碰到屏幕时的 Y坐标
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        when (ev!!.action) {
            MotionEvent.ACTION_DOWN -> {
                //当手指按下时，得到了X，Y，
                startX = ev.x.toInt()
                startY = ev.y.toInt()
                //
                parent.requestDisallowInterceptTouchEvent(true)
            }

            MotionEvent.ACTION_MOVE -> {
                //抬起手后得到的坐标，
                val endX = ev.x.toInt()
                val endY = ev.y.toInt()
                //得到绝对值 。
                val disX = abs(endX - startX)
                val disY = abs(endY - startY)
                //如果X轴 大于Y 轴，说明实在左右移动 为什么？
                // 屏幕坐标，X，Y从左上角开始。0，0
                if (disX > disY) {
                    //这个地方，判断了左右滑动的灵敏度，只有当左右滑动距离110 此时父布局才有作用，不拦截。
                    if (disX > maxX) { //结束的时候大于
                        //当滑动的距离大于100的时候，才不拦截parent的事件 父控件才会有用。
                        parent.requestDisallowInterceptTouchEvent(false)
                    }
                } else {
                    // 说明是上下滑动  //canScrollVertically 检查此视图是否可以按某个方向垂直滚动。 负数表示上下滚动。正数表示左右滚动
                    //return  true如果视图可以按指定的方向滚动，否则为false。
                    //既然是上下滑动，此时，父控件就不能有 事件 true停止
                    parent.requestDisallowInterceptTouchEvent(true)
                }
            }

            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> parent.requestDisallowInterceptTouchEvent(false)
        }
        return super.dispatchTouchEvent(ev)

    }
}