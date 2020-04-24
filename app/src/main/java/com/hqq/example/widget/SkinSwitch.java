package com.hqq.example.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.Switch;

import com.hqq.example.R;

import skin.support.content.res.SkinCompatVectorResources;
import skin.support.widget.SkinCompatHelper;
import skin.support.widget.SkinCompatSupportable;

import static skin.support.widget.SkinCompatHelper.INVALID_ID;

/**
 * @version V1.0 <描述当前版本功能>
 * 在此写用途
 * @author: huangqiqiang
 * @FileName: com.shangwenwan.sww.weight.SkinSwitch.java
 * @emain: 593979591@qq.com
 * @date: 2020-04-23 16:31
 */
public class SkinSwitch extends Switch implements SkinCompatSupportable {

    public SkinSwitch(Context context) {
        super(context, null);
    }

    public SkinSwitch(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        init(context, attrs, 0);

    }

    public SkinSwitch(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    int mThumbId;
    int mTrackId;

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = null;
        try {
            a = context.obtainStyledAttributes(
                    attrs, R.styleable.SkinSwitch, defStyleAttr, 0);
            mThumbId = a.getResourceId(R.styleable.SkinSwitch_android_thumb, INVALID_ID);
            mTrackId = a.getResourceId(R.styleable.SkinSwitch_android_track, INVALID_ID);
            applySkin();
        } finally {
            if (a != null) {
                a.recycle();
            }
        }
    }

    @Override
    public void applySkin() {
        mThumbId = SkinCompatHelper.checkResourceId(mThumbId);
        if (mThumbId != INVALID_ID) {
            Drawable drawable = SkinCompatVectorResources.getDrawableCompat(getContext(), mThumbId);
            if (drawable != null) {
                setThumbDrawable(drawable);
            }
        }
        mTrackId = SkinCompatHelper.checkResourceId(mTrackId);
        if (mTrackId != INVALID_ID) {
            Drawable drawable = SkinCompatVectorResources.getDrawableCompat(getContext(), mTrackId);
            if (drawable != null) {
                setTrackDrawable(drawable);
            }
        }
    }
}
