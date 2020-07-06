package com.hqq.example.ui.customize.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.hqq.core.utils.ResourcesUtils;
import com.hqq.example.R;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;
import static com.hqq.core.utils.ScreenUtils.getStatusBarHeight;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui.customize.widget
 * @FileName :   GuideView
 * @Date : 2020/5/12 0012  下午 2:01
 * @Email : qiqiang213@gmail.com
 * @Descrive : 引导页
 */
public class GuideView extends View {

    /**
     * 目标View 用于定位透明区域
     */
    public View mTargetView;


    private int mCurtainColor = 0x88000000;

    public GuideView(Context context) {
        super(context);
    }

    public GuideView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GuideView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);


        Paint paint = new Paint(ANTI_ALIAS_FLAG);
        paint.setXfermode(null);
        paint.setColor(mCurtainColor);
        canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
        // 设置一下  重叠的部分才能是透明的
        setLayerType(LAYER_TYPE_HARDWARE, paint);


        if (mTargetView != null) {
            Rect rect = new Rect();
            mTargetView.getDrawingRect(rect);


            int[] viewLocation = new int[2];
            mTargetView.getLocationOnScreen(viewLocation);

            paint.setColor(Color.WHITE);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));


            Rect targetBound = new Rect();
            targetBound.left = viewLocation[0];
            targetBound.top = viewLocation[1];
            targetBound.right += targetBound.left;
            targetBound.bottom += targetBound.top;
            //计算偏移量(对外)

            canvas.drawOval(new RectF(viewLocation[0],
                    viewLocation[1] - getStatusBarHeight(getContext()) - ResourcesUtils.getDimen(R.dimen.x88),
                    rect.right + viewLocation[0],
                    rect.bottom + viewLocation[1] - getStatusBarHeight(getContext()) - ResourcesUtils.getDimen(R.dimen.x88)
            ), paint);

        }


    }


    public GuideView setTargetView(View targetView) {
        this.mTargetView = targetView;
        postInvalidate();
        return this;
    }

}
