package com.easy.example.ui.launch.mode;

import android.app.Activity;
import android.content.Intent;

/**
 * @Author : huangqiqiang
 * @Package : com.easy.example.ui.launch.mode
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
