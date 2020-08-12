package com.hqq.example.ui.soft.hide.keyboard;

import com.hqq.core.ui.base.BaseActivity;
import com.hqq.core.utils.keyboard.SoftHideKeyboardUtils;
import com.hqq.example.R;


/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core_app.ui.activity.soft_hide_keyboard
 * @FileName :   SoftHideKeyBoardScrollActivity
 * @Date : 2019/6/4 0004  上午 11:43
 * @Email :  qiqiang213@gmail.com
 * @Descrive : TODO
 */
public class SoftHideKeyBoardScrollActivity extends BaseActivity {



    @Override
    public int getMLayoutViewId() {
        return R.layout.activity_soft_hide_key_board_scoller;
    }

    @Override
    public void initView() {
        SoftHideKeyboardUtils.addSoftHideKeyboardScrollView(findViewById(R.id.ll_content), findViewById(R.id.tv_login));
    }


}
