package com.easy.example.ui.adaptation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.easy.core.ui.compose.BaseComposeActivity
import com.easy.core.ui.compose.ComposeToolbarActionSpec
import com.easy.core.ui.compose.ComposeToolbarController
import com.easy.core.ui.compose.ComposeToolbarSpec
import com.easy.core.ui.compose.currentComposeDimens
import com.easy.core.ui.compose.xdp

class ComposeToolbarScrollActivity : BaseComposeActivity() {

    override val composeToolbarController = ComposeToolbarController(title = "Compose Toolbar Scroll")

    override fun providePageTitle(): CharSequence = "Compose Toolbar Scroll"

    override fun provideComposeToolbarSpec(): ComposeToolbarSpec {
        return ComposeToolbarSpec(
            title = providePageTitle(),
            controller = composeToolbarController,
            swapNavigationIconOnScroll = true,
            backgroundColor = com.easy.core.R.color.toolbar_bg_color,
            actionItems = listOf(
                ComposeToolbarActionSpec.Text("分享") {},
                ComposeToolbarActionSpec.Icon(com.easy.core.R.drawable.ic_more) {}
            )
        )
    }

    @Composable
    override fun InitComposeView() {
        val dimens = currentComposeDimens()
        val scrollState = rememberScrollState()
        val maxOffset = remember { 220f }
        LaunchedEffect(scrollState.value) {
            val progress = (scrollState.value / maxOffset).coerceIn(0f, 1f)
            composeToolbarController.setScrollProgress(progress)
            composeToolbarController.setTitle(
                if (progress > 0.65f) "已滚动到标题态"
                else "Compose Toolbar Scroll"
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F7FB))
                .verticalScroll(scrollState)
                .padding(bottom = 40.xdp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.xdp)
                    .background(Color(0xFF202F46))
                    .padding(horizontal = 40.xdp, vertical = 36.xdp)
            ) {
                Text(
                    text = "向上滚动，观察标题栏背景、标题和返回图标的变化。",
                    color = Color.White,
                    fontSize = dimens.text(36),
                    fontWeight = FontWeight.Bold
                )
            }

            repeat(12) { index ->
                DemoCard(
                    title = "Section ${index + 1}",
                    body = "这是一段滚动占位内容，用来观察 Compose 版 toolbar 的渐变和图标切换是否接近旧 DefToolBar 的行为。"
                )
            }
        }
    }

    @Composable
    private fun DemoCard(title: String, body: String) {
        val dimens = currentComposeDimens()
        Column(
            modifier = Modifier
                .padding(horizontal = 24.xdp, vertical = 12.xdp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(28.xdp))
                .background(Color.White)
                .padding(28.xdp),
            verticalArrangement = Arrangement.spacedBy(12.xdp)
        ) {
            Text(
                text = title,
                color = Color(0xFF18212F),
                fontSize = dimens.text(32),
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = body,
                color = Color(0xFF586578),
                fontSize = dimens.text(28)
            )
        }
    }
}
