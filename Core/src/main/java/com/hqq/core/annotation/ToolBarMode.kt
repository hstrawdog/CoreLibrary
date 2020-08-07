package com.hqq.core.annotation

import androidx.annotation.IntDef
import com.hqq.core.annotation.ToolBarMode

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
        @kotlin.jvm.JvmField
        var LIGHT_MODE = 1
        var DARK_MODE = 2
    }
}