package com.hqq.example.ui.launch.mode;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.hqq.core.ui.BaseActivity;
import com.hqq.example.R;

import butterknife.OnClick;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui.launch.mode
 * @FileName :   SingleTopActivity
 * @Date : 2019/10/25 0025  下午 5:17
 * @Email :  qiqiang213@gmail.com
 * @Descrive :
 */
public class SingleTopActivity extends LaunchActivity {


    public static void open(Activity context) {
        Intent starter = new Intent(context, SingleTopActivity.class);
        context.startActivityForResult(starter, -1);
    }



}
