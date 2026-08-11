package com.easy.core.ui.base

import android.view.View
import androidx.viewbinding.ViewBinding
import org.junit.Assert.assertEquals
import org.junit.Test

class BaseViewBindingActivityTest {

    @Test
    fun resolvesBindingTypePassedThroughGenericParent() {
        assertEquals(TestBinding::class.java, resolveViewBindingClass(ConcreteActivity::class.java))
    }

    private abstract class GenericActivity<T : ViewBinding> : BaseViewBindingActivity<T>()

    private class ConcreteActivity : GenericActivity<TestBinding>() {
        override fun initView() = Unit
    }

    private class TestBinding : ViewBinding {
        override fun getRoot(): View = throw UnsupportedOperationException()
    }
}
