package com.hqq.core.app.ui.activity.customize.weight;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hqq.core.app.R;
import com.hqq.core.utils.ResourcesUtils;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.app.ui.activity.customize.weight
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
        initView();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        BitmapFactory.Options options = new BitmapFactory.Options();
        BitmapFactory.decodeResource(getResources(), mDefPic, options);
        //获取图片的宽高
        mHeight = options.outHeight;
        mWidth = options.outWidth;
    }

    int mDefSelectPic = R.mipmap.ic_star_red;
    int mDefPic = R.mipmap.ic_star_gray;
    int mCurrent = 0;
    private int mWidth = 50, mHeight = 50;
    int mMarginRight = 10;

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
            params.setMargins(0, 0, mMarginRight, 0);
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
        mCurrent = (int) (5 * index / length);
        redraw();
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
