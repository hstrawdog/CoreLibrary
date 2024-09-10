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
    protected lateinit var viewModel: K
        get
    override fun initView() {
        initViewModel()
        addViewModel()
        initViews()
        viewModel.initData(arguments)
    }

    /**
     *  创建ViewModel
     */
    private fun initViewModel() {
        viewModel = createViewModel() as K
        viewModel.let {
            lifecycle.addObserver(it)
            ViewModelFactory.initBaseViewModel(it, this, loadingView)
            ViewModelFactory.initOpenActivity(it, this, this)
        }
    }

    /**
     *  创建ViewModel
     */
    override fun createViewModel(): K {
        return if (this::viewModel.isInitialized) {
            ViewModelFactory.createViewModel(this, javaClass, viewModel) as K
        } else {
            ViewModelFactory.createViewModel(this, javaClass, null) as K
        }
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