package com.hqq.core.ui.vm;

import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewbinding.ViewBinding;

import com.hqq.core.ui.BaseCoreActivity;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.ui.vm
 * @FileName :   BaseActivity
 * @Date : 2020/7/22 0022  下午 3:37
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 * T 泛型 正常使用布局生成的 ViewBanding
 * DataBindingUtil 放回的对象支持DataBinding 与 ViewBanding
 */
public abstract class BaseActivity<T extends ViewBinding, K extends BaseViewModel> extends BaseCoreActivity {
    protected T mBinding;
    protected K mViewModel;

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
        mBinding = (T) DataBindingUtil.inflate(getLayoutInflater(), getLayoutId(), parent, false);
        return mBinding.getRoot();
    }

    @Override
    public void initView() {

        Class modelClass;
        Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            modelClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[1];
        } else {
            modelClass = BaseViewModel.class;
        }
        mViewModel = createViewModel(this, modelClass);

        addViewModel();
        initViews();

    }

    private K createViewModel(AppCompatActivity tkBaseActivity, Class modelClass) {
        return (K) new ViewModelProvider(tkBaseActivity).get(modelClass);
    }

    /**
     * 添加ViewModel 与布局使用的对象
     */
    protected abstract void addViewModel();

    /**
     * 通过布局id 生成对应的 banding类
     * 只是单独使用ViewBanding的话直接用banding生成的对应类就可以
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 初始化View的要实现的默认值 正常应该通过ViewModel驱动到xml 或者Activity中
     */
    protected abstract void initViews();

}
