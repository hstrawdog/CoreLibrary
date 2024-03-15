package com.easy.example.ui.launch.mode;

import android.app.Activity;
import android.content.Intent;

/**
 * @Author : huangqiqiang
 * @Package : com.easy.core.app.ui.launch.mode
 * @FileName :   LaunchModeAActivity
 * @Date : 2019/10/25 0025  上午 9:43
 * @Email :  qiqiang213@gmail.com
 * @Describe :
 */
public class LaunchModeAActivity extends LaunchActivity {

    public static void open(Activity context) {
        Intent starter = new Intent(context, LaunchModeAActivity.class);
        context.startActivityForResult(starter, -1);
    }


}
