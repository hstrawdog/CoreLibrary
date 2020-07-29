package com.hqq.example.ui.view;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.hqq.core.ui.BaseActivity;
import com.hqq.example.R;

/**
  * @Author : huangqiqiang
  * @Package : com.hqq.example.ui.view
  * @FileName :   InflateTestActivity
  * @Date  : 2020/7/22 0022  下午 5:25
  * @Email :  qiqiang213@gmail.com
  * @Descrive :
  */

public class InflateTestActivity extends BaseActivity {

public static void open(Activity context) {
    Intent starter = new Intent(context, InflateTestActivity.class);
    context.startActivityForResult(starter,-1);
}

    @Override
    public int getLayoutViewId() {
        return R.layout.activity_inflate_test;
    }

    @Override
    public void initView() {
        LinearLayout linearLayout = findViewById(R.id.ll_rootView);

        View viewA=LayoutInflater.from(this).inflate(R.layout.item_inflate_test_a,null,false);
        linearLayout.addView(viewA);

        View viewB=LayoutInflater.from(this).inflate(R.layout.item_inflate_test_b,null,true);
        linearLayout.addView(viewB);

        View viewC=LayoutInflater.from(this).inflate(R.layout.item_inflate_test_c,linearLayout,false);
        linearLayout.addView(viewC);

        View viewD=LayoutInflater.from(this).inflate(R.layout.item_inflate_test_d,null);
        linearLayout.addView(viewD);

    }
}