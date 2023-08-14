package com.easy.example.ui.system;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.View;

import com.easy.core.ui.base.BaseActivity;
import com.easy.example.R;

/**
 * @version V1.0 <描述当前版本功能>
 * @Author : huangqiqiang
 * @Package : com.easy.example.ui.system
 * @FileName :   AppIconActivity
 * @Date : 2020/4/9  下午2:04
 * @Email : qiqiang213@gmail.com
 * @Descrive : 替换桌面图标
 */
public class AppIconActivity extends BaseActivity {
    public static void open(Context context) {
        Intent starter = new Intent(context, AppIconActivity.class);
        context.startActivity(starter);
    }

    @Override
    public int getLayoutViewId() {
        return R.layout.activity_app_icon;
    }

    @Override
    public void initView() {

        findViewById(R.id.button30).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PackageManager packageManager = getPackageManager();
                packageManager.setComponentEnabledSetting(new ComponentName(AppIconActivity.this,
                        "com.easy.example.ui.MainActivity"), PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager
                        .DONT_KILL_APP);
                packageManager.setComponentEnabledSetting(new ComponentName(AppIconActivity.this,
                        "com.easy.example.RoundActivity"), PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager
                        .DONT_KILL_APP);

            }
        });

        findViewById(R.id.button31).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PackageManager packageManager = getPackageManager();
                packageManager.setComponentEnabledSetting(new ComponentName(AppIconActivity.this,
                                "com.easy.example.RoundActivity"), PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                        PackageManager.DONT_KILL_APP);
                packageManager.setComponentEnabledSetting(new ComponentName(AppIconActivity.this,
                        "com.easy.example.ui.MainActivity"), PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager
                        .DONT_KILL_APP);

            }
        });

    }
}
