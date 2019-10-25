/*
 * Copyright (c) 2017. auth huangqiqiang Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.hqq.example.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.hqq.example.widget.ptr.PtrClassicFrameLayout;


/**
 * @Author : huangqiqiang
 * @Package : com.qi.core.widget
 * @FileName :   CusPtrClassicFrameLayout
 * @Date : 2018/12/21 0021
 * @Email :  qiqiang213@gmail.com
 * @Descrive :  解决下拉 左右滑动冲突
 * https://github.com/LiuTaw/PullToRefresh-AdBannerDemo
 */
public class CusPtrClassicFrameLayout extends PtrClassicFrameLayout {
    public CusPtrClassicFrameLayout(Context context) {
        super(context);
        initGesture();
    }

    public CusPtrClassicFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initGesture();
    }

    public CusPtrClassicFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initGesture();
    }




    private GestureDetector detector;
    private boolean mIsDisallowIntercept = false;

    private void initGesture() {
        detector = new GestureDetector(getContext(), gestureListener);
    }

    private boolean mIsHorizontalMode = false;
    private boolean isFirst = true;

    @Override
    public boolean dispatchTouchEvent(MotionEvent e) {
        if (e.getAction() == MotionEvent.ACTION_UP) {
            isFirst = true;
            mIsHorizontalMode = false;
            mIsDisallowIntercept = false;
            return super.dispatchTouchEvent(e);
        }
        if (detector.onTouchEvent(e) && mIsDisallowIntercept && mIsHorizontalMode) {
            return dispatchTouchEventSupper(e);
        }
        if (mIsHorizontalMode) {
            return dispatchTouchEventSupper(e);
        }
        return super.dispatchTouchEvent(e);
    }

    @Override
    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        this.mIsDisallowIntercept = disallowIntercept;
        super.requestDisallowInterceptTouchEvent(disallowIntercept);
    }

    private GestureDetector.OnGestureListener gestureListener = new GestureDetector.OnGestureListener() {
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public void onShowPress(MotionEvent e) {
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            float disX, disY;
            if (distanceX < 0) {
                disX = -distanceX;
            } else {
                disX = distanceX;
            }
            if (distanceY < 0) {
                disY = -distanceY;
            } else {
                disY = distanceY;
            }

            if (disX > disY) {
                if (isFirst) {
                    mIsHorizontalMode = true;
                    isFirst = false;
                }
            } else {
                if (isFirst) {
                    mIsHorizontalMode = false;
                    isFirst = false;
                }
                //垂直滑动会返回false
                return false;
            }
            //水平滑动会返回true
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }
    };
}
