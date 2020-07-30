package com.hqq.core.ui.binding;

import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.hqq.core.ui.base.BaseFragment;
import com.hqq.core.ui.builder.ICreateRootView;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.ui.binding
 * @FileName :   BaseBindingFragment
 * @Date : 2020/7/30 0030  上午 10:54
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public abstract class BaseBindingFragment<T extends ViewDataBinding> extends BaseFragment implements ICreateRootView.IBanding {
    protected T mBinding;

    @Override
    final public int getLayoutViewId() {
        return 0;
    }

    @Override
    public View getLayoutView(ViewGroup parent) {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), getLayoutId(), parent, false);
        mBinding.setLifecycleOwner(this);
        return mBinding.getRoot();

    }

}
