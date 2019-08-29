package com.hqq.core.app.dialog;

import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.WindowManager;

import com.hqq.core.app.R;
import com.hqq.core.ui.BaseDialog;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: huangqiqiang
 * 在此写用途
 * @FileName: com.hqq.core.app.dialog.RightDialog.java
 * @emain: 593979591@qq.com
 * @date: 2019-07-03 22:22
 */
public class BottomDialog extends BaseDialog {
    public static void showDialog(FragmentManager fragmentManager) {
        BottomDialog fragment = new BottomDialog();
        fragment.show(fragmentManager);
    }

    @Override
    public int setViewId() {
        return R.layout.dialog_bottom_sheet;
    }

    @Override
    public void initView() {

    }

    @Override
    public int setWeight() {
        return WindowManager.LayoutParams.MATCH_PARENT;
    }

    @Override
    public int setHeight() {
        return  WindowManager.LayoutParams.WRAP_CONTENT;
    }

    @Override
    public int setGravity() {
        return Gravity.BOTTOM;
    }
}