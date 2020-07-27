package com.hqq.example.ui.launch.mode;

import android.view.View;

import com.hqq.core.ui.BaseCoreActivity;
import com.hqq.example.R;


/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui.launch.mode
 * @FileName :   LaunchActivity
 * @Date : 2019/10/25 0025  下午 5:20
 * @Email : qiqiang213@gmail.com
 * @Descrive : 启动模式
 */
public class LaunchActivity extends BaseCoreActivity {

    @Override
    public int getLayoutViewId() {
        return R.layout.activity_launch_mode_a;
    }

    @Override
    public void initView() {
        findViewById(R.id.button20).setOnClickListener(this::onViewClicked);
        findViewById(R.id.button21).setOnClickListener(this::onViewClicked);
        findViewById(R.id.button22).setOnClickListener(this::onViewClicked);
        findViewById(R.id.button23).setOnClickListener(this::onViewClicked);
        findViewById(R.id.button24).setOnClickListener(this::onViewClicked);
        findViewById(R.id.button26).setOnClickListener(this::onViewClicked);
        findViewById(R.id.button27).setOnClickListener(this::onViewClicked);
    }

    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button20:
                LaunchModeAActivity.open(this);
                break;
            case R.id.button21:
                LaunchModeBActivity.open(this);
                break;
            case R.id.button22:
                LaunchModeCActivity.open(this);
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
