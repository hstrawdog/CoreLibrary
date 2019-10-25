package com.hqq.core.app.ui.launch.mode;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.hqq.core.app.R;
import com.hqq.core.ui.BaseActivity;

public class LaunchModeBActivity extends BaseActivity {

    public static void open(Activity context) {
        Intent starter = new Intent(context, LaunchModeBActivity.class);
        context.startActivityForResult(starter, -1);
    }

    @Override
    public int setViewId() {
        return R.layout.activity_launch_mode_a;
    }

    @Override
    public void initView() {

    }
}
