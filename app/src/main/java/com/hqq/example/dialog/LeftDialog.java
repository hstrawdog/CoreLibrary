package com.hqq.example.dialog;

import androidx.fragment.app.FragmentManager;
import android.view.Gravity;

import com.hqq.example.R;
import com.hqq.core.ui.dialog.BaseDialog;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: huangqiqiang
 * 在此写用途
 * @FileName: com.hqq.core.app.dialog.RightDialog.java
 * @emain: 593979591@qq.com
 * @date: 2019-07-03 22:22
 */
public class LeftDialog extends BaseDialog {
    public static void showDialog(FragmentManager fragmentManager) {
        LeftDialog fragment = new LeftDialog();
        fragment.show(fragmentManager);
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_right;
    }

    @Override
    public void initView() {

    }
    @Override
    public int getGravity() {
        return Gravity.LEFT;
    }

    @Override
    public int getAnimation() {
        return R.anim.dialog_left_right;
    }
}
