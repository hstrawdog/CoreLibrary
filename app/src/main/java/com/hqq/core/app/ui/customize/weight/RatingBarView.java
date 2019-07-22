package com.hqq.core.app.ui.customize.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hqq.core.app.R;
import com.hqq.core.utils.ResourcesUtils;
import com.hqq.core.utils.log.LogUtils;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.app.ui.customize.weight
 * @FileName :   RatingBarView
 * @Date : 2019/6/24 0024  下午 4:34
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public class RatingBarView extends LinearLayout {
    public RatingBarView(Context context) {
        this(context, null);
    }

    public RatingBarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RatingBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RatingBar);
        mIsSlide = array.getBoolean(R.styleable.RatingBar_is_slide, false);
        mMarginRight = (int) array.getDimension(R.styleable.RatingBar_padding_child, 10);

        array.recycle();
        initView();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        BitmapFactory.Options options = new BitmapFactory.Options();
        BitmapFactory.decodeResource(getResources(), mDefPic, options);
        //获取图片的宽高
        mHeight = options.outHeight < mHeight ? options.outHeight : mHeight;
        mWidth = options.outWidth < mWidth ? options.outWidth : mWidth;
        if (getMeasuredHeight() != 0 && getMeasuredHeight() < mHeight) {
            mWidth = (int) ((float) getMeasuredHeight() / mHeight * mWidth);
            mHeight = getMeasuredHeight();
            initView();
        }
    }

    int mDefSelectPic = R.mipmap.ic_star_red;
    int mDefPic = R.mipmap.ic_star_gray;
    int mCurrent = 0;
    private int mWidth = 50, mHeight = 50;
    int mMarginRight = 10;
    boolean mIsSlide = false;

    private void initView() {

        setBackgroundColor(ResourcesUtils.getColor(getContext(), R.color.white));
        removeAllViews();
        LayoutParams params = new LayoutParams(mWidth, mHeight);
        for (int i = 0; i < 5; i++) {
            ImageView imageView = new ImageView(getContext());
            if (i <= mCurrent) {
                imageView.setImageResource(mDefSelectPic);
            } else {
                imageView.setImageResource(mDefPic);
            }
            if (i < 5) {
                params.setMargins(0, 0, mMarginRight, 0);
            }
            addView(imageView, params);
        }
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        final float index, length;
        if (getOrientation() == HORIZONTAL) {
            index = ev.getX();
            length = getWidth();
        } else {
            index = ev.getY();
            length = getHeight();
        }
        if (mIsSlide) {

            mCurrent = (int) (5 * index / length);
            LogUtils.e(" index " + mCurrent +"------"+ index);
            redraw();
        }
        return ev.getAction() != MotionEvent.ACTION_UP || super.dispatchTouchEvent(ev);
    }

    private void redraw() {
        for (int i = 0; i < 5; i++) {
            View view = getChildAt(i);
            if (i <= mCurrent) {
                ((ImageView) view).setImageResource(mDefSelectPic);
            } else {
                ((ImageView) view).setImageResource(mDefPic);
            }
        }
    }

    public void setDefSelectPic(int defSelectPic) {
        mDefSelectPic = defSelectPic;
        initView();
    }

    public void setSelect(int select) {
        mCurrent = select;
    }

    public int getCurrent() {
        return mCurrent;
    }

    public void setSlide(boolean slide) {
        mIsSlide = slide;
    }

    public void setWidth(int width) {
        mWidth = width;
    }

    public void setHeight(int height) {
        mHeight = height;
    }

    public void setMarginRight(int marginRight) {
        mMarginRight = marginRight;
    }
}
