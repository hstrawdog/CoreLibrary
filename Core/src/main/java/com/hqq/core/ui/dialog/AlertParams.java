package com.hqq.core.ui.dialog;

import android.content.DialogInterface;

import com.hqq.core.ui.BaseViewBuilderHolder;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.ui.dialog
 * @FileName :   AlertParams
 * @Date : 2020/7/30 0030  下午 1:58
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public class AlertParams {
    /**
     * 取消按钮监听
     */
    public DialogInterface.OnClickListener mNegativeButtonListener;
    /**
     * 中间布局
     */
    public BaseViewBuilderHolder mBaseViewBuilderHolder;
    /**
     * 取消按钮上的文字
     */
    public String mNegativeButtonText = "取消";
    /**
     * 确定按钮
     */
    public CharSequence mPositiveButtonText = "确定";
    /**
     * 确定按钮监听
     */
    public DialogInterface.OnClickListener mPositiveButtonListener;
    /**
     * 顶部标题
     */
    public CharSequence mTitle = "提示";
}
