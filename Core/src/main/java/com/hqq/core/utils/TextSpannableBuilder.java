package com.hqq.core.utils;

import android.content.Context;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.core.content.ContextCompat;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.CharacterStyle;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;

/**
 * @Author : huangqiqiang
 * @Package : com.qi.core.utils
 * @FileName :   TextSpannableBuilder
 * @Date : 2018/12/19 0019  下午 4:55
 * @Descrive : String 工具类 点击事件等待完善
 * @Email :  qiqiang213@gmail.com
 */
public class TextSpannableBuilder {

    public interface OnClickListener {
        void onClick(View widget, CharSequence clickedText);
    }

    /**
     * 点击事件
     */
    public static class TextClickableSpan extends ClickableSpan {

        private final CharSequence mText;
        private final int mColor;
        private final OnClickListener mOnClickListener;

        public TextClickableSpan(CharSequence text, int color, OnClickListener onClickListener) {
            mText = text;
            mColor = color;
            mOnClickListener = onClickListener;
        }

        @Override
        public void onClick(View widget) {
            if (mOnClickListener != null) {
                mOnClickListener.onClick(widget, mText);
            }
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            //super.updateDrawState(ds);
            if (mColor != 0) {
                ds.setColor(mColor);
            }
        }
    }

    private final SpannableStringBuilder mStringBuilder = new SpannableStringBuilder();

    private void addPartTextColor(int color, int start, int end) {
        mStringBuilder.setSpan(new ForegroundColorSpan(color), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    private void addPartTextSize(float proportion, int start, int end) {
        mStringBuilder.setSpan(new RelativeSizeSpan(proportion), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    public TextSpannableBuilder addTextPart(CharSequence text) {
        mStringBuilder.append(text);
        return this;
    }

    public TextSpannableBuilder addTextPart(Context context, @ColorRes int colorId, CharSequence text) {
        return addTextPart(ContextCompat.getColor(context, colorId), text);
    }

    public TextSpannableBuilder addTextPart(@ColorInt int color, CharSequence text) {
        if (!TextUtils.isEmpty(text)) {
            final int start = mStringBuilder.length();
            final int end = start + text.length();
            mStringBuilder.append(text);
            addPartTextColor(color, start, end);
        }
        return this;
    }

    public TextSpannableBuilder addTextPart(CharSequence text, Context context, int colorId, OnClickListener listener) {
        return addTextPart(text, ContextCompat.getColor(context, colorId), listener);
    }


    public TextSpannableBuilder addTextPartColorAndSize(Context context, @ColorRes int colorId, float proportion, CharSequence text) {
        if (!TextUtils.isEmpty(text)) {
            final int start = mStringBuilder.length();
            final int end = start + text.length();
            mStringBuilder.append(text);
            addPartTextColor(ContextCompat.getColor(context, colorId), start, end);
            addPartTextSize(proportion, start, end);

        }
        return this;
    }

    public TextSpannableBuilder addTextSizeSpan(float proportion
            , CharSequence text) {
        if (!TextUtils.isEmpty(text)) {
            final int start = mStringBuilder.length();
            final int end = start + text.length();
            mStringBuilder.append(text);
            addPartTextSize(proportion, start, end);
        }
        return this;
    }

    public TextSpannableBuilder addTextPart(CharSequence text, CharacterStyle characterStyle) {
        if (!TextUtils.isEmpty(text)) {
            final int start = mStringBuilder.length();
            final int end = start + text.length();
            mStringBuilder.append(text);
            mStringBuilder.setSpan(characterStyle, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return this;
    }

    public TextSpannableBuilder addTextPart(CharSequence text, int color, OnClickListener listener) {
        if (!TextUtils.isEmpty(text)) {
            final int start = mStringBuilder.length();
            final int end = start + text.length();
            mStringBuilder.append(text);
            mStringBuilder.setSpan(new TextClickableSpan(text, color, listener)
                    , start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return this;
    }


    public Spannable build() {
        return mStringBuilder;
    }

}
