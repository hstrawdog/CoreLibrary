package com.hqq.coreapp.ui.activity.animation.weight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.coreapp.ui.activity.animation.weight
 * @FileName :   BezierView
 * @Date : 2019/5/24 0024  下午 5:27
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public class BezierView extends View {
    public BezierView(Context context) {
        super(context);
    }

    public BezierView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BezierView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBezier(canvas);
        drawRDuadTo(canvas);
    }

    private void drawRDuadTo(Canvas canvas) {
        canvas.translate(100, 350);
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD));
        paint.setStrokeWidth(5.0f);


        canvas.drawCircle(10, 10, 5, paint);
        canvas.drawCircle(180, 10, 5, paint);
        canvas.drawCircle(180, 50, 5, paint);
        canvas.drawCircle(300, 300, 5, paint);
        canvas.drawLine(10, 10, 180, 10, paint);
        canvas.drawLine(180, 10, 180, 50, paint);
        canvas.drawLine(180, 50, 300, 300, paint);

        Path path = new Path();
        path.moveTo(10, 10);
        path.cubicTo(180, 10, 180,50,300, 300);
        canvas.drawPath(path, paint);
        canvas.save();
    }

    private void drawBezier(Canvas canvas) {
        canvas.translate(100, 50);
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD));
        paint.setStrokeWidth(5.0f);


        canvas.drawCircle(10, 10, 5, paint);
        canvas.drawCircle(180, 10, 5, paint);
        canvas.drawCircle(300, 300, 5, paint);
        canvas.drawLine(10, 10, 180, 10, paint);
        canvas.drawLine(180, 10, 300, 300, paint);

        Path path = new Path();
        path.moveTo(10, 10);
        path.quadTo(180, 10, 300, 300);
        canvas.drawPath(path, paint);
        canvas.save();

    }


}
