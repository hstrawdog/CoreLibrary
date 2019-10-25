package com.hqq.example.app.ui.customize.weight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.app.ui.customize.weight
 * @FileName :   SubscriptView
 * @Date : 2019/7/26 0026  下午 3:01
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public class SubscriptView extends View {
    public SubscriptView(Context context) {
        super(context);
    }

    public SubscriptView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SubscriptView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Path path = new Path();
        path.moveTo(0, 0);
        path.moveTo(100, 0);
        path.moveTo(50, 50);


        Paint paint = new Paint();

        paint.setColor(Color.RED);
        canvas.drawPath(path, paint);
    }
}
