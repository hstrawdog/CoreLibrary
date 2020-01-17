package com.hqq.example.ui.customize.widget.imageedit;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui.customize.widget.imageedit
 * @FileName :   DrawableSticker
 * @Date : 2020/1/15 0015  上午 11:51
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public class DrawableSticker extends  Sticker {
    private Drawable drawable;
    private Rect realBounds;

    public DrawableSticker(Drawable drawable) {
        this.drawable = drawable;
        this.matrix = new Matrix();
        realBounds = new Rect(0, 0, getWidth(), getHeight());

    }

    @Override
    public void draw(Canvas canvas) {
        canvas.save();
        canvas.concat(matrix);
        drawable.setBounds(realBounds);
        drawable.draw(canvas);
        canvas.restore();
    }

    @Override
    public int getWidth() {
        return drawable.getIntrinsicWidth();

    }

    @Override
    public int getHeight() {
        return drawable.getIntrinsicHeight();

    }
}
