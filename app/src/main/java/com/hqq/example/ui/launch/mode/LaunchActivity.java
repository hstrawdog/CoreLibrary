package com.hqq.example.ui.launch.mode;

import android.view.View;

import com.hqq.core.ui.BaseActivity;
import com.hqq.example.R;

import butterknife.OnClick;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui.launch.mode
 * @FileName :   LaunchActivity
 * @Date : 2019/10/25 0025  下午 5:20
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public class LaunchActivity extends BaseActivity {
    @Override
    public int setViewId() {
        return R.layout.activity_launch_mode_a;
    }

    @Override
    public void initView() {

    }


    @OnClick({R.id.button20, R.id.button21, R.id.button22, R.id.button23, R.id.button24, R.id.button26, R.id.button27})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button20:
                LaunchModeAActivity.open(this);
                break;
            case R.id.button21:
                LaunchModeBActivity.open(this);
                break;
            case R.id.button22:
                LaunchModeBActivity.open(this);
                break;
            case R.id.button23:
                LaunchModeDActivity.open(this);
                break;
            case R.id.button24:
                SingleInstanceActivity.open(this);
                break;
            case R.id.button26:
                SingleTaskActivity.open(this);
                break;
            case R.id.button27:
                SingleTopActivity.open(this);
                break;
            default:
        }
    }
}
