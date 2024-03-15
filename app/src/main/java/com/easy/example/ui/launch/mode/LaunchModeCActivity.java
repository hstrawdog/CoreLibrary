package com.easy.example.ui.launch.mode;

import android.app.Activity;
import android.content.Intent;

/**
 * @Author : huangqiqiang
 * @Package : com.easy.core.app.ui.launch.mode
 * @FileName :   LaunchModeCActivity
 * @Date : 2019/10/25 0025  上午 9:44
 * @Email :  qiqiang213@gmail.com
 * @Describe :
 */
public class LaunchModeCActivity extends LaunchActivity {
    public static void open(Activity context) {
        Intent starter = new Intent(context, LaunchModeCActivity.class);
        context.startActivityForResult(starter, -1);
    }



}
