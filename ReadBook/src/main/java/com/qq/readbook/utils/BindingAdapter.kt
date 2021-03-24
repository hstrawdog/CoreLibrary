package com.qq.readbook.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.hqq.core.glide.ImageLoadUtils

/**
 * @Author : huangqiqiang
 * @Package : com.qq.readbook.utils
 * @Date : 上午 9:49
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
object BindingAdapter {
    @JvmStatic
    @BindingAdapter("app:url")
    fun setImageViewSrc(imageView: ImageView, src: String?) {
        if (!src.isNullOrBlank()) {
            ImageLoadUtils.with(src, imageView)
        }

    }
}