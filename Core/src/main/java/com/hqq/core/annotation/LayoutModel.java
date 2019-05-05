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
@IntDef({LayoutModel.LAYOUT_MODE_LINEAR_LAYOUT, LayoutModel.LAYOUT_MODE_FRAME_LAYOUT})
public @interface LayoutModel {
    /**
     * 布局
     * LinearLayout
     */
    int LAYOUT_MODE_LINEAR_LAYOUT = 1;
    /**
     * 布局
     * frameLayout
     */
    int LAYOUT_MODE_FRAME_LAYOUT = 2;
}
