package com.easy.example.ui.view;

import android.app.Activity;
import android.content.Intent;

import com.easy.core.ui.base.BaseActivity;
import com.easy.example.R;

/**
 * @Author : huangqiqiang
 * @Package : com.easy.example.ui.view
 * @FileName :   EditTextActivity
 * @Date : 2020/1/8 0008  上午 11:33
 * @Email :  qiqiang213@gmail.com
 * @Describe :
 */
public class EditTextActivity extends BaseActivity {
    public static void open(Activity context) {
        Intent starter = new Intent(context, EditTextActivity.class);
        context.startActivityForResult(starter, -1);
    }

    @Override
    public int getLayoutViewId() {
        return R.layout.activity_edit_text;
    }

    @Override
    public void initView() {

    }
}
