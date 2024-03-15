package com.easy.example.ui.launch.mode;

import android.app.Activity;
import android.content.Intent;

/**
  * @Author : huangqiqiang
  * @Package : com.easy.example.ui.launch.mode
  * @FileName :   LaunchModeBActivity
  * @Date  : 2019/10/28 0028  上午 9:43
  * @Email :  qiqiang213@gmail.com
  * @Describe : 
  */
public class LaunchModeBActivity extends LaunchActivity {

    public static void open(Activity context) {
        Intent starter = new Intent(context, LaunchModeBActivity.class);
        context.startActivityForResult(starter, -1);
    }

}
