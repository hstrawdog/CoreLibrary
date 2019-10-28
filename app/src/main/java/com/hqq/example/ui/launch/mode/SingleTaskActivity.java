package com.hqq.example.ui.launch.mode;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.hqq.core.ui.BaseActivity;
import com.hqq.example.R;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui.launch.mode
 * @FileName :   SingleTaskActivity
 * @Date : 2019/10/25 0025  下午 5:16
 * @Email :  qiqiang213@gmail.com
 * @Descrive :
 */
public class SingleTaskActivity extends LaunchActivity {
    public static void open(Activity context) {
        Intent starter = new Intent(context, SingleTaskActivity.class);
        context.startActivityForResult(starter, -1);
    }


}
