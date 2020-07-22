package com.hqq.core.ui.vm;

import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.ui.vm
 * @FileName :   BaseActivity
 * @Date : 2020/7/22 0022  下午 3:37
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public abstract class BaseActivity extends com.hqq.core.ui.BaseActivity {
    ViewDataBinding mBinding;

    @Override
    public int getLayoutViewId() {
        return 0;
    }

    @Override
    public View getLayoutView(ViewGroup parent) {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), getLayoutId(), parent, false);
        mBinding.setLifecycleOwner(this);
        return mBinding.getRoot();
    }


    @Override
    public void initView() {
        addViewModel();
        initViews();
    }

    protected abstract void addViewModel();
    protected abstract int getLayoutId();
    protected abstract void initViews();

}
