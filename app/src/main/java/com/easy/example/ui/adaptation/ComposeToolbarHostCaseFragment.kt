package com.easy.example.ui.adaptation

import android.os.Bundle
import androidx.compose.runtime.Composable
import com.easy.core.annotation.LayoutModel
import com.easy.core.ui.compose.BaseComposeFragment

class ComposeToolbarHostCaseFragment : BaseComposeFragment() {

    private val layoutMode: Int
        @LayoutModel get() = arguments?.getInt(EXTRA_LAYOUT_MODE, LayoutModel.LAYOUT_MODE_LINEAR_LAYOUT)
            ?: LayoutModel.LAYOUT_MODE_LINEAR_LAYOUT

    override fun providePageTitle(): CharSequence = resolveCaseTitle(HOST_TYPE_FRAGMENT, layoutMode)

    override fun provideLayoutMode(): Int = layoutMode

    @Composable
    override fun initComposeView() {
        ComposeToolbarHostVerifyScreen(
            hostName = resolveHostLabel(HOST_TYPE_FRAGMENT),
            layoutModeName = resolveLayoutModeName(layoutMode),
            toolbarController = composeToolbarController
        )
    }

    companion object {
        fun newInstance(@LayoutModel layoutMode: Int): ComposeToolbarHostCaseFragment {
            return ComposeToolbarHostCaseFragment().apply {
                arguments = Bundle().apply {
                    putInt(EXTRA_LAYOUT_MODE, layoutMode)
                }
            }
        }
    }
}
