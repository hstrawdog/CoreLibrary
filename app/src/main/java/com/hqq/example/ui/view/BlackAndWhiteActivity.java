package com.hqq.example.ui.view;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.hqq.core.ui.BaseActivity;
import com.hqq.core.utils.BaseCommonUtils;
import com.hqq.example.R;

public class BlackAndWhiteActivity extends BaseActivity {
    public static void open(Context context) {
        Intent starter = new Intent(context, BlackAndWhiteActivity.class);
        context.startActivity(starter);
    }

    @Override
    public int setViewId() {
        return R.layout.activity_black_and_white;
    }

    @Override
    public void initView() {


        findViewById(R.id.button29).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseCommonUtils.blackAndWhite(BlackAndWhiteActivity.this);
            }
        });
    }
}
