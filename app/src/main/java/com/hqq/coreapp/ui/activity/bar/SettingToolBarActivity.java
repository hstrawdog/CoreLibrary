package com.hqq.coreapp.ui.activity.bar;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.hqq.core.ui.BaseActivity;
import com.hqq.coreapp.R;

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
    public int setViewId() {
        return R.layout.activity_setting_tool_bar;
    }

    @Override
    public void initView() {
        mRootViewBuild.getDefToolBar().setToolBarColor(R.color.color_main);
    }
}
