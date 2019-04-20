package com.hqq.core.annotation;

import android.support.annotation.IntDef;

import com.hqq.core.ui.RootViewBuild;


/**
 * @Author : huangqiqiang
 * @Package : com.shangwenwan.sww.annotation
 * @FileName :   LayoutModel
 * @Date : 2019/1/17 0017  下午 1:43
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
@IntDef({RootViewBuild.LAYOUT_MODE_LINEAR_LAYOUT, RootViewBuild.LAYOUT_MODE_FRAME_LAYOUT})
public @interface LayoutModel {
    int value() default RootViewBuild.LAYOUT_MODE_LINEAR_LAYOUT;
}
