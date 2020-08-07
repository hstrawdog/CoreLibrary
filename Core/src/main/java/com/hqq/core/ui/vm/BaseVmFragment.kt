package com.hqq.core.ui.vm

import android.content.Intent
import androidx.databinding.ViewDataBinding
import com.hqq.core.ui.binding.BaseBindingFragment
import com.hqq.core.ui.builder.ICreateRootView.IBaseViewModel
import com.hqq.core.ui.builder.IOpenActivity
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
abstract class BaseVmFragment<T : ViewDataBinding?, K : BaseViewModel?> : BaseBindingFragment<T>(), IBaseViewModel, IOpenActivity {
    @JvmField
    protected var mViewModel: K? = null
    override fun initView() {
        mViewModel = createViewModel(this, javaClass, mViewModel)
        initBaseViewModel(mViewModel!!, this, mLoadingView)
        initOpenActivity(mViewModel!!!!, this, this)
        addViewModel()
        initViews()
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