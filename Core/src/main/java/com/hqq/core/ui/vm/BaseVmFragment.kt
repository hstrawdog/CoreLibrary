package com.hqq.core.ui.vm

import android.content.Intent
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.hqq.core.ui.binding.BaseBindingFragment
import com.hqq.core.ui.base.IRootView.IBaseViewModel
import com.hqq.core.ui.vm.BaseViewModel.OpenActivityComponent
import com.hqq.core.ui.vm.ViewModelFactory.createViewModel
import com.hqq.core.ui.vm.ViewModelFactory.initBaseViewModel
import com.hqq.core.ui.vm.ViewModelFactory.initOpenActivity

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.ui.vm
 * @FileName :   BaseFragment
 * @Date : 2020/7/28 0028  下午 5:36
 * @Email : qiqiang213@gmail.com
 * @Descrive :  同理Activity
 */
abstract class BaseVmFragment<T : ViewDataBinding, K : BaseViewModel?> : BaseBindingFragment<T>(), IBaseViewModel, IOpenActivity {
    protected var viewMode: K? = null
    override fun initView() {
        viewMode = getViewModel() as? K
        loadingView?.let { initBaseViewModel(viewMode!!, this, it) }
        initOpenActivity(viewMode!!!!, this, this)
        addViewModel()
        initViews()
    }


    override fun getViewModel(): ViewModel? {
      return  createViewModel(this, javaClass, viewMode)
    }

    /**
     * 正常情况下 v对应一个VM
     * 如果需要添加多个VM  重写此方法
     */
    override fun addViewModel() {
        if (bindingViewModelId != 0) {
            binding!!.setVariable(bindingViewModelId, viewMode)
        }
    }

    override fun openActivity(openActivityComponent: OpenActivityComponent) {
        val intent = Intent(activity, openActivityComponent.mActivityClass)
        if (openActivityComponent.mBundle != null) {
            intent.putExtras(openActivityComponent.mBundle!!)
        }
        activity?.startActivityForResult(intent, openActivityComponent.mActivityResult)
    }
}