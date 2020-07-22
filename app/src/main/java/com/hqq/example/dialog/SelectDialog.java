package com.hqq.example.dialog;

import android.view.Gravity;

import androidx.fragment.app.FragmentManager;

import com.hqq.core.ui.BaseDialog;
import com.hqq.example.R;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: huangqiqiang
 * 在此写用途
 * @FileName: com.hqq.core.app.dialog.RightDialog.java
 * @emain: 593979591@qq.com
 * @date: 2019-07-03 22:22
 */
public class SelectDialog extends BaseDialog {
    public static void showDialog(FragmentManager fragmentManager) {
        SelectDialog fragment = new SelectDialog();
        fragment.show(fragmentManager);
    }

    @Override
    public int getLayoutViewId() {
        return R.layout.dialog_select;
    }

    @Override
    public void initView() {

    }

    @Override
    public int getGravity() {
        return Gravity.CENTER;
    }

    @Override
    public int getAnimation() {
        return R.style.dialogAnimation_fade_in2fade_out;
    }


}
