package com.easy.core.ui.base

import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger

/**
 * 面向新页面的通用 ViewModel 基类，不依赖 Activity、Fragment 或 Context。
 * 页面状态和一次性事件由具体 ViewModel 按需定义。
 */
abstract class BaseStateViewModel : ViewModel() {
    private val openChannel = Channel<OpenRequest>(Channel.BUFFERED)
    private val toastChannel = Channel<String>(Channel.BUFFERED)
    private val finishChannel = Channel<FinishRequest>(Channel.BUFFERED)
    private val loadingRequestCount = AtomicInteger(0)
    private val mutableIsLoading = MutableStateFlow(false)

    internal val openEvents = openChannel.receiveAsFlow()
    internal val toastEvents = toastChannel.receiveAsFlow()
    internal val finishEvents = finishChannel.receiveAsFlow()
    val isLoading: StateFlow<Boolean> = mutableIsLoading.asStateFlow()

    /** 由页面基类负责展示 Toast。 */
    protected fun showToast(message: String): BaseStateViewModel {
        toastChannel.trySend(message)
        return this
    }

    /** 由页面基类通过 Core open 能力执行跳转并回传结果。 */
    protected fun open(
        activityClass: Class<*>,
        bundle: Bundle? = null,
        callback: (ActivityResult) -> Unit = {}
    ): BaseStateViewModel {
        openChannel.trySend(OpenRequest(activityClass, bundle, callback))
        return this
    }

    /** 由页面基类关闭当前页面；传入结果时同时回传 RESULT_OK。 */
    protected fun finish(result: Bundle? = null): BaseStateViewModel {
        finishChannel.trySend(FinishRequest(result))
        return this
    }

    protected fun beginLoading(): LoadingHandle {
        if (loadingRequestCount.incrementAndGet() == 1) {
            mutableIsLoading.value = true
        }
        return LoadingHandle(::endLoading)
    }

    protected suspend fun <T> withLoading(block: suspend () -> T): T {
        val handle = beginLoading()
        return try {
            block()
        } finally {
            handle.close()
        }
    }

    private fun endLoading() {
        while (true) {
            val currentCount = loadingRequestCount.get()
            if (currentCount == 0) return

            val nextCount = currentCount - 1
            if (loadingRequestCount.compareAndSet(currentCount, nextCount)) {
                if (nextCount == 0) {
                    mutableIsLoading.value = false
                }
                return
            }
        }
    }

    protected class LoadingHandle internal constructor(
        private val closeAction: () -> Unit
    ) : AutoCloseable {
        private val closed = AtomicBoolean(false)

        override fun close() {
            if (closed.compareAndSet(false, true)) {
                closeAction()
            }
        }
    }

    override fun onCleared() {
        openChannel.close()
        toastChannel.close()
        finishChannel.close()
        super.onCleared()
    }

    internal data class OpenRequest(
        val activityClass: Class<*>,
        val bundle: Bundle?,
        val callback: (ActivityResult) -> Unit
    )

    internal data class FinishRequest(val result: Bundle?)
}
