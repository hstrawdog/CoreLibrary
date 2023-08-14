package com.easy.album.customize

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

/**
 * @Author : huangqiqiang
 * @Package : com.rongji.shangwenwan.weight
 * @FileName :   SquareImageView
 * @Date : 2018/7/26 0026  下午 5:41
 * @Descrive : TODO
 * @Email :
 * 宽度为准的 正方形ImageView
 */
class SquareImageView : AppCompatImageView {
    constructor(context: Context?) : super(context!!) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs) {}
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context!!, attrs, defStyleAttr) {}

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }
}