package com.hqq.core.ui.vm

import android.content.Intent
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.hqq.core.ui.base.BaseViewModel
import com.hqq.core.ui.binding.BaseBindingFragment
import com.hqq.core.ui.base.IRootView.IBaseViewModelActivity
import com.hqq.core.ui.base.BaseViewModel.OpenActivityComponent
import com.hqq.core.ui.vm.ViewModelFactory.createViewModel

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.ui.vm
 * @FileName :   BaseFragment
 * @Date : 2020/7/28 0028  下午 5:36
 * @Email : qiqiang213@gmail.com
 * @Descrive :  同理Activity
 */
abstract class BaseVmFragment<T : ViewDataBinding, K : BaseViewModel?> : BaseBindingFragment<T>(), IBaseViewModelActivity, IOpenActivity {
    protected var viewMode: K? = null
    override fun initView() {
        viewMode = getViewModel() as? K
        viewMode?.let {
            lifecycle.addObserver(it)
            ViewModelFactory.initBaseViewModel(it, this, loadingView)
            ViewModelFactory.initOpenActivity(it, this, this)
        }
        addViewModel()
        initViews()
        viewMode?.let {
            it.initData()
        }
    }


    override fun getViewModel(): ViewModel? {
        return createViewModel(this, javaClass, viewMode)
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
        val intent = Intent(activity, openActivityComponent.activityClass)
        if (openActivityComponent.bundle != null) {
            intent.putExtras(openActivityComponent.bundle!!)
        }
        activity?.startActivityForResult(intent, openActivityComponent.activityResult)
    }
}