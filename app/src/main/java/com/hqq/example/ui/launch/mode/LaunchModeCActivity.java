package com.hqq.example.ui.launch.mode;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.hqq.example.R;
import com.hqq.core.ui.BaseActivity;

import butterknife.OnClick;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.app.ui.launch.mode
 * @FileName :   LaunchModeCActivity
 * @Date : 2019/10/25 0025  上午 9:44
 * @Email :  qiqiang213@gmail.com
 * @Descrive :
 */
public class LaunchModeCActivity extends LaunchActivity {
    public static void open(Activity context) {
        Intent starter = new Intent(context, LaunchModeCActivity.class);
        context.startActivityForResult(starter, -1);
    }



}
