package com.hqq.example.ui.customize.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.hqq.core.utils.ScreenUtils;
import com.hqq.core.utils.log.LogUtils;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui.customize.widget
 * @FileName :   ScrollView
 * @Date : 2019/12/20 0020  下午 4:17
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public class ScrollView extends View {
    public ScrollView(Context context) {
        super(context);
    }

    public ScrollView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setStrokeWidth(6);
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);

        canvas.drawLine(0, 0, getWidth(), getHeight(), paint);
    }

    float distanceX = 0;
    float mKeyX;

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                LogUtils.e("ACTION_DOWN -----------" + event.getRawX());
                mKeyX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                LogUtils.e("ACTION_MOVE -----------" + event.getRawX());
                distanceX = mKeyX - event.getRawX();
                scrollBy((int) distanceX, 0);

                if (getScrollX() < 0) {
                    scrollTo(0, 0);
                } else {
                    if (getScrollX() > getWidth()- ScreenUtils.getScreenWidth(getContext())) {
                        scrollTo(ScreenUtils.getScreenWidth(getContext()), 0);
                    }

                }

                mKeyX = event.getRawX();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                LogUtils.e("ACTION_UP -----------" + event.getRawX());

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
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:

                break;
            default:

        }


        return super.onTouchEvent(event);

    }
}
