package com.easy.example.demo.net

import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.easy.core.ui.base.BaseActivity
import com.easy.core.ui.base.BaseFragment
import com.easy.core.ui.base.BaseViewModel
import com.easy.core.utils.log.LogUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

/**
 * @Author : huangqiqiang
 * @Package : com.easy.core.kt
 * @Date  : 上午 10:56
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
fun <T> BaseViewModel.launch(block: suspend () -> NetResponseBody<T>, error: suspend (Throwable) -> Unit) {
    viewModelScope.launch(Dispatchers.IO) {
        try {
            responseData(block())
        } catch (e: Exception) {
            LogUtils.dInfo{"launch: error ${e.printStackTrace()}"}
            error(e)
        }
    }
}

fun <T> responseData(block: NetResponseBody<T>) {

}


fun BaseActivity.launch(block: suspend () -> Unit, error: suspend (Throwable) -> Unit) {
    lifecycleScope.launch(Dispatchers.IO) {
        launchRequest(block, error)

    }
}

fun BaseFragment.launch(block: suspend () -> Unit, error: suspend (Throwable) -> Unit) {
    lifecycleScope.launch(Dispatchers.IO) {
        launchRequest(block, error)
    }
}

suspend fun launchRequest(block: suspend () -> Unit, error: suspend (Throwable) -> Unit) {
    runCatching {
        block()
    }.onSuccess {
        LogUtils.e { "" }
    }
        .onFailure {
            //// 处理异常
            error(it)
        }
    //如果发生异常，则返回 null
//        .getOrNull()

}
