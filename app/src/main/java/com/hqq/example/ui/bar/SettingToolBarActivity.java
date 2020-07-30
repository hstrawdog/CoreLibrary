package com.hqq.example.ui.bar;

import android.app.Activity;
import android.content.Intent;

import com.hqq.core.ui.base.BaseActivity;
import com.hqq.example.R;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.coreapp.ui.activity.bar
 * @FileName :   SettingToolBarActivity
 * @Date : 2019/5/22 0022
 * @Email :  qiqiang213@gmail.com
 * @Descrive :
 */
public class SettingToolBarActivity extends BaseActivity {
    public static void open(Activity context) {
        Intent starter = new Intent(context, SettingToolBarActivity.class);
        context.startActivityForResult(starter, -1);
    }

    @Override
    public void initDefConfig() {
        super.initDefConfig();

    }

    @Override
    public int getLayoutViewId() {
        return R.layout.activity_setting_tool_bar;
    }

    @Override
    public void initView() {
        mRootViewBuild.getDefToolBar().setToolBarColor(R.color.color_main);
    }
}
