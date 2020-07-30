package com.hqq.example.ui.exception;


import android.view.View;

import com.hqq.core.ui.base.BaseActivity;
import com.hqq.core.utils.log.LogUtils;
import com.hqq.example.R;


/**
 * @Author : huangqiqiang
 * @Package : com.hqq.coreapp.ui.activity
 * @FileName :   ThrowActivity
 * @Date : 2019/5/22 0022
 * @Email :  qiqiang213@gmail.com
 * @Descrive :异常
 */
public class ThrowActivity extends BaseActivity {


    @Override
    public int getLayoutViewId() {
        return R.layout.activity_throw;
    }

    @Override
    public void initView() {
    findViewById(R.id.button11).setOnClickListener(this::onViewClicked);
    findViewById(R.id.button12).setOnClickListener(this::onViewClicked);
    }


    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button11:
                LogUtils.e(new Exception("一个异常 "));

                break;
            case R.id.button12:
                LogUtils.e(new RuntimeException("一个运行异常 "));
                break;

            default:
        }


    }
}
