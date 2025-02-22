package com.easy.core.ui.dialog

import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.easy.core.BR
import com.easy.core.ui.base.BaseViewModel
import com.easy.core.ui.base.IBanding
import com.easy.core.ui.base.IBaseViewModelActivity
import com.easy.core.ui.base.ViewModelFactory

/**
 * @Author : huangqiqiang
 * @Package : com.easy.core.ui.dialog
 * @Date : 15:22
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
abstract class BaseVmDialog<K : BaseViewModel, T : ViewDataBinding> : BaseBindingDialog<T>(), IBaseViewModelActivity,
    IBanding {
    val viewModel: K by lazy {
        createViewModel().apply {
            lifecycle.addObserver(this)
            ViewModelFactory.initBaseViewModel(this, this@BaseVmDialog, this@BaseVmDialog.loadingView)
        }
    }

    override fun getLayoutId(): Int {
        return -1
    }

    override fun initView() {

        addViewModel()
        initViews()
        viewModel.initData(null)
    }

    override fun getBindingView(parent: ViewGroup): View? {
        binding = DataBindingUtil.inflate<T>(layoutInflater, getDialogLayoutId(), parent, false)
            .apply {
                lifecycleOwner = this@BaseVmDialog
            }
        return binding.root
    }

    /**
     * 获取ViewModel
     * 利用反射方式构建ViewMode
     * 必要情况可重写次方法 构建过-> Hilt的方式
     * 需要测试 反射构建与Hilt 注入是否有差别
     * @return K
     */
    override fun createViewModel(): K {
//        return if (this::viewModel.isInitialized) {
//            ViewModelFactory.createViewModel(requireActivity(), javaClass, viewModel) as K
//        } else {
//            ViewModelFactory.createViewModel(requireActivity(), javaClass, null) as K
//        }

        return ViewModelFactory.createViewModel(requireActivity(), javaClass, null) as K
    }

    /**
     * 正常情况下 一个v对应一个VM
     * 如果需要添加多个VM  重写此方法
     */
    override fun addViewModel() {
//        if (bindingViewModelId() != 0) {
//            binding.setVariable(bindingViewModelId(), viewMode)
//        }
        binding.setVariable(BR.vm, viewModel)

    }
}