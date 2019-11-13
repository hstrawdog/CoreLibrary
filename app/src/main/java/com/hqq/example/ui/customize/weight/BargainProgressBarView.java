package com.hqq.example.ui.customize.weight;

import android.animation.FloatEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.RequiresApi;

import com.hqq.core.utils.ResourcesUtils;
import com.hqq.core.utils.log.LogUtils;
import com.hqq.example.R;


/**
 * @version V1.0 <描述当前版本功能>
 * @author: huangqiqiang
 * 在此写用途
 * @FileName: com.hqq.core.app.ui.activity.customize.weight.ProgressBarView.java
 * @emain: 593979591@qq.com
 * @date: 2019-06-23 18:41
 */
public class BargainProgressBarView extends View {


    float mProgress = 1;
    @ColorInt
    int mUnselectedColor = Color.RED;
    @ColorInt
    int mSelectedColor = Color.GREEN;
    /**
     * 圆角大小
     */
    int mFilletSize = 10;
    private float mHeight;
    private Bitmap mBitmap;

    public BargainProgressBarView(Context context) {
        super(context);
        init();
    }

    public BargainProgressBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BargainProgressBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        mHeight = ResourcesUtils.getDimen(getContext(), R.dimen.x16);
        mFilletSize = (int) ResourcesUtils.getDimen(getContext(), R.dimen.x8);
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_ax);

        setProgress(100);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, mBitmap.getHeight());
        setMeasuredDimension(widthMeasureSpec, mBitmap.getHeight());
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        canvas.save();
        canvas.translate(0, (getHeight() - mHeight) / 2);
        paint.setColor(mUnselectedColor);
        canvas.drawRoundRect(0 + mBitmap.getWidth() / 2, 0, getWidth() - mBitmap.getWidth() / 2, mHeight, mFilletSize, mFilletSize, paint);
        paint.setColor(mSelectedColor);


        canvas.drawRoundRect(0 + mBitmap.getWidth() / 2, 0, mBitmap.getWidth() / 2 + (((getWidth() - mBitmap.getWidth() / 2) / 100.0f)) * (mProgress >= 100 ? 100 : mProgress), mHeight, mFilletSize, mFilletSize, paint);
        canvas.restore();


        float left = (((getWidth() / 100.0f)) * (mProgress >= 100 ? 100 : mProgress)) - (mBitmap.getWidth() / 2);
        if (left < 1) {
            left = 1;
        } else if (left + mBitmap.getWidth() >= getWidth()) {
            left = getWidth() - mBitmap.getWidth();
        }
        canvas.drawBitmap(mBitmap, left, 1, paint);

    }


    public void setProgress(float progress) {
        if (mProgress > progress) {
            mProgress = 1;
        }
        ValueAnimator valueAnimator = ValueAnimator.ofObject(new FloatEvaluator(), mProgress, progress);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mProgress = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.setDuration(3000);
        valueAnimator.start();
    }

    public void setUnselectedColor(int unselectedColor) {
        mUnselectedColor = unselectedColor;
    }

    public void setSelectedColor(int selectedColor) {
        mSelectedColor = selectedColor;
    }

    public void setFilletSize(int filletSize) {
        mFilletSize = filletSize;
    }
}
