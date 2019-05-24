package com.hqq.coreapp.dialog;

import android.support.v4.app.FragmentManager;
import android.view.WindowManager;

import com.hqq.core.ui.BaseDialog;
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

 public static void showDialog(FragmentManager fragmentManager){
     FullDialog fragment = new FullDialog();
     fragment.show(fragmentManager);
 }
    @Override
    protected int setHeight() {
        return WindowManager.LayoutParams.MATCH_PARENT;

    }

    @Override
    protected int setWeight() {
        return WindowManager.LayoutParams.MATCH_PARENT;
    }

    @Override
    public int setView() {
        return R.layout.dialog_full;
    }

    @Override
    protected void initView() {

    }

}
