package com.hqq.example.ui.customize.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.hqq.core.utils.ResourcesUtils;
import com.hqq.core.utils.log.LogUtils;
import com.hqq.example.R;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui.customize.widget
 * @FileName :   RoundCountdownView
 * @Date : 2019/12/24 0024  下午 5:06
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public class RoundCountdownView extends View {


    /**
     * 进度
     */
    private float mSweepAngle = 0;
    /**
     * 开始角度
     */
    private float mStarAngle = 270;

    /**
     * 总时间
     */
    private int mCountdown = 60;
    /**
     * 剩余时间
     */
    private int mNowCountdown = mCountdown;
    /**
     * 动画
     */
    ValueAnimator mAnimatorSet = ValueAnimator.ofFloat(0, (360));

    public RoundCountdownView(Context context) {
        super(context);
    }

    public RoundCountdownView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);

    }

    public RoundCountdownView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);

    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {

        }

        setBackgroundResource(R.color.color_b36d61);
        startCountdownAnimation();
    }

    /**
     * 执行动画
     */
    private void startCountdownAnimation() {

        mAnimatorSet.setInterpolator(new LinearInterpolator());
        mAnimatorSet.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mSweepAngle = (float) animation.getAnimatedValue();
                mNowCountdown = (int) ((360 - mSweepAngle) / (360 / mCountdown));
                LogUtils.e("onAnimationUpdate", mNowCountdown);
                invalidate();
            }
        });
        mAnimatorSet.setDuration(mCountdown * 1000);
        mAnimatorSet.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (getParent() instanceof ConstraintLayout) {
            ViewGroup.LayoutParams params = getLayoutParams();
            if (params != null) {
                params.height = 0;
                params.width = 0;
            } else {
                params = new ViewGroup.LayoutParams(0, 0);
            }
            setLayoutParams(params);
        }
        int wSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int wSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int hSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int hSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        LogUtils.e("wSpecSize", wSpecSize);
//        if (wSpecMode == MeasureSpec.AT_MOST && hSpecMode == MeasureSpec.AT_MOST) {
//            setMeasuredDimension(300, 300);
//        } else if (wSpecMode == MeasureSpec.AT_MOST) {
//            setMeasuredDimension(300, hSpecSize);
//        } else if (hSpecMode == MeasureSpec.AT_MOST) {
//            setMeasuredDimension(300, 300);
//        }
        wSpecSize = 600;
        hSpecSize = 600;
        setMeasuredDimension(wSpecSize, hSpecSize);
        LogUtils.e("onMeasure getMeasuredWidth", getMeasuredWidth());
        LogUtils.e("onMeasure getMeasuredHeight", getMeasuredHeight());


    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        LogUtils.e("getWidth", getWidth());
        canvasText(canvas);
        canvasBg(canvas);
        cancasArc(canvas);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        mAnimatorSet.cancel();
    }

    private void canvasText(Canvas canvas) {
        Paint mPaint = new Paint();
        Paint.FontMetrics fontMetrics = new Paint.FontMetrics();
        mPaint.getFontMetrics(fontMetrics);
        mPaint.setTextSize(ResourcesUtils.getDimen(R.dimen.x30));
        float offset = (fontMetrics.descent + fontMetrics.ascent) / 2;
        int centerX = (int) (getWidth() / 2 - (mPaint.measureText(mNowCountdown + "秒") / 2));
        int centerY = getHeight() / 2;
        canvas.drawText(mNowCountdown + "秒", centerX, centerY - offset, mPaint);
    }

    private void cancasArc(Canvas canvas) {
        Paint paint1 = new Paint();
        paint1.setAntiAlias(true);
        paint1.setColor(Color.BLUE);
        paint1.setStyle(Paint.Style.STROKE);
        paint1.setStrokeWidth(ResourcesUtils.getDimen(R.dimen.x5));
        canvas.drawArc(0 + mPadding, 0 + mPadding, getWidth() - mPadding, getWidth() - mPadding, mStarAngle, mSweepAngle, false, paint1);
    }

    int mPadding = 5;

    private void canvasBg(Canvas canvas) {

        Paint paint = new Paint();
        paint.setStrokeWidth(5);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2 - mPadding, paint);

    }

}
