/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */
/*
 * Create on 2016-11-18 上午10:20
 * FileName: FilterImageView.java
 * Author: huang qiqiang
 * Contact:
 */
package com.easy.core.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.ImageView

/**
 * @author LinJ
 * @ClassName: ThumbnailView
 * @Description: 点击时显示明暗变化(滤镜效果)的ImageView
 * @date 2015-1-6 下午2:13:46
 */
@SuppressLint("AppCompatCustomView")
class FilterImageView : ImageView {
    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {}

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN ->                 //在按下事件中设置滤镜
                setFilter()
            MotionEvent.ACTION_UP -> {
                //由于捕获了Touch事件，需要手动触发Click事件
                performClick()
                //在CANCEL和UP事件中清除滤镜
                removeFilter()
            }
            MotionEvent.ACTION_CANCEL -> removeFilter()
            else -> {
            }
        }
        return true
    }

    /**
     * 设置滤镜
     */
    private fun setFilter() {
        //先获取设置的src图片
        var drawable = drawable
        //当src图片为Null，获取背景图片
        if (drawable == null) {
            drawable = background
        }
        drawable?.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY)
    }

    /**
     * 清除滤镜
     */
    private fun removeFilter() {
        //先获取设置的src图片
        var drawable = drawable
        //当src图片为Null，获取背景图片
        if (drawable == null) {
            drawable = background
        }
        drawable?.clearColorFilter()
    }
}