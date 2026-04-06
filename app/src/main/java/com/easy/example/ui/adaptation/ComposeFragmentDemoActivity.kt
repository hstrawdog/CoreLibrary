package com.easy.example.ui.adaptation

import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.runtime.Composable
import com.easy.core.ui.compose.BaseComposeActivity
import com.easy.core.ui.compose.ContentSpec

class ComposeFragmentDemoActivity : BaseComposeActivity() {

    private val fragmentContainerId = View.generateViewId()

    override fun provideShowStatusBar(): Boolean = false

    override fun provideShowToolbar(): Boolean = false


    override fun provideContentSpec(): ContentSpec {
        return ContentSpec.ViewFactory {
            FrameLayout(this).apply {
                id = fragmentContainerId
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            }
        }
    }

    override fun initView() {
        if (supportFragmentManager.findFragmentById(fragmentContainerId) == null) {
            supportFragmentManager.beginTransaction()
                .replace(fragmentContainerId, ComposeFragmentDemoFragment())
                .commit()
        }
    }

    @Composable
    override fun InitComposeView() {}
}
