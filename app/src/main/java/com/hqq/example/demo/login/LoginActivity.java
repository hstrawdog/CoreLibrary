package com.hqq.example.demo.login;

import android.app.Activity;
import android.content.Intent;

import com.hqq.core.ui.vm.BaseActivity;
import com.hqq.example.R;
import com.hqq.example.databinding.ActivityLoginBinding;
 /**
  * @Author : huangqiqiang
  * @Package : com.hqq.example.demo
  * @FileName :   LoginActivity
  * @Date  : 2020/7/28 0028  下午 4:06
  * @Email :  qiqiang213@gmail.com
  * @Descrive :
  */
public class LoginActivity extends BaseActivity<ActivityLoginBinding, LoginModel> {

public static void open(Activity context) {
    Intent starter = new Intent(context, LoginActivity.class);
    context.startActivityForResult(starter,-1);
}


    @Override
    protected void addViewModel() {
        mBinding.setVm(mViewModel);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initViews() {

    }
}