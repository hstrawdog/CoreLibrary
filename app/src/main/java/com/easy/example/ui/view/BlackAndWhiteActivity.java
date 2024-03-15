package com.easy.example.ui.view;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.easy.core.ui.base.BaseActivity;
import com.easy.core.utils.BaseCommonUtils;
import com.easy.example.R;

/**
 * @version V1.0 <描述当前版本功能>
 * @Author : huangqiqiang
 * @Package : com.easy.example.ui.view
 * @FileName :   BlackAndWhiteActivity
 * @Date : 2020/4/7  上午11:35
 * @Email : qiqiang213@gmail.com
 * @Describe :  黑白化
 */
public class BlackAndWhiteActivity extends BaseActivity {
    public static void open(Context context) {
        Intent starter = new Intent(context, BlackAndWhiteActivity.class);
        context.startActivity(starter);
    }

    @Override
    public int getLayoutViewId() {
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
