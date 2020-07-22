package com.hqq.example.dialog;

import android.view.Gravity;
import android.view.WindowManager;

import androidx.fragment.app.FragmentManager;

import com.hqq.core.annotation.ToolBarMode;
import com.hqq.core.ui.BaseDialog;
import com.hqq.core.utils.ResourcesUtils;
import com.hqq.example.R;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: huangqiqiang
 * 在此写用途
 * @FileName: com.hqq.core.app.dialog.RightDialog.java
 * @emain: 593979591@qq.com
 * @date: 2019-07-03 22:22
 */
public class RightDialog extends BaseDialog {
    public static void showDialog(FragmentManager fragmentManager) {
        RightDialog fragment = new RightDialog();
        fragment.show(fragmentManager);
    }

    @Override
    public int getLayoutViewId() {
        return R.layout.dialog_right;
    }

    @Override
    public void initDefConfig() {
        super.initDefConfig();
        mStatusBarMode = ToolBarMode.LIGHT_MODE;

    }

    @Override
    public void initView() {
//        StatusBarManager.setStatusBarModel(getDialog().getWindow(), true);

    }

    @Override
    public int getGravity() {
        return Gravity.RIGHT;
    }

    @Override
    public int getAnimation() {
        return R.anim.dialog_right_left;
    }
}
