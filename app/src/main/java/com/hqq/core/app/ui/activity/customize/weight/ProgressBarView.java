package com.hqq.core.app.ui.activity.customize.weight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.hqq.core.utils.log.LogUtils;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: huangqiqiang
 * 在此写用途
 * @FileName: com.hqq.core.app.ui.activity.customize.weight.ProgressBarView.java
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

    int defProgress = 10;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        canvas.drawRoundRect(0, 0, getWidth(), getHeight(), 20, 20, paint);
        paint.setColor(Color.BLUE);
        LogUtils.e(defProgress);


        LogUtils.e(((getWidth() / 100 )+ "-----" +((float) (getWidth() / 1000f)) * (defProgress >= 100 ? 100 : defProgress) + "----" + getWidth()));
        canvas.drawRoundRect(0, 0, ((float) (getWidth() / 100.0f)) * (defProgress >= 100 ? 100 : defProgress), getHeight(), 20, 20, paint);
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                defProgress = (int) ((((float) event.getX() ) / getWidth()) * 100);
                LogUtils.e("ACTION_MOVE " + defProgress);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                defProgress = (int) ((((float) event.getX()) / getWidth()) * 100);
                LogUtils.e("ACTION_UP  " + defProgress);
                invalidate();
                break;

        }

        return true;


    }


}
