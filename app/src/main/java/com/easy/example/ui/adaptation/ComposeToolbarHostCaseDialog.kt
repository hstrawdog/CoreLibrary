package com.easy.example.ui.adaptation

import android.os.Bundle
import androidx.compose.runtime.Composable
import com.easy.core.R
import com.easy.core.annotation.LayoutModel
import com.easy.core.ui.compose.BaseComposeDialog

class ComposeToolbarHostCaseDialog : BaseComposeDialog() {

    private val layoutMode: Int
        @LayoutModel get() = arguments?.getInt(EXTRA_LAYOUT_MODE, LayoutModel.LAYOUT_MODE_LINEAR_LAYOUT)
            ?: LayoutModel.LAYOUT_MODE_LINEAR_LAYOUT

    override fun providePageTitle(): CharSequence = resolveCaseTitle(HOST_TYPE_DIALOG, layoutMode)

    override fun provideLayoutMode(): Int = layoutMode

    override fun provideBackgroundColor(): Int = R.color.white

    @Composable
    override fun initComposeView() {
        ComposeToolbarHostVerifyScreen(
            hostName = resolveHostLabel(HOST_TYPE_DIALOG),
            layoutModeName = resolveLayoutModeName(layoutMode),
            toolbarController = composeToolbarController,
            onClose = ::dismiss
        )
    }

    companion object {
        fun newInstance(@LayoutModel layoutMode: Int): ComposeToolbarHostCaseDialog {
            return ComposeToolbarHostCaseDialog().apply {
                arguments = Bundle().apply {
                    putInt(EXTRA_LAYOUT_MODE, layoutMode)
                }
            }
        }
    }
}
