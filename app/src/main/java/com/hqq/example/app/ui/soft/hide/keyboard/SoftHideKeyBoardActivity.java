package com.hqq.example.app.ui.soft.hide.keyboard;

import com.hqq.example.ui.BaseActivity;
import com.hqq.example.utils.ToastUtils;
import com.hqq.example.utils.keyboard.SoftHideKeyboardListener;
import com.hqq.example.utils.keyboard.SoftHideKeyboardUtils;
import com.hqq.example.app.R;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.coreapp.ui.activity
 * @FileName :   SoftHideKeyBoardActivity
 * @Date : 2019/6/4 0004
 * @Email :  qiqiang213@gmail.com
 * @Descrive : TODO
 */
public class SoftHideKeyBoardActivity extends BaseActivity {

    @Override
    public int setViewId() {
        return R.layout.activity_soft_hide_key_board;
    }

    @Override
    public void initView() {
        SoftHideKeyboardUtils.softHideKeyboardRedraw(this);
        SoftHideKeyboardUtils.addSoftHideKeyboardListener(this, new SoftHideKeyboardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                ToastUtils.showToast("键盘显示");
            }

            @Override
            public void keyBoardHide(int height) {
                ToastUtils.showToast("键盘隐藏");

            }
        });
    }


}
