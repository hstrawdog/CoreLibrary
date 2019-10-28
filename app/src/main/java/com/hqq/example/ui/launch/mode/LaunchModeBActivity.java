package com.hqq.example.ui.launch.mode;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.hqq.example.R;
import com.hqq.core.ui.BaseActivity;

import butterknife.OnClick;
 /**
  * @Author : huangqiqiang
  * @Package : com.hqq.example.ui.launch.mode
  * @FileName :   LaunchModeBActivity
  * @Date  : 2019/10/28 0028  上午 9:43
  * @Email :  qiqiang213@gmail.com
  * @Descrive : 
  */
public class LaunchModeBActivity extends LaunchActivity {

    public static void open(Activity context) {
        Intent starter = new Intent(context, LaunchModeBActivity.class);
        context.startActivityForResult(starter, -1);
    }

}
