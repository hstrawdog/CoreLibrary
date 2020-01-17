package com.hqq.example.ui.customize.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.hqq.core.utils.log.LogUtils;
import com.hqq.example.R;
import com.hqq.example.ui.customize.widget.imageedit.DrawableSticker;

import java.util.Arrays;

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
    float distanceX = 0;
    float distanceY = 0;
    float mKeyX;
    float mKeyY;
    float mFrame = getWidth() / 8;
    Matrix mCurrentMatrix;
    private Paint borderPaint;

    private void init() {
        mCurrentMatrix = new Matrix();
        mClipPath.moveTo(0, 0);
        mClipPath.lineTo(300, 0);
        mClipPath.lineTo(300, 300);
        mClipPath.lineTo(0, 300);
        mClipPath.lineTo(0, 0);
        mDrawableSticker = new DrawableSticker(ContextCompat.getDrawable(getContext(), R.mipmap.ic_content));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mFrame = getWidth() / 8;
        LogUtils.e("onLayout", "" + getWidth());
    }

    DrawableSticker mDrawableSticker;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initClipPath();

        drawBg(canvas);


        drawClipBg(canvas);

        mDrawableSticker.draw(canvas);

        float[] dst = new float[8];
        mDrawableSticker.getMatrix()
                .mapPoints(dst, getBitmapSize());
        LogUtils.e(Arrays.toString(dst));
        float x1 = dst[0];
        float y1 = dst[1];
        float x2 = dst[2];
        float y2 = dst[3];
        float x3 = dst[4];
        float y3 = dst[5];
        float x4 = dst[6];
        float y4 = dst[7];
        borderPaint = new Paint();
        borderPaint.setAntiAlias(true);
        borderPaint.setColor(Color.RED);
//        borderPaint.setAlpha(128);
        borderPaint.setStrokeWidth(6);
        canvas.drawLine(x1, y1, x2, y2, borderPaint);
        canvas.drawLine(x1, y1, x3, y3, borderPaint);
        canvas.drawLine(x2, y2, x4, y4, borderPaint);
        canvas.drawLine(x4, y4, x3, y3, borderPaint);


    }

    private float[] getBitmapSize() {
        return new float[]{
                0f, 0f, mDrawableSticker
                .getWidth(), 0f, 0f, mDrawableSticker.getHeight(), mDrawableSticker.getWidth(), mDrawableSticker.getHeight()
        };
    }

    boolean isTounch = false;

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                LogUtils.e("getRight", getRight());
                LogUtils.e("ACTION_DOWN -----------" + event.getX());
                mKeyX = event.getX();
                mKeyY = event.getY();

                if (mDrawableSticker.contains(mKeyX, mKeyY)) {
                    isTounch = true;

                    mCurrentMatrix.set(mDrawableSticker.getMatrix()
                    );
                }

                break;
            case MotionEvent.ACTION_MOVE:
                LogUtils.e("ACTION_MOVE -----------" + event.getX());
                distanceX = event.getX() - mKeyX;
                distanceY = event.getY() - mKeyY;
//                mKeyX = event.getX();
//                mKeyY = event.getY();


                float xx = ((getWidth() - mFrame * 2) / getWidth());
                float yy = ((getHeight() - mFrame * 2) / getHeight());


                LogUtils.e("ACTION_MOVE --------distanceX---" + distanceX);
                LogUtils.e("ACTION_MOVE --------distanceY---" + distanceY);
                LogUtils.e("-------------------     " + xx + "            " + yy);

                if (isTounch) {

                    mDrawableSticker.getMatrix()
                            .set(mCurrentMatrix);

                    mDrawableSticker.getMatrix()
                            .postTranslate(distanceX, distanceY);
                    invalidate();
                }


                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                isTounch = false;
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


    private void initClipPath() {
        mClipPath.reset();
        mClipPath.moveTo(mFrame, mFrame);
        mClipPath.lineTo(getWidth() - mFrame, mFrame);
        mClipPath.lineTo(getWidth() - mFrame, getHeight() - mFrame);
        mClipPath.lineTo(mFrame, getHeight() - mFrame);
        mClipPath.lineTo(mFrame, mFrame);
    }

    private void drawClipBg(Canvas canvas) {

        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(mClipPath, paint);
        canvas.clipPath(mClipPath);
        refreshDrawableState();
    }

    private void drawBg(Canvas canvas) {
        canvas.save();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.bg_sty);
        Matrix matrix = new Matrix();
        matrix.setScale(Float.valueOf(getWidth()) / bitmap.getWidth(), Float.valueOf(getHeight()) / bitmap.getHeight());
        canvas.drawBitmap(bitmap, matrix, null);
        canvas.restore();
    }
}
