package com.easy.core.ui.base

import android.app.Activity
import androidx.activity.result.ActivityResult
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class BaseStateViewModelTest {
    @Test
    fun openCanReturnResultToConcreteViewModel() = runBlocking {
        val viewModel = TestViewModel()

        viewModel.openPage()
        val request = viewModel.openEvents.first()
        request.callback(ActivityResult(Activity.RESULT_OK, null))

        assertEquals(TestActivity::class.java, request.activityClass)
        assertEquals(Activity.RESULT_OK, viewModel.lastResultCode)
    }

    @Test
    fun toastCanBeSentByConcreteViewModel() = runBlocking {
        val viewModel = TestViewModel()

        viewModel.toast("操作成功")

        assertEquals("操作成功", viewModel.toastEvents.first())
    }

    @Test
    fun loadingRemainsVisibleUntilAllRequestsFinish() {
        val viewModel = TestViewModel()

        val firstRequest = viewModel.acquireLoading()
        val secondRequest = viewModel.acquireLoading()
        assertTrue(viewModel.isLoading.value)

        firstRequest.close()
        assertTrue(viewModel.isLoading.value)

        secondRequest.close()
        assertFalse(viewModel.isLoading.value)
    }

    @Test
    fun loadingHandleCanOnlyReleaseOnce() {
        val viewModel = TestViewModel()
        val firstRequest = viewModel.acquireLoading()
        val secondRequest = viewModel.acquireLoading()

        firstRequest.close()
        firstRequest.close()
        assertTrue(viewModel.isLoading.value)

        secondRequest.close()
        assertFalse(viewModel.isLoading.value)
    }

    @Test
    fun withLoadingReleasesAfterFailure() = runBlocking {
        val viewModel = TestViewModel()

        runCatching { viewModel.failWhileLoading() }

        assertFalse(viewModel.isLoading.value)
    }

    private class TestViewModel : BaseStateViewModel() {
        var lastResultCode: Int? = null

        fun openPage() {
            open(TestActivity::class.java) { result ->
                lastResultCode = result.resultCode
            }
        }

        fun toast(message: String) = showToast(message)

        fun acquireLoading(): AutoCloseable = beginLoading()

        suspend fun failWhileLoading() {
            withLoading<Unit> {
                throw IllegalStateException("failed")
            }
        }
    }

    private class TestActivity : Activity()
}
