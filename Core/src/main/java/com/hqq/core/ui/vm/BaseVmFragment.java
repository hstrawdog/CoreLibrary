package com.hqq.core.ui.vm;

import android.content.Intent;

import androidx.databinding.ViewDataBinding;

import com.hqq.core.ui.binding.BaseBindingFragment;
import com.hqq.core.ui.builder.ICreateRootView;
import com.hqq.core.ui.builder.IOpenActivity;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.ui.vm
 * @FileName :   BaseFragment
 * @Date : 2020/7/28 0028  下午 5:36
 * @Email : qiqiang213@gmail.com
 * @Descrive :  同理Activity
 */
public abstract class BaseVmFragment<T extends ViewDataBinding, K extends BaseViewModel>
        extends BaseBindingFragment implements ICreateRootView.IBaseViewModel , IOpenActivity {
    protected K mViewModel;

    @Override
    public void initView() {
        mViewModel = ViewModelFactory.createViewModel(this, getClass(), mViewModel);
        ViewModelFactory.initBaseViewModel(mViewModel, this, mLoadingView);
        ViewModelFactory.initOpenActivity(mViewModel,this,this);

        addViewModel();
        initViews();
    }
    /**
     * 正常情况下 v对应一个VM
     * 如果需要添加多个VM  重写此方法
     */
    @Override
    public void addViewModel() {
        if (getBindingViewModelId() != 0) {
            mBinding.setVariable(getBindingViewModelId(), mViewModel);
        }
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
