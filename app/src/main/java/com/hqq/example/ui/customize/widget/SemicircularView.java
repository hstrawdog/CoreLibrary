package com.hqq.example.ui.customize.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.hqq.core.utils.log.LogUtils;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui.customize.widget
 * @FileName :   Semicircular
 * @Date : 2020/7/6 0006  上午 11:02
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public class SemicircularView extends View {
    private float radius = 100;
    private int mBackgroundArcColor = 0xFF999999;

    public SemicircularView(Context context) {
        super(context);
    }

    public SemicircularView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SemicircularView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        LogUtils.e("widthSpecMode      " + widthSpecMode);
        LogUtils.e("widthSpecSize      " + widthSpecSize);
        LogUtils.e("heightSpecMode     " + heightSpecMode);
        LogUtils.e("heightSpecSize     " + heightSpecSize);

        if (widthSpecMode == MeasureSpec.EXACTLY || heightSpecMode == MeasureSpec.EXACTLY) {
            if (widthSpecSize < heightSpecSize) {
                heightSpecSize = widthSpecSize;
            } else {
                widthSpecSize = heightSpecSize;
            }
        }

        radius = (widthSpecSize - getPaddingLeft() - getPaddingRight()) / 2;

        setMeasuredDimension(widthSpecSize, heightSpecSize);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        drawOvel(canvas, Color.RED);


    }

    private void drawOvel(Canvas canvas, int color) {
        float mXCenter = getWidth() / 2;
        float mYCenter = getHeight();
        RectF ovl = new RectF();
        ovl.left = mXCenter - radius;
        ovl.top = mYCenter / 2 - radius;
        ovl.right = mXCenter + radius;
        ovl.bottom = (mYCenter / 2 - radius) + radius * 2;

        Paint mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);
        //开启显示边缘为圆形
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        // 背景
        mPaint.setColor(mBackgroundArcColor);
        canvas.drawArc(ovl, -180, 180, false, mPaint);

        mPaint.setColor(color);
        canvas.drawArc(ovl, -180, 30, false, mPaint);
    }
}
