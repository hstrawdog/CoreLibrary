package com.easy.core.utils

import android.app.Activity
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import android.view.View

/**
 * @version V1.0 <描述当前版本功能>
 * @author: huangqiqiang
 * @FileName: com.easy.core.utils.BaseCommonUtils.java
 * @emain: 593979591@qq.com
 * @date: 2020-04-07 10:54
 *
 */
object BaseCommonUtils {

    enum class MemoryUnit {
        BYTE, KB, MB, GB
    }

    enum class TimeUnit {
        MSEC, SEC, MIN, HOUR, DAY
    }

    /**
     * Byte与Byte的倍数
     */
    const val BYTE = 1

    /**
     * KB与Byte的倍数
     */
    const val KB = 1024

    /**
     * MB与Byte的倍数
     */
    const val MB = 1048576

    /**
     * GB与Byte的倍数
     */
    const val GB = 1073741824

    /**
     * A String for a space character.
     *
     * @since 3.2
     */
    const val SPACE = " "

    /**
     * 黑白化的一种解决方案
     * https://mp.weixin.easy.com/s/8fTWLYaPhi0to47EUmFd7A
     * https://mp.weixin.easy.com/s/EioJ8ogsCxQEFm44mKFiOQ
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