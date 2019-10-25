package com.hqq.example.annotation;

/**
 * @Author : huangqiqiang
 * @Package : com.rongji.core.annotation
 * @FileName :   ToolBarMode
 * @Date : 2019/5/15 0015  下午 4:11
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */

import androidx.annotation.IntDef;


@IntDef({ToolBarMode.LIGHT_MODE, ToolBarMode.DARK_MODE})
public @interface ToolBarMode {
    int LIGHT_MODE = 1;
    int DARK_MODE = 2;
}
