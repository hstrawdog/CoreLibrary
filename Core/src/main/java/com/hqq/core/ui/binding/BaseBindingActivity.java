package com.hqq.core.ui.binding;

import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.hqq.core.ui.base.BaseActivity;
import com.hqq.core.ui.builder.ICreateRootView;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.ui.binding
 * @FileName :   BaseBindingActivity
 * @Date : 2020/7/30 0030  上午 10:41
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public abstract class BaseBindingActivity<T extends ViewDataBinding> extends BaseActivity implements ICreateRootView.IBanding {
    protected T mBinding;

    /**
     * 禁止 子类继承使用 保证走的都是getLayoutView方法
     *
     * @return
     */
    @Override
    public final int getLayoutViewId() {
        return 0;
    }

    @Override
    public View getLayoutView(ViewGroup parent) {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), getLayoutId(), parent, false);
        mBinding.setLifecycleOwner(this);

        return mBinding.getRoot();
    }

}
