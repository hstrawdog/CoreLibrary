package com.hqq.example.ui.customize.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Typeface;

import androidx.annotation.Nullable;

import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.hqq.example.utils.BezierUtil;

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
        this(context, attrs, 0);
    }

    public BezierView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        startBezierAnimation();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBezier(canvas);
        drawCubicTo(canvas);
    }

    /**
     * 三阶的贝塞尔曲线
     *
     * @param canvas
     */
    private void drawCubicTo(Canvas canvas) {
        canvas.translate(0, 350);
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD));
        paint.setStrokeWidth(5.0f);

        canvas.drawCircle(mCubicPoint.x, mCubicPoint.y, 5, paint);
        canvas.drawCircle(mStartPoint.x, mStartPoint.y, 5, paint);
        canvas.drawCircle(mCtrlPoint.x, mCtrlPoint.y, 5, paint);
        canvas.drawCircle(mEndPoint.x, mEndPoint.y, 5, paint);

        canvas.drawLine(mCubicPoint.x, mCubicPoint.y, mStartPoint.x, mStartPoint.y, paint);
        canvas.drawLine(mStartPoint.x, mStartPoint.y, mCtrlPoint.x, mCtrlPoint.y, paint);
        canvas.drawLine(mCtrlPoint.x, mCtrlPoint.y, mEndPoint.x, mEndPoint.y, paint);

        Path path = new Path();
        path.moveTo(mCubicPoint.x, mCubicPoint.y);
        path.cubicTo(mStartPoint.x, mStartPoint.y, mCtrlPoint.x, mCtrlPoint.y, mEndPoint.x, mEndPoint.y);
        canvas.drawPath(path, paint);
        paint.setColor(Color.BLUE);
        canvas.drawCircle(mMoveThreePoint.x, mMoveThreePoint.y, 5, paint);
        canvas.save();

    }

    private void startBezierAnimation() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(2000).addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float t = (float) animation.getAnimatedValue();
                PointF p = BezierUtil.calculateBezierPointForQuadratic(t,
                        new PointF(mStartPoint.x, mStartPoint.y),
                        new PointF(mCtrlPoint.x, mCtrlPoint.y),
                        new PointF(mEndPoint.x, mEndPoint.y));
                mMovePoint.x = (int) p.x;
                mMovePoint.y = (int) p.y;


                PointF p3 = BezierUtil.calculateBezierPointForCubic(t,
                        new PointF(mCubicPoint.x, mCubicPoint.y), new PointF(mStartPoint.x, mStartPoint.y),
                        new PointF(mCtrlPoint.x, mCtrlPoint.y),
                        new PointF(mEndPoint.x, mEndPoint.y));
                mMoveThreePoint.x = (int) p3.x;
                mMoveThreePoint.y = (int) p3.y;

                //重新绘制View
                invalidate();
            }
        });
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.start();
    }

    Point mStartPoint = new Point(100, 500);
    Point mCtrlPoint = new Point(300, 100);
    Point mEndPoint = new Point(600, 500);

    Point mCubicPoint = new Point(0, 0);



    Point mMovePoint = new Point(10, 10);
    Point mMoveThreePoint = new Point(10, 10);

    /**
     * 二阶
     *
     * @param canvas
     */
    private void drawBezier(Canvas canvas) {
        canvas.translate(100, 50);
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD));
        paint.setStrokeWidth(5.0f);

        Path path = new Path();
        path.moveTo(mStartPoint.x, mStartPoint.y);
        path.quadTo(mCtrlPoint.x, mCtrlPoint.y, mEndPoint.x, mEndPoint.y);

        canvas.drawCircle(mStartPoint.x, mStartPoint.y, 5, paint);
        canvas.drawCircle(mCtrlPoint.x, mCtrlPoint.y, 5, paint);
        canvas.drawCircle(mEndPoint.x, mEndPoint.y, 5, paint);

        canvas.drawLine(mStartPoint.x, mStartPoint.y, mCtrlPoint.x, mCtrlPoint.y, paint);
        canvas.drawLine(mCtrlPoint.x, mCtrlPoint.y, mEndPoint.x, mEndPoint.y, paint);


        canvas.drawPath(path, paint);

        paint.setColor(Color.BLUE);
        canvas.drawCircle(mMovePoint.x, mMovePoint.y, 5, paint);

        canvas.save();

    }


}
