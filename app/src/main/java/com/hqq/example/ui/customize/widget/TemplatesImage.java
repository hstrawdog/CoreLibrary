package com.hqq.example.ui.customize.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.hqq.core.utils.ScreenUtils;
import com.hqq.core.utils.log.LogUtils;
import com.hqq.example.R;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui.customize.widget
 * @FileName :   TemplatesImage
 * @Date : 2020/1/10 0010  下午 1:35
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public class TemplatesImage extends View {
    public TemplatesImage(Context context) {
        super(context);
        init();
    }

    public TemplatesImage(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public TemplatesImage(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    Path mClipPath = new Path();

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initClipPath();

        drawBg(canvas);


        drawClipBg(canvas);

        canvas.save();
        canvas.translate(-distanceX, -distanceY);
        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_content);
        canvas.drawBitmap(bitmap2, getWidth() / 2 - getWidth() / 4, getHeight() / 2 - getWidth() / 4, null);

        canvas.restore();


    }


    float distanceX = 0;
    float distanceY = 0;
    float mKeyX;
    float mKeyY;

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                LogUtils.e("getRight", getRight());
                LogUtils.e("ACTION_DOWN -----------" + event.getRawX());
                mKeyX = event.getRawX();
                mKeyY = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                LogUtils.e("ACTION_MOVE -----------" + event.getRawX());
                distanceX = distanceX + mKeyX - event.getRawX();
                distanceY = distanceY + mKeyY - event.getRawY();
                LogUtils.e("ACTION_MOVE --------distanceX---" + distanceX);
                LogUtils.e("ACTION_MOVE --------distanceY---" + distanceY);

                invalidate();
                mKeyX = event.getRawX();
                mKeyY = event.getRawY();

                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                LogUtils.e("---------------------------------------------------------------------------------------------------------------------");
                LogUtils.e("distanceX", distanceX);
                LogUtils.e("getRawX -----------" + event.getRawX());
                LogUtils.e("getX", getX());
                LogUtils.e("getLeft", getLeft());
                LogUtils.e("getRight", getRight());
                LogUtils.e("getTranslationX", getTranslationX());
                LogUtils.e("getScrollX", getScrollX());

                break;
            default:

        }


        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return true;
            default:
        }
        return super.onTouchEvent(event);
    }

    private void init() {
        mClipPath.moveTo(0, 0);
        mClipPath.lineTo(300, 0);
        mClipPath.lineTo(300, 300);
        mClipPath.lineTo(0, 300);
        mClipPath.lineTo(0, 0);
    }

    private void initClipPath() {
        mClipPath.reset();

        float frame = getWidth() / 8;

        mClipPath.moveTo(frame, frame);
        mClipPath.lineTo(getWidth() - frame, frame);
        mClipPath.lineTo(getWidth() - frame, getHeight() - frame);
        mClipPath.lineTo(frame, getHeight() - frame);
        mClipPath.lineTo(frame, frame);
    }


    private void drawClipBg(Canvas canvas) {

        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(mClipPath, paint);
        canvas.clipPath(mClipPath);
    }

    private void drawBg(Canvas canvas) {
        canvas.save();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.bg_sty);
        RectF rectF = new RectF(0, 0, getWidth(), getHeight());
        canvas.drawBitmap(bitmap, null, rectF, null);
        canvas.restore();
    }
}
