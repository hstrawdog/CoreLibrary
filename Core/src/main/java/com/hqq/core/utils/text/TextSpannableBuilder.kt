package com.hqq.core.utils.text

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BlurMaskFilter
import android.graphics.BlurMaskFilter.Blur
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.net.Uri
import android.text.*
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
import com.hqq.core.CoreConfig
import com.hqq.core.utils.ResourcesUtils
import com.hqq.core.utils.ResourcesUtils.getResources
import com.hqq.core.utils.RxTool
import android.text.style.SuperscriptSpan as SuperscriptSpan1

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
                mStringBuilder.append(text)
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
    class TextClickableSpan(private val mText: CharSequence, val mColor: Int,
                            private val mOnClickListener: OnClickListener?) : ClickableSpan() {
        override fun onClick(widget: View) {
            mOnClickListener?.onClick(widget, mText)
        }

        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds);
//            if (mColor != 0) {
//                ds.color = mColor
//            }
            ds.setColor(ResourcesUtils.getColor(android.R.color.holo_red_dark));
            ds.setUnderlineText(false);
            ds.clearShadowLayer();
        }

    }

    //region   span
    fun getForegroundColorSpan(@ColorInt color: Int): ForegroundColorSpan {
        return ForegroundColorSpan(color)
    }

    fun getBackgroundColorSpan(@ColorInt color: Int): BackgroundColorSpan {
        return BackgroundColorSpan(color)
    }

    fun getLeadingMarginSpan(first: Int, rest: Int): LeadingMarginSpan.Standard {
        return LeadingMarginSpan.Standard(first, rest)
    }


    fun getQuoteSpan(@ColorInt color: Int): QuoteSpan {

        return QuoteSpan(color)
    }


    fun getBulletSpan(gapWidth: Int, @ColorInt bulletColor: Int): BulletSpan {
        return BulletSpan(gapWidth, bulletColor)
    }


    fun getRelativeSizeSpan(proportion: Float): RelativeSizeSpan {
        return RelativeSizeSpan(proportion)
    }

    fun getScaleXSpan(xProportion: Float): ScaleXSpan {
        return ScaleXSpan(xProportion)
    }

    fun getStrikethroughSpan(): StrikethroughSpan {
        return StrikethroughSpan()
    }

    fun getUnderlineSpan(): UnderlineSpan {
        return UnderlineSpan()
    }

    fun getSuperscriptSpan(): SuperscriptSpan {
        return SuperscriptSpan1();
    }

    fun getSubscriptSpan(): SubscriptSpan {
        return SubscriptSpan()
    }

    fun getStyleSpan4BOLD(): StyleSpan {
        return StyleSpan(Typeface.BOLD)
    }

    fun getStyleSpan4ITALIC(): StyleSpan {
        return StyleSpan(Typeface.ITALIC)
    }

    fun getStyleSpan4BOLD_ITALIC(): StyleSpan {
        return StyleSpan(Typeface.BOLD_ITALIC)
    }

    fun getTypefaceSpan(fontFamily: String): TypefaceSpan {
        return TypefaceSpan(fontFamily)
    }

    fun getAlignmentSpan(align: Layout.Alignment): AlignmentSpan.Standard {
        return AlignmentSpan.Standard(align!!)
    }


    fun getImageSpan(bitmap: Bitmap): ImageSpan {
        return ImageSpan(CoreConfig.applicationContext, bitmap!!)
    }

    fun getImageSpan(drawable: Drawable): ImageSpan {
        return ImageSpan(drawable!!)
    }

    fun getImageSpan(uri: Uri): ImageSpan {
        return ImageSpan(CoreConfig.applicationContext, uri!!)
    }


    fun getImageSpan(resourceId: Int): ImageSpan {
        return ImageSpan(CoreConfig.applicationContext, resourceId)
    }

    fun getURLSpan(url: String): URLSpan {
        return URLSpan(url)
    }


    fun getMaskFilterSpan(radius: Float, style: Blur): MaskFilterSpan {
        return MaskFilterSpan(BlurMaskFilter(radius, style))
    }

    //endregion

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