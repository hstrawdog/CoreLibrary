package com.hqq.core.ui.vm;

import androidx.databinding.ViewDataBinding;

import com.hqq.core.ui.binding.BaseBindingActivity;
import com.hqq.core.ui.builder.ICreateRootView;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.ui.vm
 * @FileName :   BaseActivity
 * @Date : 2020/7/22 0022  下午 3:37
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 * T 泛型 正常使用布局生成的 ViewBanding
 * DataBindingUtil 放回的对象支持DataBinding 与 ViewBanding
 * BaseViewModel 驱动 ui显示 Toast以及Loading
 */
public abstract class BaseVmActivity<T extends ViewDataBinding, K extends BaseViewModel> extends
        BaseBindingActivity<T> implements ICreateRootView.IBaseViewModel {
    protected K mViewModel;

    @Override
    public void initView() {
        mViewModel = ViewModelFactory.createViewModel(this, getClass(), mViewModel);
        ViewModelFactory.initBaseViewModel(mViewModel, this, mLoadingView);
        addViewModel();
        initViews();
    }

}
