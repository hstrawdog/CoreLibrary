package com.hqq.example.ui.skin;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.hqq.example.R;

/**
 * @version V1.0 <描述当前版本功能>
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui.skin
 * @FileName :   SkinBActivity
 * @Date : 2020/4/10  上午10:02
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public class SkinBActivity extends BaseSkinActivity {

    public static void open(Context context) {
        Intent starter = new Intent(context, SkinBActivity.class);
        context.startActivity(starter);
    }

    @Override
    public void initView() {
        super.initView();
        findViewById(R.id.button34).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SkinCActivity.open(mActivity);
            }
        });
    }
}
