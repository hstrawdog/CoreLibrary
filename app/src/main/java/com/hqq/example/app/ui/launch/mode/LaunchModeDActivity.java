package com.hqq.example.app.ui.launch.mode;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.hqq.example.app.R;
import com.hqq.example.ui.BaseActivity;

import butterknife.OnClick;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.app.ui.launch.mode
 * @FileName :   LaunchModeDActivity
 * @Date : 2019/10/25 0025  上午 9:45
 * @Email :  qiqiang213@gmail.com
 * @Descrive :
 */
public class LaunchModeDActivity extends BaseActivity {

    public static void open(Activity context) {
        Intent starter = new Intent(context, LaunchModeDActivity.class);
        context.startActivityForResult(starter, -1);
    }

    @Override
    public int setViewId() {
        return R.layout.activity_launch_mode_a;
    }

    @Override
    public void initView() {

    }

    @OnClick({R.id.button20, R.id.button21, R.id.button22, R.id.button23, R.id.button24})
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
            default:

        }
    }
}
