package com.hqq.example.ui.view;

import android.content.Context;
import android.content.Intent;

import com.hqq.core.ui.BaseActivity;
import com.hqq.example.R;

public class TextViewActivity extends BaseActivity {
    public static void open(Context context) {
        Intent starter = new Intent(context, TextViewActivity.class);
        context.startActivity(starter);
    }
    @Override
    public int getViewId() {
        return R.layout.activity_text_view;
    }

    @Override
    public void initView() {

    }
}
