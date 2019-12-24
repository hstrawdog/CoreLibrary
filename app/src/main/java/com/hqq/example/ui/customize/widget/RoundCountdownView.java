package com.hqq.example.ui.customize.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

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

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (getParent() instanceof ConstraintLayout) {
            ViewGroup.LayoutParams params = getLayoutParams();
            if (params!=null) {
                params.height=0;
                params.width=0;
            }else {
                params=new ViewGroup.LayoutParams(0,0);
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
        wSpecSize = 300;
        setMeasuredDimension(wSpecSize, 300);
        LogUtils.e("onMeasure getWidth", getMeasuredWidth());


    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        LogUtils.e("getWidth", getWidth());
    }

}
