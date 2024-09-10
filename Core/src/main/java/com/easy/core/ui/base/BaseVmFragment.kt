package com.easy.core.ui.base

import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.ViewDataBinding
import com.easy.core.BR
import com.easy.core.ui.base.BaseViewModel.OpenActivityComponent

/**
 * @Author : huangqiqiang
 * @Package : com.easy.core.ui.vm
 * @FileName :   BaseFragment
 * @Date : 2020/7/28 0028  下午 5:36
 * @Email : qiqiang213@gmail.com
 * @Describe :  同理Activity
 */
abstract class BaseVmFragment<K : BaseViewModel, T : ViewDataBinding> : BaseDataBindingFragment<T>(), IBaseViewModelActivity,
    IOpenActivity {
    val viewModel: K by lazy {
        createViewModel().apply {
            lifecycle.addObserver(this)
            ViewModelFactory.initBaseViewModel(this, this@BaseVmFragment, this@BaseVmFragment.loadingView)
            ViewModelFactory.initOpenActivity(this, this@BaseVmFragment, this@BaseVmFragment)
        }
    }
    override fun initView() {
        addViewModel()
        initViews()
        viewModel.initData(arguments)
    }


    /**
     *  创建ViewModel
     */
    override fun createViewModel(): K {
//        return if (this::viewModel.isInitialized) {
//            ViewModelFactory.createViewModel(this, javaClass, viewModel) as K
//        } else {
//            ViewModelFactory.createViewModel(this, javaClass, null) as K
//        }

        return ViewModelFactory.createViewModel(this, javaClass, null) as K
    }

    /**
     * 正常情况下 v对应一个VM
     * 如果需要添加多个VM  重写此方法
     */
    override fun addViewModel() {
//        if (bindingViewModelId() != 0) {
//            binding.setVariable(bindingViewModelId(), viewMode)
//        }
        binding.setVariable(BR.vm, viewModel)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        viewModel.onActivityResult(requestCode, resultCode, data)
    }

    /**
     * 打开新的界面
     */
    override fun openActivity(openActivityComponent: OpenActivityComponent) {
        registerForActivityResult(ActivityResultContracts.StartActivityForResult(), openActivityComponent.result)
                .launch(Intent(context, openActivityComponent.activityClass).apply {
                    openActivityComponent.bundle?.let {
                        this.putExtras(it)
                    }
                })
    }
}