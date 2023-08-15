package com.easy.example.demo;

import android.os.Bundle;

import com.easy.core.ui.base.BaseVmFragment;
import com.easy.example.BR;
import com.easy.example.R;
import com.easy.example.databinding.FragmentTestBinding;
import com.easy.example.ui.jetpack.package1.UserViewModel;

/**
 * @Author : huangqiqiang
 * @Package : com.easy.example.demo
 * @FileName :   TestFtamgent
 * @Date : 2020/7/28 0028  下午 6:01
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public class TestFragment extends BaseVmFragment<UserViewModel, FragmentTestBinding> {
    public static TestFragment newInstance() {
        Bundle args = new Bundle();
        TestFragment fragment = new TestFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_test;
    }

    @Override
    public void initViews() {

    }


    @Override
    public int bindingViewModelId() {
        return BR.vm;
    }
}
