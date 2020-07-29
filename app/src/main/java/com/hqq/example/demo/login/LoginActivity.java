package com.hqq.example.demo.login;

import android.app.Activity;
import android.content.Intent;

import com.hqq.core.ui.vm.BaseVmActivity;
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
public class LoginActivity extends BaseVmActivity<ActivityLoginBinding, LoginModel> {

public static void open(Activity context) {
    Intent starter = new Intent(context, LoginActivity.class);
    context.startActivityForResult(starter,-1);
}


    @Override
    public void addViewModel() {
        mBinding.setVm(mViewModel);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initViews() {

    }
}