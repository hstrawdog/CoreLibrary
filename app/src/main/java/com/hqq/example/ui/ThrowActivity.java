package com.hqq.example.ui;


import android.view.View;
import android.widget.Button;

import com.hqq.core.ui.BaseActivity;
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
    public int setViewId() {
        return R.layout.activity_throw;
    }

    @Override
    public void initView() {

    }


    @OnClick({R.id.button11, R.id.button12})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button11:
                try {
                    throw new Exception("一个异常 ");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.button12:
                try {
                    throw new RuntimeException("一个运行异常 ");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            default:
        }


    }
}
