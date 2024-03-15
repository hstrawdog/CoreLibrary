package com.easy.core.ui.base

import androidx.lifecycle.*
import com.easy.core.utils.ToastUtils
import com.easy.core.widget.LoadingView
import java.lang.reflect.ParameterizedType

/**
 * @Author : huangqiqiang
 * @Package : com.easy.core.ui.binding
 * @FileName :   ViewModelFactory
 * @Date : 2020/7/30 0030  上午 11:04
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
internal object ViewModelFactory {
    /**
     * 初始化 BaseViewModel中关联ui的字段
     */
    fun initBaseViewModel(
        viewModel: BaseViewModel, lifecycleOwner: LifecycleOwner, loadingView: LoadingView?
    ) {
        viewModel.loadingView.observe(lifecycleOwner, Observer { aBoolean: Boolean? ->
            if (loadingView != null) {
                if (aBoolean!!) {
                    loadingView.show()
                } else {
                    loadingView.dismiss()
                }
            }
        })
        viewModel.toast.observe(lifecycleOwner, Observer { s: String -> ToastUtils.showToast(s) })
    }

    /**
     * 创建ViewModel 保证不为空
     * viewModel 的创建需要在 onCreate 之后
     * @param aClass   这边 取的是第一个泛型类
     * @return
     */
    fun <K : ViewModel> createViewModel(
        viewModelStoreOwner: ViewModelStoreOwner, aClass: Class<*>, viewModelK: K?
    ): ViewModel {
        var viewModel: ViewModel? = null
        viewModelK?.let {
            viewModel = it
        }
        if (viewModel == null) {
            // 利用反射获取泛型类型 ViewModel子类 对象名称
            val modelClass: Class<out ViewModel>
            val type = aClass.genericSuperclass
            if (type is ParameterizedType) {
                modelClass = type.actualTypeArguments[0] as Class<ViewModel>
            } else {
                modelClass = BaseViewModel::class.java
            }
            viewModel = createViewModel(viewModelStoreOwner, modelClass)
        }
        return viewModel!!
    }

    /**
     * 通过 ViewModelProvider
     * 创建 ViewModel
     *
     * @param viewModelStoreOwner
     * @param modelClass
     * @param <K>
     * @return
     */
    fun <K : ViewModel> createViewModel(
        viewModelStoreOwner: ViewModelStoreOwner, modelClass: Class<K>
    ): ViewModel {
        return ViewModelProvider(viewModelStoreOwner)[modelClass]
    }


    /**
     *  打开某个Activity
     */
    fun initOpenActivity(
        viewModel: BaseViewModel, lifecycleOwner: LifecycleOwner?, openActivity: IOpenActivity
    ) {
        viewModel.openActivityComponentMutableLiveData.observe(lifecycleOwner!!,
            Observer { openActivityComponent -> openActivity.openActivity(openActivityComponent) })
    }

    /**
     *  绑定返回事件
     */
    fun initGoBack(
        viewModel: BaseViewModel, lifecycleOwner: LifecycleOwner, iGoBack: IFinishActivity
    ) {
        viewModel.goBack.observe(lifecycleOwner, Observer { goBackComponent ->
            iGoBack.finishActivity(goBackComponent)
        })
    }

}