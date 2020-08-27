package com.hqq.example.demo;

import android.os.Bundle;

import com.hqq.core.ui.vm.BaseVmFragment;
import com.hqq.example.BR;
import com.hqq.example.R;
import com.hqq.example.databinding.FragmentTestBinding;
import com.hqq.example.ui.jetpack.package1.UserViewModel;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.demo
 * @FileName :   TestFtamgent
 * @Date : 2020/7/28 0028  下午 6:01
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public class TestFragment extends BaseVmFragment<FragmentTestBinding, UserViewModel> {
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
    public int getBindingViewModelId() {
        return R.layout.fragment_test;
    }
}
