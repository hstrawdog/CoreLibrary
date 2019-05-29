package com.hqq.coreapp.ui.activity.dialog;


import android.view.View;
import android.widget.Button;

import com.hqq.core.ui.BaseActivity;
import com.hqq.coreapp.R;
import com.hqq.coreapp.dialog.FullDialog;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.coreapp.ui.activity
 * @FileName :   TestDialogActivity
 * @Date : 2019/5/24 0024
 * @Email :  qiqiang213@gmail.com
 * @Descrive : TODO
 */
public class TestDialogActivity extends BaseActivity {

    @BindView(R.id.button14)
    Button mButton14;
    @BindView(R.id.button15)
    Button mButton15;

    @Override
    public int getViewId() {
        return R.layout.activity_test_dialog;
    }

    @Override
    public void initView() {

    }

    @OnClick({R.id.button14, R.id.button15})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.button14:
                BottomSheetActivity.open(this);
                break;
            case R.id.button15:
                FullDialog.showDialog(getSupportFragmentManager());
                break;
            default:
        }
    }


}
