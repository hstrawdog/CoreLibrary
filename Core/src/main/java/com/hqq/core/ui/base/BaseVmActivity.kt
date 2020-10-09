package com.hqq.core.ui.base

import android.content.Intent
import androidx.databinding.ViewDataBinding
import com.hqq.core.ui.base.IRootView.IBaseViewModelActivity
import com.hqq.core.ui.base.BaseViewModel.OpenActivityComponent

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
 *
 * 正常情况下 viewModel  不应该在Base中初始化
 *
 */
abstract class BaseVmActivity<T : ViewDataBinding, K : BaseViewModel>
    : BaseDataBindingActivity<T>(), IBaseViewModelActivity, IOpenActivity, IFinishActivity {
    lateinit var viewMode: K

    override fun initView() {
        viewMode = getViewModel()
        viewMode?.let {
            lifecycle.addObserver(it)
            ViewModelFactory.initBaseViewModel(it, this, loadingView)
            ViewModelFactory.initOpenActivity(it, this, this)
            ViewModelFactory.initGoBack(it, this, this)
        }
        addViewModel()
        initViews()
        viewMode?.let {
            it.initData()
        }
    }

    override fun getViewModel(): K {
        return ViewModelFactory.createViewModel(this, javaClass, viewMode) as K
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
        if (bindingViewModelId != 0) {
            binding.setVariable(bindingViewModelId, viewMode)
        }
    }

    override fun finishActivity(goBackComponent: BaseViewModel.GoBackComponent) {
        if (goBackComponent.goBack) {
            finish()
        }
    }

    override fun openActivity(openActivityComponent: OpenActivityComponent) {
        val intent = Intent(activity, openActivityComponent.activityClass)
        if (openActivityComponent.bundle != null) {
            intent.putExtras(openActivityComponent.bundle!!)
        }
        activity.startActivityForResult(intent, openActivityComponent.activityResult)
    }
}