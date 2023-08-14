package com.easy.example.ui.launch.mode;

import android.app.Activity;
import android.content.Intent;

/**
 * @Author : huangqiqiang
 * @Package : com.easy.example.ui.launch.mode
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
