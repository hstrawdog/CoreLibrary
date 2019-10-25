package com.hqq.example.app.dialog;

import androidx.fragment.app.FragmentManager;
import android.view.WindowManager;

import com.hqq.example.annotation.ToolBarMode;
import com.hqq.example.ui.BaseDialog;
import com.hqq.example.app.R;

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
        mStatusBarMode = ToolBarMode.DARK_MODE;
    }

    @Override
    public int setAnimation() {
        return R.anim.fade_in;
    }

    @Override
    public void initView() {

    }


}
