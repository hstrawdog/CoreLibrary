package com.hqq.example.ui.jetpack.package1;

import android.app.Activity;
import android.content.Intent;

import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;

import com.hqq.core.ui.vm.BaseActivity;
import com.hqq.example.BR;
import com.hqq.example.R;

import java.lang.reflect.ParameterizedType;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui.jetpack.package1
 * @FileName :   Package1Activity
 * @Date : 2020/7/21 0021  下午 3:53
 * @Email :  qiqiang213@gmail.com
 * @Descrive : ViewBinding + ViewModel + liveData
 */
public class Package1Activity extends BaseActivity {
    public static void open(Activity context) {
        Intent starter = new Intent(context, Package1Activity.class);
        context.startActivityForResult(starter, -1);
    }

    ViewDataBinding mBinding;

    @Override
    protected void addViewModel() {
        mBinding.setVariable(BR.vm, new ViewModelProvider(this).get(UserViewModel.class));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_package1;
    }

    @Override
    protected void initViews() {

    }
}