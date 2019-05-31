package com.hqq.coreapp.dialog;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.hqq.core.annotation.ToolBarMode;
import com.hqq.core.ui.BaseDialog;
import com.hqq.core.utils.statusbar.StatusBarManager;
import com.hqq.coreapp.R;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.coreapp.dialog
 * @FileName :   FullDialog
 * @Date : 2019/5/24 0024  上午 9:14
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public class FullDialog extends BaseDialog {

    public static void showDialog(FragmentManager fragmentManager) {
        FullDialog fragment = new FullDialog();
        fragment.show(fragmentManager);
    }

    @Override
    public int setHeight() {
        return WindowManager.LayoutParams.MATCH_PARENT;
    }

    @Override
    public int setWeight() {
        return WindowManager.LayoutParams.MATCH_PARENT;
    }

    @Override
    public int setViewId() {
        return R.layout.dialog_full;
    }

    @Override
    public void initDefConfig() {
        mStatusBarMode = ToolBarMode.LIGHT_MODE;
    }

    @Override
    public void initView() {

    }


}
