package com.easy.core.annotation

import androidx.annotation.IntDef


/**
 * @Author : huangqiqiang
 * @Package : com...annotation
 * @FileName :   LayoutModel
 * @Date : 2019/1/17 0017  下午 1:43
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
@IntDef(LayoutModel.LAYOUT_MODE_LINEAR_LAYOUT, LayoutModel.LAYOUT_MODE_FRAME_LAYOUT)
annotation class LayoutModel {
    companion object {
        /**
         * 布局
         * LinearLayout
         */
        const val LAYOUT_MODE_LINEAR_LAYOUT = 1

        /**
         * 布局
         * frameLayout
         */
        const val LAYOUT_MODE_FRAME_LAYOUT = 2
    }
}

