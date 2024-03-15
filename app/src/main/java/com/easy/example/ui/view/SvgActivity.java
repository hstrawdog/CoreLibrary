package com.easy.example.ui.view;

import android.content.Context;
import android.content.Intent;

import com.easy.core.ui.base.BaseActivity;
import com.easy.example.R;
 /**
  * @Author : huangqiqiang
  * @Package : com.easy.example.ui.view
  * @FileName :   SvgActivity
  * @Date  : 2020/7/9 0009  下午 3:36
  * @Email :  qiqiang213@gmail.com
  * @Describe :
  */
public class SvgActivity extends BaseActivity {

    public static void open(Context context) {
        Intent starter = new Intent(context, SvgActivity.class);
        context.startActivity(starter);
    }

    @Override
    public int getLayoutViewId() {
        return R.layout.activity_svg;
    }

    @Override
    public void initView() {

    }
}
