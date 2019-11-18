package com.hqq.example.ui.customize.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.hqq.core.utils.log.LogUtils;

import java.security.acl.LastOwnerException;
import java.util.Calendar;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui.customize.widget
 * @FileName :   ClockView
 * @Date : 2019/11/15 0015  下午 4:42
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public class ClockView extends View {

    public ClockView(Context context) {
        super(context);
    }

    public ClockView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ClockView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = drawBg(canvas);

        paint.setColor(Color.RED);

        drawSecond(canvas, paint);

        drawMinute(canvas, paint);
        drawHour(canvas, paint);

        postInvalidateDelayed(1000);
    }

    private void drawMinute(Canvas canvas, Paint paint) {
        int minute = Calendar.getInstance().get(Calendar.MINUTE);
        canvas.save();
        canvas.rotate(6 * minute, 0, getWidth() / 2);
        canvas.drawLine(0, getHeight() / 2 + 30, 0, getHeight() / 2 - 80, paint);
        canvas.restore();
    }

    private void drawHour(Canvas canvas, Paint paint) {
        int hour = Calendar.getInstance().get(Calendar.HOUR);

        LogUtils.e("--------------------   " + hour);
        canvas.save();
        canvas.rotate(30 * hour, 0, getWidth() / 2);
        canvas.drawLine(0, getHeight() / 2 + 30, 0, getHeight() / 2 - 60, paint);
        canvas.restore();
    }

    private void drawSecond(Canvas canvas, Paint paint) {
        int second = Calendar.getInstance().get(Calendar.SECOND);
        LogUtils.e("--------------------   " + second);
        canvas.save();
        canvas.rotate(6 * second, 0, getWidth() / 2);
        canvas.drawLine(0, getHeight() / 2 + 30, 0, getHeight() / 2 - 180, paint);
        canvas.restore();
    }


    private Paint drawBg(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.GRAY);
        paint.setStrokeWidth(5.0f);
        paint.setAntiAlias(true);//设置抗锯齿
        canvas.translate(getWidth() / 2, 0);
        canvas.save();
        for (int i = 0; i < 4; i++) {
            canvas.drawLine(0, 0, 0, 100, paint);
            canvas.rotate(90, 0, getWidth() / 2);
        }

        for (int i = 0; i < 12; i++) {
            canvas.drawLine(0, 0, 0, 40, paint);
            canvas.rotate(30, 0, getWidth() / 2);
        }
        for (int i = 0; i < 60; i++) {
            canvas.drawLine(0, 0, 0, 3, paint);
            canvas.rotate(6, 0, getWidth() / 2);
        }

        canvas.drawCircle(0, getHeight() / 2, 20, paint);
        canvas.restore();

        return paint;
    }
}
