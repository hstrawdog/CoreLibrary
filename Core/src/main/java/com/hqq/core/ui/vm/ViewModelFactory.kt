package com.hqq.core.ui.vm

import androidx.lifecycle.*
import com.hqq.core.ui.builder.IOpenActivity
import com.hqq.core.utils.ToastUtils
import com.hqq.core.widget.LoadingView
import java.lang.reflect.ParameterizedType

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.ui.binding
 * @FileName :   ViewModelFactory
 * @Date : 2020/7/30 0030  上午 11:04
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
internal object ViewModelFactory {
    /**
     * 初始化 BaseViewModel中关联ui的字段
     */
    fun initBaseViewModel(viewModel: BaseViewModel?, lifecycleOwner: LifecycleOwner?, loadingView: LoadingView?) {
        viewModel?.showLoading?.observe(lifecycleOwner!!, Observer { aBoolean: Boolean? ->
            if (loadingView != null) {
                if (aBoolean!!) {
                    loadingView?.show()
                } else {
                    loadingView?.dismiss()
                }
            }
        })
        viewModel?.showToast?.observe(lifecycleOwner!!, Observer { s: String -> ToastUtils.showToast(s) })
    }

    /**
     * 创建ViewModel 保证不为空
     * viewModel 的创建需要在 onCreate 之后
     *
     * @param aClass
     * @return
     */
    fun <K : ViewModel?> createViewModel(viewModelStoreOwner: ViewModelStoreOwner?, aClass: Class<*>, mViewModel: K): K? {
        var mViewModel: K? = mViewModel
        if (mViewModel == null) {
            // 利用反射获取泛型类型 创建ViewModel 对象
            val modelClass: Class<out ViewModel>
            val type = aClass.genericSuperclass
            if (type is ParameterizedType) {
                modelClass = type.actualTypeArguments[1] as Class<ViewModel>
            } else {
                modelClass = BaseViewModel::class.java
            }
            mViewModel = createViewModel(viewModelStoreOwner, modelClass) as K
        }
        return mViewModel
    }

    /**
     * 创建 ViewModel
     *
     * @param viewModelStoreOwner
     * @param modelClass
     * @param <K>
     * @return
     */
    fun <K : ViewModel?> createViewModel(viewModelStoreOwner: ViewModelStoreOwner?, modelClass: Class<K>?): K {
        return ViewModelProvider(viewModelStoreOwner!!)[modelClass!!]
    }

    fun initOpenActivity(viewModel: BaseViewModel, lifecycleOwner: LifecycleOwner?, openActivity: IOpenActivity) {
        viewModel.openActivityComponentMutableLiveData.observe(lifecycleOwner!!, Observer { openActivityComponent -> openActivity.openActivity(openActivityComponent) })
    }
}