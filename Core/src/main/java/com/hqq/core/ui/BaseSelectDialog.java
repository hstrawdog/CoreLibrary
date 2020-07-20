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
public abstract class BaseSelectDialog<T extends BaseViewBuilderHolder> extends BaseDialog {
    protected T mViewHolder;

    @Override
    public int getViewId() {
        return R.layout.dialog_base_select_dialog;
    }

    @Override
    public int getAnimation() {
        return R.style.dialogAnimation_fade_in2fade_out;
    }

    @Override
    public int getWeight() {
        return WindowManager.LayoutParams.MATCH_PARENT;
    }

    @Override
    public void initView() {
        mViewHolder = getViewHolder();
        mViewHolder.addToParent();
        initData();
    }

    /**
     * 将所有的操作 过度到viewHolder 中去
     *
     * @return
     */
    protected abstract T getViewHolder();

    /**
     * 初始化数据 传递数据到viewHolder 中
     */
    protected abstract void initData();

}
