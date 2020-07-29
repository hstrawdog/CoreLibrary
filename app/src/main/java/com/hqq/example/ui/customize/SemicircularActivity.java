package com.hqq.example.ui.customize;

import android.app.Activity;
import android.content.Intent;

import com.hqq.core.ui.BaseActivity;
import com.hqq.example.R;

/**
  * @Author : huangqiqiang
  * @Package : com.hqq.example.ui.customize
  * @FileName :   SemicircularActivity
  * @Date  : 2020/7/6 0006  上午 11:03
  * @Email :  qiqiang213@gmail.com
  * @Descrive :
  */
public class SemicircularActivity extends BaseActivity {

    public static void open(Activity context) {
        Intent starter = new Intent(context, SemicircularActivity.class);
        context.startActivityForResult(starter,-1);
    }


    @Override
    public int getLayoutViewId() {
        return R.layout.activity_semicircular;
    }

    @Override
    public void initView() {

    }
}