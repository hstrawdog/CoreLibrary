package com.easy.core.ui.base

import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import com.easy.core.kt.open
import com.easy.core.kt.setResultOk
import com.easy.core.utils.ToastUtils
import kotlinx.coroutines.launch

/**
 * 自动处理 [BaseStateViewModel] 通用 UI 指令的 ViewBinding Activity。
 */
abstract class BaseStateViewModelActivity<Binding : ViewBinding, VM : BaseStateViewModel> :
    BaseViewBindingActivity<Binding>() {

    protected abstract val viewModel: VM

    final override fun initView() {
        observeBaseViewModel()
        initViews()
    }

    protected abstract fun initViews()

    private fun observeBaseViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.openEvents.collect { request ->
                        open(request.activityClass, request.bundle ?: Bundle(), request.callback)
                    }
                }
                launch {
                    viewModel.toastEvents.collect { ToastUtils.showToast(it) }
                }
                launch {
                    viewModel.finishEvents.collect { request ->
                        request.result?.let(::setResultOk)
                        finish()
                    }
                }
                launch {
                    viewModel.isLoading.collect { isLoading ->
                        if (isLoading) loadingView.show() else loadingView.dismiss()
                    }
                }
            }
        }
    }
}
