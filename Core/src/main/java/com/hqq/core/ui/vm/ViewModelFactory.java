package com.hqq.core.ui.vm;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.hqq.core.utils.ToastUtils;
import com.hqq.core.widget.LoadingView;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.ui.binding
 * @FileName :   ViewModelFactory
 * @Date : 2020/7/30 0030  上午 11:04
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
class ViewModelFactory {

    /**
     * 初始化 BaseViewModel中关联ui的字段
     */
    public static void initBaseViewModel(BaseViewModel viewModel, LifecycleOwner lifecycleOwner, LoadingView loadingView) {
        viewModel.mShowLoading.observe(lifecycleOwner, aBoolean -> {
            if (aBoolean) {
                loadingView.show();
            } else {
                loadingView.dismiss();
            }
        });
        viewModel.mShowToast.observe(lifecycleOwner, s -> ToastUtils.showToast(s));
    }





    /**
     * 创建ViewModel 保证不为空
     * viewModel 的创建需要在 onCreate 之后
     *
     * @param aClass
     * @return
     */
    public static <K> K createViewModel(ViewModelStoreOwner viewModelStoreOwner, Class<?> aClass, K mViewModel) {
        if (mViewModel == null) {
            // 利用反射获取泛型类型 创建ViewModel 对象
            Class modelClass;
            Type type = aClass.getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                modelClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[1];
            } else {
                modelClass = BaseViewModel.class;
            }
            mViewModel = createViewModel(viewModelStoreOwner, modelClass);
        }
        return mViewModel;
    }

    /**
     *  创建 ViewModel
     * @param viewModelStoreOwner
     * @param modelClass
     * @param <K>
     * @return
     */
    public static <K> K createViewModel(ViewModelStoreOwner viewModelStoreOwner, Class modelClass) {
        return (K) new ViewModelProvider(viewModelStoreOwner).get(modelClass);
    }

}
