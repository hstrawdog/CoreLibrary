package com.hqq.core.example.dialog;

import androidx.fragment.app.FragmentManager;
import android.view.Gravity;
import android.view.WindowManager;

import com.hqq.core.example.R;
import com.hqq.core.ui.BaseDialog;
import com.hqq.core.utils.ResourcesUtils;

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
    public int setViewId() {
        return R.layout.dialog_select;
    }

    @Override
    public void initView() {

    }

    @Override
    public int setWeight() {
        return (int) ResourcesUtils.getDimen(getActivity(), R.dimen.x560);
    }


    @Override
    public int setHeight() {
        return WindowManager.LayoutParams.WRAP_CONTENT;
    }

    @Override
    public int setGravity() {
        return Gravity.CENTER;
    }

    @Override
    public int setAnimation() {
        return R.style.dialogAnimation_fade_in2fade_out;
    }


}
