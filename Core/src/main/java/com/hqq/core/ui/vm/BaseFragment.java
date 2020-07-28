package com.hqq.core.ui.vm;

import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;

import com.hqq.core.ui.BaseCoreFragment;
import com.hqq.core.ui.builder.ICreateRootViewBuilder;
import com.hqq.core.utils.ToastUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.ui.vm
 * @FileName :   BaseFragment
 * @Date : 2020/7/28 0028  下午 5:36
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public abstract class BaseFragment<T extends ViewDataBinding, K extends BaseViewModel> extends BaseCoreFragment implements ICreateRootViewBuilder.IBaseVMBuilder {
    protected T mBinding;
    protected K mViewModel;

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

    @Override
    public void initView() {
        createViewModel();
        initBaseViewModel();
        addViewModel();
        initViews();
    }

    /**
     * 初始化 BaseViewModel中关联ui的字段
     */
    private void initBaseViewModel() {
        mViewModel.mShowLoading.observe(this, aBoolean -> {
            if (aBoolean) {
                mLoadingView.show();
            } else {
                mLoadingView.dismiss();
            }
        });
        mViewModel.mShowToast.observe(this, s -> ToastUtils.showToast(s));
    }

    /**
     * 创建ViewModel 保证不为空
     * viewModel 的创建需要在 onCreate 之后
     */
    private void createViewModel() {
        if (mViewModel == null) {
            // 利用反射获取泛型类型 创建ViewModel 对象
            Class modelClass;
            Type type = getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                modelClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[1];
            } else {
                modelClass = BaseViewModel.class;
            }
            mViewModel = createViewModel(this, modelClass);
        }
    }

    private K createViewModel(BaseFragment tkBaseActivity, Class modelClass) {
        return (K) new ViewModelProvider(tkBaseActivity).get(modelClass);
    }


}
