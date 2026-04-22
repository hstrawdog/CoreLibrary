package com.easy.example.ui.adaptation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.easy.core.annotation.LayoutModel
import com.easy.core.ui.compose.BaseComposeActivity
import com.easy.core.ui.compose.currentComposeDimens
import com.easy.core.ui.compose.xdp

class ComposeToolbarHostCatalogActivity : BaseComposeActivity() {

    override fun providePageTitle(): CharSequence = "Compose Toolbar 宿主验证"

    @Composable
    override fun InitComposeView() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F7FB))
                .verticalScroll(rememberScrollState())
                .padding(12.xdp),
            verticalArrangement = Arrangement.spacedBy(10.xdp)
        ) {
            CatalogIntro()
            HostGroup(
                title = "Activity",
                summary = "直接验证 Activity 自身作为 Compose 页面宿主时，线性布局与帧布局下 toolbar 的装配效果。",
                hostType = HOST_TYPE_ACTIVITY
            )
            HostGroup(
                title = "Fragment",
                summary = "Activity 只提供容器，真正的 toolbar 由 BaseComposeFragment 路径渲染，观察宿主切换后行为是否一致。",
                hostType = HOST_TYPE_FRAGMENT
            )
            HostGroup(
                title = "Dialog",
                summary = "先进入宿主页，再弹出 BaseComposeDialog，验证对话框窗口下 toolbar 的显隐、层级与关闭链路。",
                hostType = HOST_TYPE_DIALOG
            )
        }
    }

    @Composable
    private fun CatalogIntro() {
        val dimens = currentComposeDimens()
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(14.xdp))
                .padding(14.xdp),
            verticalArrangement = Arrangement.spacedBy(6.xdp)
        ) {
            Text(
                text = "宿主验证入口已收敛",
                color = Color(0xFF18212F),
                fontSize = dimens.text(18),
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "这里统一覆盖 Activity / Fragment / Dialog 三类宿主，并且每类都能分别验证 LinearLayout 与 FrameLayout。",
                color = Color(0xFF5C6878),
                fontSize = dimens.text(14)
            )
        }
    }

    @Composable
    private fun HostGroup(
        title: String,
        summary: String,
        hostType: String
    ) {
        val dimens = currentComposeDimens()
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(14.xdp))
                .padding(14.xdp),
            verticalArrangement = Arrangement.spacedBy(8.xdp)
        ) {
            Text(
                text = title,
                color = Color(0xFF18212F),
                fontSize = dimens.text(17),
                fontWeight = FontWeight.Bold
            )
            Text(
                text = summary,
                color = Color(0xFF5C6878),
                fontSize = dimens.text(14)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.xdp)
            ) {
                HostCaseCard(
                    label = "LinearLayout",
                    modifier = Modifier.weight(1f)
                ) {
                    startActivity(
                        ComposeToolbarHostCaseActivity.intent(
                            context = this@ComposeToolbarHostCatalogActivity,
                            hostType = hostType,
                            layoutMode = LayoutModel.LAYOUT_MODE_LINEAR_LAYOUT
                        )
                    )
                }
                HostCaseCard(
                    label = "FrameLayout",
                    modifier = Modifier.weight(1f)
                ) {
                    startActivity(
                        ComposeToolbarHostCaseActivity.intent(
                            context = this@ComposeToolbarHostCatalogActivity,
                            hostType = hostType,
                            layoutMode = LayoutModel.LAYOUT_MODE_FRAME_LAYOUT
                        )
                    )
                }
            }
        }
    }

    @Composable
    private fun HostCaseCard(
        label: String,
        modifier: Modifier = Modifier,
        onClick: () -> Unit
    ) {
        val dimens = currentComposeDimens()
        Column(
            modifier = modifier
                .background(Color(0xFFEAF2FF), RoundedCornerShape(12.xdp))
                .clickable(onClick = onClick)
                .padding(horizontal = 12.xdp, vertical = 14.xdp),
            verticalArrangement = Arrangement.spacedBy(4.xdp)
        ) {
            Text(
                text = label,
                color = Color(0xFF1E6EEB),
                fontSize = dimens.text(15),
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "打开验证页",
                color = Color(0xFF5C6878),
                fontSize = dimens.text(12)
            )
        }
    }
}
