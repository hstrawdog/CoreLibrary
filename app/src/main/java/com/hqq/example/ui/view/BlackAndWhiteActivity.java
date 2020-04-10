package com.hqq.example.ui.view;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.hqq.core.ui.BaseActivity;
import com.hqq.core.utils.BaseCommonUtils;
import com.hqq.example.R;

/**
 * @version V1.0 <描述当前版本功能>
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui.view
 * @FileName :   BlackAndWhiteActivity
 * @Date : 2020/4/7  上午11:35
 * @Email : qiqiang213@gmail.com
 * @Descrive :  黑白化
 */
public class BlackAndWhiteActivity extends BaseActivity {
    public static void open(Context context) {
        Intent starter = new Intent(context, BlackAndWhiteActivity.class);
        context.startActivity(starter);
    }

    @Override
    public int getViewId() {
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
