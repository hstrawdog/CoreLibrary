package com.hqq.example.ui.customize.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.hqq.core.CoreConfig;
import com.hqq.core.utils.ResourcesUtils;
import com.hqq.core.utils.ScreenUtils;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui.customize.widget
 * @Date : 上午 11:20
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public class DiskView extends View {
    public DiskView(Context context) {
        this(context, null);
    }

    public DiskView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DiskView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(dotColor);
        mPaint.setTextAlign(Paint.Align.LEFT);
        //默认20sp
        mPaint.setTextSize(ScreenUtils.sp2px(20));
        mRadius= 350;
    }
    float mRadius;

    Paint mPaint;

    //秒针指示点颜色
    int indicatorColor = 0xfff0ff00;

    //秒针点颜色
    int dotColor = 0xffffffff;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画圆点
        mPaint.setColor(dotColor);
        for (int i = 0; i < 60; i++) {
            if (i == 0) {
                mPaint.setColor(indicatorColor);
                canvas.drawCircle(mRadius, 2 * mRadius, ScreenUtils.sp2px(20) / 5, mPaint);
                mPaint.setColor(dotColor);
            } else {
                canvas.drawCircle(mRadius, 2 * mRadius, ScreenUtils.sp2px( 20) / 5, mPaint);
            }
            canvas.rotate(-6, mRadius, mRadius);
        }
    }
}
