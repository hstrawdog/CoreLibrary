package com.hqq.core.utils.text

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
     * @param text CharSequence  文字
     * @param color Int  颜色
     * @param proportion Float 大小 倍数
     * @param characterStyle CharacterStyle?  字符样式
     * @param listener OnClickListener?  点击事件
     * @return TextSpannableBuilder
     */
    fun addTextPart(text: CharSequence, @ColorInt color: Int, proportion: Float = 0f,
                    characterStyle: CharacterStyle? = null): TextSpannableBuilder {
        if (!TextUtils.isEmpty(text)) {
            val start = mStringBuilder.length
            val end = start + text.length
            mStringBuilder.append(text)
            addPartTextColor(color, start, end)
            if (proportion != 0f) {
                addPartTextSize(proportion, start, end)
            }

            characterStyle?.let {
                mStringBuilder.setSpan(it, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
        }
        return this
    }

    /**
     *  添加点击事件
     * @param text CharSequence
     * @param color Int
     * @param listener OnClickListener?
     */
    fun addTextClick(text: CharSequence, @ColorInt color: Int, listener: OnClickListener? = null) {
        if (!TextUtils.isEmpty(text)) {
            val start = mStringBuilder.length
            val end = start + text.length
            listener?.let {
                mStringBuilder.setSpan(TextClickableSpan(text, color, it), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
        }
    }

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
     *  点击事件 类
     */
    class TextClickableSpan(private val mText: CharSequence, private val mColor: Int,
                            private val mOnClickListener: OnClickListener?) : ClickableSpan() {
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
     *  构建Spannable
     * @return Spannable
     */
    fun build(): Spannable {
        return mStringBuilder
    }

    /**
     * 点击回调
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