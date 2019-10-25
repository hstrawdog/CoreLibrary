package com.hqq.example.ui.customize;

import android.app.Activity;
import android.content.Intent;

import com.hqq.core.ui.BaseActivity;
import com.hqq.example.R;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.coreapp.ui.activity.animation
 * @FileName :   BezierActivity
 * @Date : 2019/5/24 0024
 * @Email :  qiqiang213@gmail.com
 * @Descrive : TODO
 */
public class BezierActivity extends BaseActivity {

    public static void open(Activity context) {
        Intent starter = new Intent(context, BezierActivity.class);
        context.startActivityForResult(starter, -1);
    }

    @Override
    public int setViewId() {
        return R.layout.activity_bezier;
    }

    @Override
    public void initView() {

    }


}
