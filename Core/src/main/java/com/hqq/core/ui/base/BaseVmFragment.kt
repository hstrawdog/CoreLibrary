package com.hqq.core.ui.base

import android.content.Intent
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.hqq.core.ui.base.IRootView.IBaseViewModelActivity
import com.hqq.core.ui.base.BaseViewModel.OpenActivityComponent

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.ui.vm
 * @FileName :   BaseFragment
 * @Date : 2020/7/28 0028  下午 5:36
 * @Email : qiqiang213@gmail.com
 * @Descrive :  同理Activity
 */
abstract class BaseVmFragment<K : BaseViewModel,T : ViewDataBinding > : BaseDataBindingFragment<T>(), IBaseViewModelActivity, IOpenActivity {
    protected lateinit var viewMode: K
    override fun initView() {
        initViewModel()
        addViewModel()
        initViews()
        viewMode?.let {
            it.initData()
        }
    }

    /**
     *  创建ViewModel
     */
    private fun initViewModel() {
        viewMode = getViewModel() as K
        viewMode?.let {
            lifecycle.addObserver(it)
            ViewModelFactory.initBaseViewModel(it, this, loadingView)
            ViewModelFactory.initOpenActivity(it, this, this)
        }
    }

    /**
     *  创建ViewModel
     */
    override fun getViewModel(): K {
        return if (this::viewMode.isInitialized) {
            ViewModelFactory.createViewModel(this, javaClass, viewMode) as K
        } else {
            ViewModelFactory.createViewModel(this, javaClass, null) as K
        }
    }

    /**
     * 正常情况下 v对应一个VM
     * 如果需要添加多个VM  重写此方法
     */
    override fun addViewModel() {
        if (bindingViewModelId != 0) {
            binding.setVariable(bindingViewModelId, viewMode)
        }
    }

    /**
     * 打开新的界面
     */
    override fun openActivity(openActivityComponent: OpenActivityComponent) {
        val intent = Intent(activity, openActivityComponent.activityClass)
        if (openActivityComponent.bundle != null) {
            intent.putExtras(openActivityComponent.bundle!!)
        }
        startActivityForResult(intent, openActivityComponent.activityResult)
    }
}