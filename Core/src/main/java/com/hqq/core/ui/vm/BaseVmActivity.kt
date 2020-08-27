package com.hqq.core.ui.vm

import android.content.Intent
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.hqq.core.ui.binding.BaseBindingActivity
import com.hqq.core.ui.base.ICreateRootView.IBaseViewModel
import com.hqq.core.ui.vm.BaseViewModel.OpenActivityComponent

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.ui.vm
 * @FileName :   BaseActivity
 * @Date : 2020/7/22 0022  下午 3:37
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 * T 泛型 正常使用布局生成的 ViewBanding
 * DataBindingUtil 放回的对象支持DataBinding 与 ViewBanding
 * BaseViewModel 驱动 ui显示 Toast以及Loading
 */
abstract class BaseVmActivity<T : ViewDataBinding, K : BaseViewModel>
    : BaseBindingActivity<T>(), IBaseViewModel, IOpenActivity {
    var viewMode: K? = null


    override fun initView() {
        viewMode = getViewModel() as? K
        viewMode?.let {
            lifecycle.addObserver(it)
            ViewModelFactory.initBaseViewModel(it, this, loadingView)
            ViewModelFactory.initOpenActivity(it, this, this)
        }
        addViewModel()
        initViews()
    }

    override fun getViewModel(): ViewModel? {
        return ViewModelFactory.createViewModel(this, javaClass, viewMode)
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(viewMode!!)
    }

    /**
     * 正常情况下 v对应一个VM
     * 如果需要添加多个VM  重写此方法
     */
    override fun addViewModel() {
        if (layoutId != 0) {
            binding!!.setVariable(layoutId, viewMode)
        }
    }

    override fun openActivity(openActivityComponent: OpenActivityComponent) {
        val intent = Intent(activity, openActivityComponent.mActivityClass)
        if (openActivityComponent.mBundle != null) {
            intent.putExtras(openActivityComponent.mBundle!!)
        }
        activity!!.startActivityForResult(intent, openActivityComponent.mActivityResult)
    }
}