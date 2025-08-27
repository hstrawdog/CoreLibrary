package com.easy.core.utils.text

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BlurMaskFilter
import android.graphics.BlurMaskFilter.Blur
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.net.Uri
import android.text.*
import android.text.TextUtils
import android.text.style.AlignmentSpan
import android.text.style.BackgroundColorSpan
import android.text.style.BulletSpan
import android.text.style.CharacterStyle
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.ImageSpan
import android.text.style.LeadingMarginSpan
import android.text.style.MaskFilterSpan
import android.text.style.QuoteSpan
import android.text.style.RelativeSizeSpan
import android.text.style.ScaleXSpan
import android.text.style.StrikethroughSpan
import android.text.style.StyleSpan
import android.text.style.SubscriptSpan
import android.text.style.SuperscriptSpan
import android.text.style.TypefaceSpan
import android.text.style.URLSpan
import android.text.style.UnderlineSpan
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.easy.core.CoreConfig
import com.easy.core.utils.ResourcesUtils
import android.text.style.SuperscriptSpan as SuperscriptSpan1

/**
 * @Author : huangqiqiang
 * @Package : com.qi.core.utils
 * @FileName :   TextSpannableBuilder
 * @Date : 2018/12/19 0019  下午 4:55
 * @Email :  qiqiang213@gmail.com
 * @Describe : String 工具类 点击事件等待完善
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
     * @param color
     * @param text
     * @return
     */
    fun addTextPart(text: CharSequence, @ColorInt color: Int): TextSpannableBuilder {
        if (!TextUtils.isEmpty(text)) {
            val start = mStringBuilder.length
            val end = start + text.length
            mStringBuilder.append(text)
            addPartTextColor(color, start, end)
        }
        return this
    }

//    /**
//     * 添加文本
//     *
//     * @param context context
//     * @param colorId 颜色值
//     * @param text    内容
//     * @return
//     */
//    fun addTextPart(context: Context?, @ColorRes colorId: Int, text: CharSequence): TextSpannableBuilder {
//        return addTextPart(ContextCompat.getColor(context!!, colorId), text)
//    }

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
            mStringBuilder.setSpan(TextClickableSpan(text, color, listener), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        return this
    }


    /**
     * 添加文本
     * @param text CharSequence  文字
     * @param color Int  颜色
     * @param proportion Float 大小 倍数
     * @param characterStyle CharacterStyle?  字符样式
     * @param listener OnClickListener?  点击事件  点击后带背景颜色  需要设置 setHighlightColor(ResourcesUtils.getColor(R.color.transparent))  点击焦点 setMovementMethod(LinkMovementMethod.getInstance())
     * @return TextSpannableBuilder
     */
    fun addTextPart(text: CharSequence, @ColorInt color: Int, proportion: Float = 0f,
                    characterStyle: CharacterStyle? = null, listener: OnClickListener? = null): TextSpannableBuilder {
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
            listener?.let {
                mStringBuilder.setSpan(TextClickableSpan(text, color, it), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
        }
        return this
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
    fun addTextPartColorAndSize(context: Context?, @ColorRes colorId: Int, proportion: Float,
                                text: CharSequence): TextSpannableBuilder {
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

    fun build(): Spannable {
        return mStringBuilder
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