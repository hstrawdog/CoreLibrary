package com.hqq.album.customize;

import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;

/**
 * @Author : huangqiqiang
 * @Package : com.rongji.shangwenwan.weight
 * @FileName :   SquareImageView
 * @Date : 2018/7/26 0026  下午 5:41
 * @Descrive : TODO
 * @Email :
 * 宽度为准的 正方形ImageView
 */

public class SquareImageView extends androidx.appcompat.widget.AppCompatImageView {
    public SquareImageView(Context context) {
        super(context);
    }

    public SquareImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
