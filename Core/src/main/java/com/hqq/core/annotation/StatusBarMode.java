package com.hqq.core.annotation;

/**
 * @Author : huangqiqiang
 * @Package : com.rongji.core.annotation
 * @FileName :   StatusBarMode
 * @Date : 2019/5/15 0015  下午 4:11
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */

import android.support.annotation.IntDef;


@IntDef({StatusBarMode.LIGHT_MODE, StatusBarMode.DARK_MODE})
public @interface StatusBarMode {
    int LIGHT_MODE = 1;
    int DARK_MODE = 2;

}
