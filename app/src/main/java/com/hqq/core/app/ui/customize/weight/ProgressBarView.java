package com.hqq.core.app.ui.customize.weight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import androidx.annotation.ColorInt;
import androidx.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.hqq.core.utils.log.LogUtils;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: huangqiqiang
 * 在此写用途
 * @FileName: com.hqq.core.app.ui.customize.weight.ProgressBarView.java
 * @emain: 593979591@qq.com
 * @date: 2019-06-23 18:41
 */
public class ProgressBarView extends View {


    public ProgressBarView(Context context) {
        super(context);
    }

    public ProgressBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ProgressBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    float mProgress = 10;
    @ColorInt
    int mUnselectedColor = Color.RED;
    @ColorInt
    int mSelectedColor = Color.GREEN;
    /**
     * 圆角大小
     */
    int mFilletSize = 10;

    boolean mSlide = true;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(mUnselectedColor);
        canvas.drawRoundRect(0, 0, getWidth(), getHeight(), mFilletSize, mFilletSize, paint);
        paint.setColor(mSelectedColor);

        canvas.drawRoundRect(0, 0, ((getWidth() / 100.0f)) * (mProgress >= 100 ? 100 : mProgress), getHeight(), mFilletSize, mFilletSize, paint);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        LogUtils.e(event.getAction());
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                if (mSlide) {
                    mProgress = (((event.getX()) / getWidth()) * 100);
                    invalidate();
                }
                break;
            default:
        }
        return event.getAction() != MotionEvent.ACTION_UP || super.dispatchTouchEvent(event);

    }


    public void setProgress(float progress) {
        this.mProgress = progress;
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
