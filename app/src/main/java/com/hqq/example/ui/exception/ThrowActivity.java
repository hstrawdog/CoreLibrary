package com.hqq.example.ui.exception;


import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hqq.core.ui.BaseActivity;
import com.hqq.core.utils.log.LogUtils;
import com.hqq.example.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.coreapp.ui.activity
 * @FileName :   ThrowActivity
 * @Date : 2019/5/22 0022
 * @Email :  qiqiang213@gmail.com
 * @Descrive :
 */
public class ThrowActivity extends BaseActivity {

    @BindView(R.id.button11)
    Button mButton11;

    @Override
    public int getLayoutViewId() {
        return R.layout.activity_throw;
    }

    @Override
    public void initView() {

    }


    @OnClick({R.id.button11, R.id.button12})
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
