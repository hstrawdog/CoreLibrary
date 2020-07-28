package com.hqq.example.demo;

import com.hqq.core.ui.vm.BaseFragment;
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
public class TestFragment extends BaseFragment<FragmentTestBinding, UserViewModel> {
    @Override
    public void addViewModel() {
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_test;
    }

    @Override
    public void initViews() {

    }
}
