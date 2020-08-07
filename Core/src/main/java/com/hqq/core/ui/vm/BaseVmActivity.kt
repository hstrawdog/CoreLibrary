package com.hqq.core.ui.vm

import android.content.Intent
import androidx.databinding.ViewDataBinding
import com.hqq.core.ui.binding.BaseBindingActivity
import com.hqq.core.ui.builder.ICreateRootView.IBaseViewModel
import com.hqq.core.ui.builder.IOpenActivity
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
abstract class BaseVmActivity<T : ViewDataBinding?, K : BaseViewModel?>
    : BaseBindingActivity<T>(), IBaseViewModel, IOpenActivity {
    public var mViewModel: K? = null
        get
        set

    override fun initView() {
        mViewModel = ViewModelFactory.createViewModel(this, javaClass, mViewModel)
        lifecycle.addObserver(mViewModel!!)
        ViewModelFactory.initBaseViewModel(mViewModel!!, this, mLoadingView)
        ViewModelFactory.initOpenActivity(mViewModel!!, this, this)
        addViewModel()
        initViews()
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(mViewModel!!)
    }

    /**
     * 正常情况下 v对应一个VM
     * 如果需要添加多个VM  重写此方法
     */
    override fun addViewModel() {
        if (bindingViewModelId != 0) {
            mBinding!!.setVariable(bindingViewModelId, mViewModel)
        }
    }

    override fun openActivity(openActivityComponent: OpenActivityComponent) {
        val intent = Intent(mActivity, openActivityComponent.mActivityClass)
        if (openActivityComponent.mBundle != null) {
            intent.putExtras(openActivityComponent.mBundle!!)
        }
        mActivity.startActivityForResult(intent, openActivityComponent.mActivityResult)
    }
}