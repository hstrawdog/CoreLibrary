package com.hqq.core.ui.vm;

import androidx.databinding.ViewDataBinding;

import com.hqq.core.ui.binding.BaseBindingFragment;
import com.hqq.core.ui.builder.ICreateRootView;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.ui.vm
 * @FileName :   BaseFragment
 * @Date : 2020/7/28 0028  下午 5:36
 * @Email : qiqiang213@gmail.com
 * @Descrive :  同理Activity
 */
public abstract class BaseVmFragment<T extends ViewDataBinding, K extends BaseViewModel>
        extends BaseBindingFragment implements ICreateRootView.IBaseViewModel {
    protected K mViewModel;

    @Override
    public void initView() {
        mViewModel = ViewModelFactory.createViewModel(this, getClass(), mViewModel);
        ViewModelFactory.initBaseViewModel(mViewModel, this, mLoadingView);
        addViewModel();
        initViews();
    }



}
