package com.hqq.coreapp.ui.activity;


import android.widget.Button;

import com.hqq.core.ui.BaseActivity;
import com.hqq.coreapp.R;

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
    public int getViewId() {
        return R.layout.activity_throw;
    }

    @Override
    public void initView() {

    }


    @OnClick(R.id.button11)
    public void onViewClicked() {
        try {
            throw new Exception("一个异常 ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
