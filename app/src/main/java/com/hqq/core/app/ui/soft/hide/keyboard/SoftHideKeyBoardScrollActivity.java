package com.hqq.core.app.ui.soft.hide.keyboard;

import android.support.constraint.ConstraintLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hqq.core.ui.BaseActivity;
import com.hqq.core.utils.keyboard.SoftHideKeyboardUtils;
import com.hqq.core.app.R;

import butterknife.BindView;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core_app.ui.activity.soft_hide_keyboard
 * @FileName :   SoftHideKeyBoardScrollActivity
 * @Date : 2019/6/4 0004  上午 11:43
 * @Email :  qiqiang213@gmail.com
 * @Descrive : TODO
 */
public class SoftHideKeyBoardScrollActivity extends BaseActivity {

    @BindView(R.id.tv_login)
    TextView mTvLogin;
    @BindView(R.id.cl_root)
    ConstraintLayout mClRoot;
    @BindView(R.id.ll_content)
    LinearLayout mLlContent;
    @BindView(R.id.ll_login)
    LinearLayout mLlLogin;

    @Override
    public int setViewId() {
        return R.layout.activity_soft_hide_key_board_scoller;
    }

    @Override
    public void initView() {
        SoftHideKeyboardUtils.addSoftHideKeyboardScrollView(mLlContent, mTvLogin);
    }


}
