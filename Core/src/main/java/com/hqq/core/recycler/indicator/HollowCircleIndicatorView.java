package com.hqq.core.recycler.indicator;

import android.content.Context;
import android.graphics.Paint;
import androidx.annotation.Nullable;
import android.util.AttributeSet;

 /**
  * @Author : huangqiqiang
  * @Package : com.hqq.core.recycler.indicator
  * @FileName :   HollowCircleIndicatorImpl
  * @Date  : 2019/4/23 0023
  * @Email :  qiqiang213@gmail.com
  * @Descrive : TODO
  */
public class HollowCircleIndicatorView extends CircleIndicatorView {

    public HollowCircleIndicatorView(Context context) {
        super(context);
    }

    public HollowCircleIndicatorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HollowCircleIndicatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 边框 默认 3px
     */
    int mStrokeWidth = 3;

    @Override
    public void init() {
        super.init();
        mPaintPageFill.setStrokeWidth(mStrokeWidth);
        mPaintPageFill.setStyle(Paint.Style.STROKE);
    }

    public int getStrokeWidth() {
        return mStrokeWidth;
    }

    public void setStrokeWidth(int strokeWidth) {
        mStrokeWidth = strokeWidth;
        mPaintPageFill.setStrokeWidth(mStrokeWidth);
        postInvalidate();
    }
}
