package com.hqq.core.annotation

import androidx.annotation.IntDef

/**
 * @Author : huangqiqiang
 * @Package : com.rongji.core.annotation
 * @FileName :   ToolBarMode
 * @Date : 2019/5/15 0015  下午 4:11
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
@IntDef(ToolBarMode.LIGHT_MODE, ToolBarMode.DARK_MODE)
annotation class ToolBarMode {
    companion object {
        /**
         *  亮色模式
         */
        const val LIGHT_MODE = 1

        /**
         * 暗色模式
         */
        const val DARK_MODE = 2
    }
}