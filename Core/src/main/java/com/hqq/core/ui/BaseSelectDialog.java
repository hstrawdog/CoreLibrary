package com.hqq.core.ui;

import android.view.WindowManager;

import com.hqq.core.R;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.ui
 * @FileName :   BaseSelectDialog
 * @Date : 2019/10/31 0031  上午 11:12
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public abstract class BaseSelectDialog<T extends BaseViewHolder> extends BaseDialog {
    protected T mViewHolder;

    @Override
    public int setViewId() {
        return R.layout.dialog_base_select_dialog;
    }


    @Override
    public int setAnimation() {
        return R.style.dialogAnimation_fade_in2fade_out;
    }

    @Override
    public int setWeight() {
        return WindowManager.LayoutParams.MATCH_PARENT;
    }


}
