package com.hqq.core.utils

import android.app.Activity
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import android.view.View

/**
 * @version V1.0 <描述当前版本功能>
 * @author: huangqiqiang
 * @FileName: com.hqq.core.utils.BaseCommonUtils.java
 * @emain: 593979591@qq.com
 * @date: 2020-04-07 10:54
 * 在此写用途
</描述当前版本功能> */
object BaseCommonUtils {
    /**
     * 黑白化的一种解决方案
     * https://mp.weixin.qq.com/s/8fTWLYaPhi0to47EUmFd7A
     * https://mp.weixin.qq.com/s/EioJ8ogsCxQEFm44mKFiOQ
     *
     * @param activity
     */
    @kotlin.jvm.JvmStatic
    fun blackAndWhite(activity: Activity) {
        val paint = Paint()
        val cm = ColorMatrix()
        cm.setSaturation(0f)
        paint.colorFilter = ColorMatrixColorFilter(cm)
        activity.window.decorView.setLayerType(View.LAYER_TYPE_HARDWARE, paint)
    }
}