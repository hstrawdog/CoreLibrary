package com.easy.example.ui.launch.mode;

import android.app.Activity;
import android.content.Intent;

/**
 * @Author : huangqiqiang
 * @Package : com.easy.core.app.ui.launch.mode
 * @FileName :   LaunchModeDActivity
 * @Date : 2019/10/25 0025  上午 9:45
 * @Email :  qiqiang213@gmail.com
 * @Descrive :
 */
public class LaunchModeDActivity extends LaunchActivity {

    public static void open(Activity context) {
        Intent starter = new Intent(context, LaunchModeDActivity.class);
        context.startActivityForResult(starter, -1);
    }


}
