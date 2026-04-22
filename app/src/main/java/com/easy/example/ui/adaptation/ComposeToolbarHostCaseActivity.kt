package com.easy.example.ui.adaptation

import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.easy.core.annotation.LayoutModel
import com.easy.core.ui.compose.BaseComposeActivity
import com.easy.core.ui.compose.ContentSpec
import com.easy.core.ui.compose.currentComposeDimens
import com.easy.core.ui.compose.xdp

internal const val HOST_TYPE_ACTIVITY = "activity"
internal const val HOST_TYPE_FRAGMENT = "fragment"
internal const val HOST_TYPE_DIALOG = "dialog"

internal const val EXTRA_HOST_TYPE = "compose_toolbar_host_type"
internal const val EXTRA_LAYOUT_MODE = "compose_toolbar_layout_mode"

internal fun resolveHostLabel(hostType: String): String {
    return when (hostType) {
        HOST_TYPE_FRAGMENT -> "Fragment"
        HOST_TYPE_DIALOG -> "Dialog"
        else -> "Activity"
    }
}

internal fun resolveLayoutModeName(@LayoutModel layoutMode: Int): String {
    return if (layoutMode == LayoutModel.LAYOUT_MODE_FRAME_LAYOUT) {
        "FrameLayout"
    } else {
        "LinearLayout"
    }
}

internal fun resolveCaseTitle(hostType: String, @LayoutModel layoutMode: Int): String {
    val hostLabel = resolveHostLabel(hostType)
    val layoutModeName = resolveLayoutModeName(layoutMode)
    return "$hostLabel Toolbar $layoutModeName 验证"
}

class ComposeToolbarHostCaseActivity : BaseComposeActivity() {

    private val fragmentContainerId = View.generateViewId()

    private val hostType: String
        get() = intent?.getStringExtra(EXTRA_HOST_TYPE) ?: HOST_TYPE_ACTIVITY

    private val layoutMode: Int
        @LayoutModel get() = intent?.getIntExtra(EXTRA_LAYOUT_MODE, LayoutModel.LAYOUT_MODE_LINEAR_LAYOUT)
            ?: LayoutModel.LAYOUT_MODE_LINEAR_LAYOUT

    override fun providePageTitle(): CharSequence = resolveCaseTitle(hostType, layoutMode)

    override fun provideLayoutMode(): Int {
        return if (hostType == HOST_TYPE_ACTIVITY) layoutMode else LayoutModel.LAYOUT_MODE_LINEAR_LAYOUT
    }

    override fun provideShowStatusBar(): Boolean = hostType != HOST_TYPE_FRAGMENT

    override fun provideShowToolbar(): Boolean = hostType != HOST_TYPE_FRAGMENT

    override fun provideContentSpec(): ContentSpec {
        return if (hostType == HOST_TYPE_FRAGMENT) {
            ContentSpec.ViewFactory {
                FrameLayout(this).apply {
                    id = fragmentContainerId
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                }
            }
        } else {
            super.provideContentSpec()
        }
    }

    override fun initView() {
        if (hostType != HOST_TYPE_FRAGMENT) {
            return
        }
        if (supportFragmentManager.findFragmentById(fragmentContainerId) == null) {
            supportFragmentManager.beginTransaction()
                .replace(fragmentContainerId, ComposeToolbarHostCaseFragment.newInstance(layoutMode))
                .commit()
        }
    }

    @Composable
    override fun InitComposeView() {
        when (hostType) {
            HOST_TYPE_DIALOG -> DialogLauncherContent()
            else -> ComposeToolbarHostVerifyScreen(
                hostName = resolveHostLabel(hostType),
                layoutModeName = resolveLayoutModeName(layoutMode),
                toolbarController = composeToolbarController
            )
        }
    }

    @Composable
    private fun DialogLauncherContent() {
        val dimens = currentComposeDimens()
        val layoutModeName = resolveLayoutModeName(layoutMode)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F7FB))
                .padding(24.xdp),
            verticalArrangement = Arrangement.spacedBy(16.xdp)
        ) {
            Text(
                text = "这里验证 BaseComposeDialog 在 $layoutModeName 下的标题栏层级与控制行为。",
                color = Color(0xFF18212F),
                fontSize = dimens.text(18),
                fontWeight = FontWeight.SemiBold
            )
            Button(
                onClick = {
                    ComposeToolbarHostCaseDialog.newInstance(layoutMode)
                        .show(supportFragmentManager)
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF1E88E5))
            ) {
                Text(
                    text = "打开 Dialog 验证页",
                    color = Color.White,
                    fontSize = dimens.text(14)
                )
            }
        }
    }

    companion object {
        fun intent(
            context: Context,
            hostType: String,
            @LayoutModel layoutMode: Int
        ): Intent {
            return Intent(context, ComposeToolbarHostCaseActivity::class.java).apply {
                putExtra(EXTRA_HOST_TYPE, hostType)
                putExtra(EXTRA_LAYOUT_MODE, layoutMode)
            }
        }
    }
}
