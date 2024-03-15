/*
 * Copyright (c) 2017. auth huangqiqiang Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */
package com.easy.core.widget

import android.content.Context
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import com.easy.core.widget.ptr.PtrClassicFrameLayout

/**
 * @Author : huangqiqiang
 * @Package : com.qi.core.widget
 * @FileName :   CusPtrClassicFrameLayout
 * @Date : 2018/12/21 0021
 * @Email :  qiqiang213@gmail.com
 * @Describe :  解决下拉 左右滑动冲突
 * https://github.com/LiuTaw/PullToRefresh-AdBannerDemo
 */
class CusPtrClassicFrameLayout : PtrClassicFrameLayout {
    constructor(context: Context?) : super(context) {
        initGesture()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initGesture()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        initGesture()
    }

    private var detector: GestureDetector? = null
    private var mIsDisallowIntercept = false
    private fun initGesture() {
        detector = GestureDetector(context, gestureListener)
    }

    private var mIsHorizontalMode = false
    private var isFirst = true
    override fun dispatchTouchEvent(e: MotionEvent): Boolean {
        if (e.action == MotionEvent.ACTION_UP) {
            isFirst = true
            mIsHorizontalMode = false
            mIsDisallowIntercept = false
            return super.dispatchTouchEvent(e)
        }
        if (detector!!.onTouchEvent(e) && mIsDisallowIntercept && mIsHorizontalMode) {
            return dispatchTouchEventSupper(e)
        }
        return if (mIsHorizontalMode) {
            dispatchTouchEventSupper(e)
        } else super.dispatchTouchEvent(e)
    }

    override fun requestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
        mIsDisallowIntercept = disallowIntercept
        super.requestDisallowInterceptTouchEvent(disallowIntercept)
    }

    private val gestureListener: GestureDetector.OnGestureListener = object : GestureDetector.OnGestureListener {
        override fun onDown(e: MotionEvent): Boolean {
            return true
        }

        override fun onShowPress(e: MotionEvent) {}
        override fun onSingleTapUp(e: MotionEvent): Boolean {
            return false
        }

        override fun onScroll(e1: MotionEvent, e2: MotionEvent, distanceX: Float, distanceY: Float): Boolean {
            val disX: Float
            val disY: Float
            disX = if (distanceX < 0) {
                -distanceX
            } else {
                distanceX
            }
            disY = if (distanceY < 0) {
                -distanceY
            } else {
                distanceY
            }
            if (disX > disY) {
                if (isFirst) {
                    mIsHorizontalMode = true
                    isFirst = false
                }
            } else {
                if (isFirst) {
                    mIsHorizontalMode = false
                    isFirst = false
                }
                //垂直滑动会返回false
                return false
            }
            //水平滑动会返回true
            return true
        }

        override fun onLongPress(e: MotionEvent) {}
        override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
            return false
        }
    }
}