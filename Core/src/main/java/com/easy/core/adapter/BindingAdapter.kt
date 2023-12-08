package com.easy.core.adapter

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.easy.core.utils.ResourcesUtils
import com.easy.core.glide.ImageLoadUtils

/**
 * @Author : huangqiqiang
 * @Package : com.easy.readbook.utils
 * @Date : 上午 9:49
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
object BindingAdapter {

    @JvmStatic
    @BindingAdapter("url")
    fun setImageViewSrc(imageView: ImageView, src: String?) {
        if (!src.isNullOrBlank()) {
            ImageLoadUtils.with(src, imageView)
        }
    }


    @JvmStatic
    @BindingAdapter(value = [ "strokeColor", "strokeWidth", "cornerRadius" ], requireAll=false)
    fun setFilletBackground(view: View, colorId: Int, strokeWidthId: Int, cornerRadiusId: Int) {
        val shap = GradientDrawable()
//        shap.setColor(ResourcesUtils.getColor(id))\

        var color = Color.RED
        if (colorId != 0 && colorId > 0) {
            color = ResourcesUtils.getColor(colorId)
        }
        var strokeWidth = 10f
        if (strokeWidthId != 0 && strokeWidthId >10) {
            strokeWidth = ResourcesUtils.getDimen(strokeWidthId)
        }


        shap.setStroke(strokeWidth.toInt(), color)
        view.background = shap
    }

}