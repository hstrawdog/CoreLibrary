package com.hqq.core.kt

import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.hqq.core.ui.base.BaseActivity
import com.hqq.core.ui.base.BaseFragment
import com.hqq.core.ui.base.BaseViewModel
import com.hqq.core.utils.log.LogUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.kt
 * @Date  : 上午 10:56
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
fun BaseViewModel.launch( block: suspend () -> Unit, error: suspend (Throwable) -> Unit) {
    viewModelScope.launch(Dispatchers.IO) {
        try {
            block()
        } catch (e: Exception) {
            LogUtils.dInfo("launch: error ${e.printStackTrace()}" )
            error(e)
        }
    }
}

fun BaseActivity.launch(block: suspend () -> Unit, error: suspend (Throwable) -> Unit) {
    lifecycleScope.launch(Dispatchers.IO) {
        try {
            block()
        } catch (e: Exception) {
            LogUtils.dInfo("launch: error ${e.printStackTrace()}" )
            error(e)
        }
    }
}

fun BaseFragment.launch(block: suspend () -> Unit, error: suspend (Throwable) -> Unit) {
    lifecycleScope.launch(Dispatchers.IO) {
        try {
            block()
        } catch (e: Exception) {
            LogUtils.dInfo("launch: error ${e.printStackTrace()}" )
            error(e)
        }
    }
}
