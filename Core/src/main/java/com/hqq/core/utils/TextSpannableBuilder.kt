package com.hqq.core.utils

import android.content.Context
import android.text.*
import android.text.style.CharacterStyle
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

/**
 * @Author : huangqiqiang
 * @Package : com.qi.core.utils
 * @FileName :   TextSpannableBuilder
 * @Date : 2018/12/19 0019  下午 4:55
 * @Email :  qiqiang213@gmail.com
 * @Descrive : String 工具类 点击事件等待完善
 */
class TextSpannableBuilder {
    /**
     * 缓存的集合
     */
    private val mStringBuilder = SpannableStringBuilder()

    /**
     * 添加文本颜色
     *
     * @param color
     * @param start
     * @param end
     */
    private fun addPartTextColor(color: Int, start: Int, end: Int) {
        mStringBuilder.setSpan(ForegroundColorSpan(color), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    }

    /**
     * 添加文字大小
     *
     * @param proportion
     * @param start
     * @param end
     */
    private fun addPartTextSize(proportion: Float, start: Int, end: Int) {
        mStringBuilder.setSpan(RelativeSizeSpan(proportion), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    }

    /**
     * 添加文本
     *
     * @param text
     * @return
     */
    fun addTextPart(text: CharSequence?): TextSpannableBuilder {
        mStringBuilder.append(text)
        return this
    }

    /**
     * 添加文本
     *
     * @param context context
     * @param colorId 颜色值
     * @param text    内容
     * @return
     */
    fun addTextPart(context: Context?, @ColorRes colorId: Int, text: CharSequence): TextSpannableBuilder {
        return addTextPart(ContextCompat.getColor(context!!, colorId), text)
    }

    /**
     * 添加文本
     *
     * @param color
     * @param text
     * @return
     */
    fun addTextPart(@ColorInt color: Int, text: CharSequence): TextSpannableBuilder {
        if (!TextUtils.isEmpty(text)) {
            val start = mStringBuilder.length
            val end = start + text.length
            mStringBuilder.append(text)
            addPartTextColor(color, start, end)
        }
        return this
    }

    /**
     * 添加文本
     *
     * @param text
     * @param context
     * @param colorId
     * @param listener
     * @return
     */
    fun addTextPart(text: CharSequence, context: Context?, colorId: Int, listener: OnClickListener?): TextSpannableBuilder {
        return addTextPart(text, ContextCompat.getColor(context!!, colorId), listener)
    }

    /**
     * 添加文本
     *
     * @param context
     * @param colorId
     * @param proportion
     * @param text
     * @return
     */
    fun addTextPartColorAndSize(context: Context?, @ColorRes colorId: Int, proportion: Float, text: CharSequence): TextSpannableBuilder {
        if (!TextUtils.isEmpty(text)) {
            val start = mStringBuilder.length
            val end = start + text.length
            mStringBuilder.append(text)
            addPartTextColor(ContextCompat.getColor(context!!, colorId), start, end)
            addPartTextSize(proportion, start, end)
        }
        return this
    }

    /**
     * 添加文本
     *
     * @param proportion 文字大小
     * @param text       内容
     * @return
     */
    fun addTextSizeSpan(proportion: Float, text: CharSequence): TextSpannableBuilder {
        if (!TextUtils.isEmpty(text)) {
            val start = mStringBuilder.length
            val end = start + text.length
            mStringBuilder.append(text)
            addPartTextSize(proportion, start, end)
        }
        return this
    }

    /**
     * 添加文本
     *
     * @param text
     * @param characterStyle
     * @return
     */
    fun addTextPart(text: CharSequence, characterStyle: CharacterStyle?): TextSpannableBuilder {
        if (!TextUtils.isEmpty(text)) {
            val start = mStringBuilder.length
            val end = start + text.length
            mStringBuilder.append(text)
            mStringBuilder.setSpan(characterStyle, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        return this
    }

    /**
     * 添加文本
     *
     * @param text
     * @param color
     * @param listener
     * @return
     */
    fun addTextPart(text: CharSequence, color: Int, listener: OnClickListener?): TextSpannableBuilder {
        if (!TextUtils.isEmpty(text)) {
            val start = mStringBuilder.length
            val end = start + text.length
            mStringBuilder.append(text)
            mStringBuilder.setSpan(TextClickableSpan(text, color, listener)
                    , start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        return this
    }

    fun build(): Spannable {
        return mStringBuilder
    }

    /**
     * 点击事件
     */
    class TextClickableSpan(private val mText: CharSequence, private val mColor: Int, private val mOnClickListener: OnClickListener?) : ClickableSpan() {
        override fun onClick(widget: View) {
            mOnClickListener?.onClick(widget, mText)
        }

        override fun updateDrawState(ds: TextPaint) {
            //super.updateDrawState(ds);
            if (mColor != 0) {
                ds.color = mColor
            }
        }

    }

    /**
     * 接口
     */
    interface OnClickListener {
        /**
         * 回调
         *
         * @param widget
         * @param clickedText
         */
        fun onClick(widget: View?, clickedText: CharSequence?)
    }
}