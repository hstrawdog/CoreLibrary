package com.hqq.core.recycler.indicator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

/**
 * @Author : huangqiqiang
 * @Package : com.qi.core.banner.indicator
 * @FileName :   CircleIndicatorImpl
 * @Date : 2019/1/3 0003  下午 7:40
 * @Descrive :
 * @Email :
 */
public class CircleIndicatorView extends View implements Indicator {


    public CircleIndicatorView(Context context) {
        this(context, null);
    }

    public CircleIndicatorView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleIndicatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);
        init();
    }


    /**
     * 小圆点的 总数
     */
    int mColumn = 0;
    /**
     * 默认的画笔
     */
    protected final Paint mPaintPageFill = new Paint(ANTI_ALIAS_FLAG);
    /**
     * 选中的画笔
     */
    private final Paint mPaintFill = new Paint(ANTI_ALIAS_FLAG);
    /**
     * 当前选中的 小圆点
     */
    int mCurrItem = 0;

    /**
     * 默认点的半径
     */
    int mDefRadius = 10;
    /**
     * 点与点的间隔
     */
    int mDefPointPadding = 10;
    /**
     * 默认颜色
     */
    @ColorInt
    int mDefColor = 0xffffffff;
    /**
     * 选中颜色
     */
    @ColorInt
    int mSelectColor = 0xff87582e;

    /**
     * 类型  0 1 2
     */
    int mModel = 2;

    @Override
    public void setCurrentItem(int item) {
        mCurrItem = item;
        invalidate();
    }

    @Override
    public void notifyDataSetChanged() {
        postInvalidate();
    }

    @Override
    public void setPageColumn(int column) {
        mColumn = column;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        // view 的高度  点的高度 +  padding
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(mDefRadius * 2 + mDefPointPadding + 2,
                MeasureSpec.EXACTLY);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        if (mColumn <= 0) {
            return;
        }

        if (mModel == 0) {
            int cx = mDefRadius;
            for (int i = 0; i < mColumn; i++) {
                canvas.drawCircle(cx, getHeight() / 2, mDefRadius, mPaintPageFill);
                cx += getPointSize();
            }
            canvas.drawCircle((getPointSize() * (mCurrItem)) + mDefRadius, getHeight() / 2, mDefRadius, mPaintFill);

        } else if (mModel == 1) {

            int cx = (getWidth() - (getPointSize() * mColumn)) / 2;
            for (int i = 0; i < mColumn; i++) {
                canvas.drawCircle(cx, getHeight() / 2, mDefRadius, mPaintPageFill);
                cx += getPointSize();
            }
            canvas.drawCircle(((getWidth() - (getPointSize() * mColumn)) / 2) + (getPointSize() * (mCurrItem)), getHeight() / 2, mDefRadius, mPaintFill);


        } else {
            int cx = getWidth() - (getPointSize() * mColumn) - mDefRadius;
            for (int i = 0; i < mColumn; i++) {
                canvas.drawCircle(cx, getHeight() / 2, mDefRadius, mPaintPageFill);
                cx += getPointSize();
            }
            canvas.drawCircle(getWidth() - (getPointSize() * (mColumn - mCurrItem)) - mDefRadius, getHeight() / 2, mDefRadius, mPaintFill);
        }

    }

    public void setDefPointPadding(int defPointPadding) {
        mDefPointPadding = defPointPadding;
    }

    public void setDefRadius(int defRadius) {
        mDefRadius = defRadius;
    }

    public void setDefColor(int defColor) {
        mDefColor = defColor;
        mPaintPageFill.setColor(mDefColor);
    }

    public void setSelectColor(int selectColor) {
        mSelectColor = selectColor;
        mPaintFill.setColor(mSelectColor);

    }

    public void setModel(int model) {
        mModel = model;
    }

    public void init() {
        mPaintPageFill.setStyle(Paint.Style.FILL);
        mPaintPageFill.setColor(mDefColor);
        mPaintFill.setStyle(Paint.Style.FILL);
        mPaintFill.setColor(mSelectColor);
    }


    /**
     * 点的 宽度
     *
     * @return
     */
    private int getPointSize() {
        return mDefRadius * 2 + mDefPointPadding;
    }
}
