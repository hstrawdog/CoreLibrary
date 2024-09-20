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
    fun<T> BaseViewModel.launch(block: suspend () -> NetResponseBody<T>, error: suspend (Throwable) -> Unit) {
    viewModelScope.launch(Dispatchers.IO) {
        try {
            responseData( block())
        } catch (e: Exception) {
            LogUtils.dInfo("launch: error ${e.printStackTrace()}" )
            error(e)
        }
    }
}

fun <T>responseData(block: NetResponseBody<T>) {

}


fun BaseActivity.launch(block: suspend () -> Unit, error: suspend (Throwable) -> Unit) {
    lifecycleScope.launch(Dispatchers.IO) {
        try {
          var  bean=   block()
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
