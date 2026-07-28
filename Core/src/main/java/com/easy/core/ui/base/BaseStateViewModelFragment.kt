package com.easy.core.ui.base

import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import com.easy.core.kt.open
import com.easy.core.utils.ToastUtils
import kotlinx.coroutines.launch

/**
 * 自动处理 [BaseStateViewModel] 通用 UI 指令的 ViewBinding Fragment。
 */
abstract class BaseStateViewModelFragment<Binding : ViewBinding, VM : BaseStateViewModel> :
    BaseViewBindingFragment<Binding>() {

    protected abstract val viewModel: VM

    final override fun initView() {
        observeBaseViewModel()
        initViews()
    }

    protected abstract fun initViews()

    private fun observeBaseViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.openEvents.collect { request ->
                        open(request.activityClass, request.bundle ?: Bundle(), request.callback)
                    }
                }
                launch {
                    viewModel.toastEvents.collect { ToastUtils.showToast(it) }
                }
                launch {
                    viewModel.isLoading.collect { isLoading ->
                        if (isLoading) loadingView?.show() else loadingView?.dismiss()
                    }
                }
            }
        }
    }
}
