package com.easy.core.ui.base

import android.app.Activity
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.ViewDataBinding
import com.easy.core.BR
import com.easy.core.ui.base.BaseViewModel.OpenActivityComponent

/**
 * @Author : huangqiqiang
 * @Package : com.easy.core.ui.vm
 * @FileName :   BaseActivity
 * @Date : 2020/7/22 0022  下午 3:37
 * @Email : qiqiang213@gmail.com
 * @Describe :
 * T 泛型 正常使用布局生成的 ViewBanding
 * DataBindingUtil 放回的对象支持DataBinding 与 ViewBanding
 * BaseViewModel 驱动 ui显示 Toast以及Loading
 * 正常情况下 viewModel  不应该在Base中初始化
 */
abstract class BaseVmActivity<K : BaseViewModel, T : ViewDataBinding>
    : BaseDataBindingActivity<T>(), IBaseViewModelActivity, IOpenActivity, IFinishActivity {
     val viewModel: K by lazy {
         createViewModel().apply {
             lifecycle.addObserver(this)
             ViewModelFactory.initBaseViewModel(this, this@BaseVmActivity, this@BaseVmActivity.loadingView)
             ViewModelFactory.initOpenActivity(this, this@BaseVmActivity, this@BaseVmActivity)
             ViewModelFactory.initGoBack(this, this@BaseVmActivity, this@BaseVmActivity)
         }
     }

    override fun initView() {
        addViewModel()
        initViews()
        viewModel.initData(intent.extras)
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
//            ViewModelFactory.createViewModel(this, javaClass, viewModel) as K
//        } else {
//            ViewModelFactory.createViewModel(this, javaClass, null) as K
//        }
        return  ViewModelFactory.createViewModel(this, javaClass, null) as K
    }

    /**
     *  解绑
     */
    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(viewModel)
    }

    /**
     * 正常情况下 一个v对应一个VM
     * 如果需要添加多个VM  重写此方法
     */
    override fun addViewModel() {
//        if (bindingViewModelId() != 0) {
//        }
        binding.setVariable(BR.vm, viewModel)

    }

    /**
     *  将结果回调给 ViewModel
     * @param requestCode Int
     * @param resultCode Int
     * @param data Intent?
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        viewModel.onActivityResult(requestCode, resultCode, data)
    }

    /**
     * ViewModel 过来 关闭当前界面
     * @param goBackComponent GoBackComponent
     */
    override fun finishActivity(goBackComponent: BaseViewModel.GoBackComponent) {
        if (goBackComponent.goBack) {
            goBackComponent.bundle?.let {
                setResult(Activity.RESULT_OK, Intent().apply {
                    putExtras(it)
                })
            }
            finish()
        }
    }

    /**
     * ViewModel 中过来的 打开新的页面
     * @param openActivityComponent OpenActivityComponent
     */
    override fun openActivity(openActivityComponent: OpenActivityComponent) {
        registerForActivityResult(ActivityResultContracts.StartActivityForResult(), openActivityComponent.result)
                .launch(Intent(this, openActivityComponent.activityClass).apply {
                    openActivityComponent.bundle?.let {
                        this.putExtras(it)
                    }
                })
    }
}