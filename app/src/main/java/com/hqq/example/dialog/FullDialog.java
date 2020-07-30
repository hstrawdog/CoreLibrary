package com.hqq.example.dialog;

import androidx.fragment.app.FragmentManager;

import com.hqq.core.ui.dialog.BaseDialog;
import com.hqq.example.R;

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
    public int getViewId() {
        return R.layout.dialog_full;
    }

    @Override
    public void initDefConfig() {

    }

    @Override
    public int getAnimation() {
        return R.anim.fade_in;
    }

    @Override
    public void initView() {

    }


}
