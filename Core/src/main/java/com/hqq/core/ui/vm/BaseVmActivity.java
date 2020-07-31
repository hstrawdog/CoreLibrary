package com.hqq.core.ui.vm;

import android.content.Intent;

import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;

import com.hqq.core.ui.binding.BaseBindingActivity;
import com.hqq.core.ui.builder.ICreateRootView;
import com.hqq.core.ui.builder.IOpenActivity;

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
        BaseBindingActivity<T> implements ICreateRootView.IBaseViewModel, IOpenActivity {
    protected K mViewModel;

    @Override
    public void initView() {
        mViewModel = ViewModelFactory.createViewModel(this, getClass(), mViewModel);
        ViewModelFactory.initBaseViewModel(mViewModel, this, mLoadingView);
        ViewModelFactory.initOpenActivity(mViewModel,this,this);

        addViewModel();
        initViews();
    }

    @Override
    public void openActivity(BaseViewModel.OpenActivityComponent openActivityComponent) {
        Intent intent = new Intent(mActivity, openActivityComponent.mActivityClass);
        if (openActivityComponent.mBundle != null) {
            intent.putExtras(openActivityComponent.mBundle);
        }
        mActivity.startActivityForResult(intent, openActivityComponent.mActivityResult);

    }


}
