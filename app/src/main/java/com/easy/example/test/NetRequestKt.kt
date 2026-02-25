package com.easy.example.test

import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.easy.core.ui.base.BaseActivity
import com.easy.core.ui.base.BaseFragment
import com.easy.core.ui.base.BaseViewModel
import com.easy.core.utils.ToastUtils
import com.easy.core.utils.log.LogUtils
import com.easy.example.demo.net.NetResponseBody
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


/**
 * @Author : huangqiqiang
 * @Package : com.easy.core.kt
 * @Date  : 上午 10:56
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */

inline fun <reified T> BaseFragment.launchRequest(crossinline block: suspend () -> EncodeResponseBody,
                                                  crossinline success: (T) -> Unit,
                                                  noinline error: suspend (Throwable) -> Unit) {
    lifecycleScope.launch(Dispatchers.IO) {
        launchRequestBase(block, success, error)
    }
}

inline fun <reified T> BaseViewModel.launchRequest(crossinline block: suspend () -> EncodeResponseBody,
                                                   crossinline success: (T) -> Unit,
                                                   noinline error: suspend (Throwable) -> Unit) {
    viewModelScope.launch(Dispatchers.IO) {
        launchRequestBase(block, success, error)
    }
}

inline fun <reified T> CoroutineScope.launchRequest(crossinline block: suspend () -> EncodeResponseBody,
                                                    crossinline success: (T) -> Unit,
                                                    noinline error: suspend (Throwable) -> Unit) {
    launch(Dispatchers.IO) {
        launchRequestBase(block, success, error)
    }
}

inline fun <reified T> BaseActivity.launchRequest(crossinline block: suspend () -> EncodeResponseBody,
                                                  crossinline success: (T) -> Unit,
                                                  noinline error: suspend (Throwable) -> Unit) {
    lifecycleScope.launch(Dispatchers.IO) {
        launchRequestBase(block, success, error)
    }
}


suspend inline fun <reified T> launchRequestBase(crossinline block: suspend () -> EncodeResponseBody,
                                             crossinline success: (T) -> Unit,
                                             noinline error: suspend (Throwable) -> Unit) {
    runCatching {
        block()
    }.onSuccess {


        LogUtils.e { "111" }
//        val str = AESUtils.decrypt(it.getData())
//        if (BuildConfig.DEBUG) {
//            withContext(Dispatchers.Main) {
//                LogUtils.e { "解密结果 ------------" }
//                LogUtils.e { str }
//            }
//        }
//        // 将str  解析成T  对象
//        val type = object : TypeToken<NetResponseBody<T>>() {}.type
//        val responseBody: NetResponseBody<T> = Gson().fromJson(str, type)
//        withContext(Dispatchers.Main) {
//            if ("200" == responseBody.status && "200" == responseBody.code) {
//                if ("406" == responseBody.code) {
//                    //  触发登录
//                    error(CustomException(responseBody.message, responseBody.code, null))
//                } else {
//                    error(CustomException(responseBody.message, responseBody.code, null))
//                }
//            }
//        }
    }
        .onFailure {
            LogUtils.e { "${it}" }
            //// 处理异常
            withContext(Dispatchers.Main) {
                error(it)
            }
        }
}

// Gson 单例
val gson: Gson = GsonBuilder().create()

inline fun <reified T> BaseFragment.launchEncryptedRequest(crossinline block: suspend () -> EncodeResponseBody,
                                                           crossinline onSuccess: (T) -> Unit,
                                                           noinline onError: suspend (Throwable) -> Unit) =
    launchRequestWithScope(lifecycleScope, block, onSuccess, onError)

inline fun <reified T> BaseActivity.launchEncryptedRequest(crossinline block: suspend () -> EncodeResponseBody,
                                                           crossinline onSuccess: (T) -> Unit,
                                                           noinline onError: suspend (Throwable) -> Unit) =
    launchRequestWithScope(lifecycleScope, block, onSuccess, onError)

inline fun <reified T> BaseViewModel.launchEncryptedRequest(crossinline block: suspend () -> EncodeResponseBody,
                                                            crossinline onSuccess: (T) -> Unit,
                                                            noinline onError: suspend (Throwable) -> Unit) =
    launchRequestWithScope(viewModelScope, block, onSuccess, onError)

inline fun <reified T> CoroutineScope.launchEncryptedRequest(crossinline block: suspend () -> EncodeResponseBody,
                                                             crossinline onSuccess: (T) -> Unit,
                                                             noinline onError: suspend (Throwable) -> Unit) =
    launchRequestWithScope(this, block, onSuccess, onError)

inline fun <reified T> launchRequestWithScope(scope: CoroutineScope,
                                              crossinline block: suspend () -> EncodeResponseBody,
                                              crossinline onSuccess: (T) -> Unit,
                                              noinline onError: suspend (Throwable) -> Unit) {
    scope.launch(Dispatchers.IO) {
        runCatching { block() }.onSuccess { response ->
//                val decrypted = AESUtils.decrypt(response.getData())
//                if (BuildConfig.DEBUG) {
//                    LogUtils.e { "解密结果 ------------" }
//                    LogUtils.e { decrypted }
//                }
//
//                val type = object : TypeToken<NetResponseBody<T>>() {}.type
//                val responseBody: NetResponseBody<T> = gson.fromJson(decrypted, type)
//
//                withContext(Dispatchers.Main) {
//                    when (responseBody.code) {
//                        "200" -> onSuccess(responseBody.getBody())
//                        "406" -> onError(CustomException(responseBody.message, responseBody.code))
//                        else -> onError(CustomException(responseBody.message, responseBody.code))
//                    }
//                }
            }
            .onFailure {
                LogUtils.e { "请求异常: $it" }
                withContext(Dispatchers.Main) {
                    onError(it)
                }
            }
    }
}


suspend inline fun <reified T> requestEncryptedSync(crossinline block: suspend () -> EncodeResponseBody): T {
    return withContext(Dispatchers.IO) {
        val response = block()
//        val decrypted = AESUtils.decrypt(response.getData())
//
//        if (BuildConfig.DEBUG) {
//            LogUtils.e { "解密结果 ------------" }
//            LogUtils.e { decrypted }
//        }
//
//        val type = object : TypeToken<NetResponseBody<T>>() {}.type
//        val responseBody: NetResponseBody<T> = gson.fromJson(decrypted, type)

//        if (responseBody.status == "200" && responseBody.code == "200") {
//            responseBody.getBody()
//        } else {
//            throw CustomException(responseBody.message, responseBody.code)
//        }
            throw CustomException("","")

    }
}


fun ToastUtils.showError(exception: Throwable) {
    val message = (exception as? CustomException)?.message ?: "请求失败,请稍后再试"
    showToast(message)
}