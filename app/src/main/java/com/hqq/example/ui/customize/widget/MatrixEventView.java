package com.hqq.example.ui.customize.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.hqq.example.R;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.widget
 * @Date : 下午 3:22
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
public class MatrixEventView extends View {
    public MatrixEventView(Context context) {
        super(context);
    }

    public MatrixEventView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MatrixEventView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MatrixEventView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_square);
        Matrix matrix = new Matrix();

        if (mCache != null) {
            double ang = angle(mCache, bitmap.getWidth() / 2, bitmap.getHeight());

            matrix.postRotate((float) ang, bitmap.getWidth() / 2, bitmap.getHeight() / 2);

        }


        Paint paint = new Paint();
        canvas.drawBitmap(bitmap, matrix, paint);

    }

    /**
     * 计算旋转角度
     *
     * @param event
     * @param centerX
     * @param centerY
     * @return
     */
    private double angle(MotionEvent event, float centerX, float centerY) {

        double x = event.getX() - centerX;
        double y = event.getY() - centerY;
        double radians = Math.atan2(y, x);
        return (float) Math.toDegrees(radians);

    }

    MotionEvent mCache;
    MotionEvent mDown;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDown = mDown;

                break;

            case MotionEvent.ACTION_MOVE:


                mCache = event;


                invalidate();
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mCache = null;
                break;
        }

        return true;


    }
}
