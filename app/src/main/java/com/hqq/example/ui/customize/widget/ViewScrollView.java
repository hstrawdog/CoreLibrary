package com.hqq.example.ui.customize.widget;

import android.animation.PointFEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

import androidx.annotation.Nullable;

import com.hqq.core.utils.ScreenUtils;
import com.hqq.core.utils.log.LogUtils;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui.customize.widget
 * @FileName :   ScrollView
 * @Date : 2019/12/20 0020  下午 4:17
 * @Email : qiqiang213@gmail.com
 * @Descrive : 测试view 的内容滑动 以及边界判断
 */
public class ViewScrollView extends View {
    public ViewScrollView(Context context) {
        super(context);
        mScroller = new Scroller(context);

    }

    public ViewScrollView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(context);

    }

    public ViewScrollView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScroller = new Scroller(context);

    }

    Scroller mScroller;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setStrokeWidth(6);
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);

        canvas.drawLine(0, 0, getWidth(), getHeight(), paint);


//        LogUtils.e("800",""+getWidth());
//        LogUtils.e("getMeasuredWidth()",""+getMeasuredWidth());
//        LogUtils.e("getMeasuredWidth()",""+getMeasuredWidth());
//        LogUtils.e("getMeasuredWidth()",""+getMinimumWidth());
//        LogUtils.e("onDraw",""+ScreenUtils.dip2px(getContext(),800));
        LogUtils.e("onDraw", getScrollX());
        // LogUtils.e("getScreenWidth", "" + ScreenUtils.getScreenWidth(getContext()));
    }

    float distanceX = 0;
    float mKeyX;
    float mKeyX1;

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                LogUtils.e("getRight", getRight());
                LogUtils.e("ACTION_DOWN -----------" + event.getRawX());
                mKeyX = event.getX();
                mKeyX1 = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                LogUtils.e("ACTION_MOVE -----------" + event.getRawX());
                distanceX = mKeyX - event.getRawX();


                scrollBy((int) distanceX, 0);
                if (getScrollX() < 0) {
                    scrollTo(0, 0);
                } else {
                    if (getScrollX() > getWidth() - ScreenUtils.getScreenWidth(getContext())) {
                        scrollTo(getWidth() - ScreenUtils.getScreenWidth(getContext()), 0);
                    }
                }
                mKeyX = event.getRawX();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                LogUtils.e("---------------------------------------------------------------------------------------------------------------------");
                LogUtils.e("ACTION_CANCEL", mKeyX1 - event.getX());
                LogUtils.e("distanceX", distanceX);
                LogUtils.e("getRawX -----------" + event.getRawX());
                LogUtils.e("getX", getX());
                LogUtils.e("getLeft", getLeft());
                LogUtils.e("getRight", getRight());
                LogUtils.e("getTranslationX", getTranslationX());
                LogUtils.e("getScrollX", getScrollX());
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        LogUtils.e("---------------------------------------------------------------------------------------------------------------------");
                        LogUtils.e("getScrollX", getScrollX());
                        int getScrollX = getScrollX();
                        ValueAnimator animator = ValueAnimator.ofInt(0, getScrollX());
                        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                int scrollX = (int) animation.getAnimatedValue();
                                LogUtils.e("scrollX   " + scrollX);
                                LogUtils.e("getScrollX()-scrollX   ", getScrollX - scrollX);
                                scrollTo(getScrollX - scrollX, 0);
                            }
                        });
                        animator.setDuration(5000);
                        animator.start();
                    }
                }, 2 * 1000);
                break;
            default:

        }


        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return true;
            default:

        }
        return super.onTouchEvent(event);

    }
}
