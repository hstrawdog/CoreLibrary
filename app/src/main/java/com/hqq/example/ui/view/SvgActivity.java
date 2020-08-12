package com.hqq.example.ui.view;

import android.content.Context;
import android.content.Intent;

import com.hqq.core.ui.base.BaseActivity;
import com.hqq.example.R;
 /**
  * @Author : huangqiqiang
  * @Package : com.hqq.example.ui.view
  * @FileName :   SvgActivity
  * @Date  : 2020/7/9 0009  下午 3:36
  * @Email :  qiqiang213@gmail.com
  * @Descrive :
  */
public class SvgActivity extends BaseActivity {

    public static void open(Context context) {
        Intent starter = new Intent(context, SvgActivity.class);
        context.startActivity(starter);
    }

    @Override
    public int getMLayoutViewId() {
        return R.layout.activity_svg;
    }

    @Override
    public void initView() {

    }
}
