package com.easy.example.ui.adaptation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.easy.core.ui.compose.ComposeToolbarController
import com.easy.core.ui.compose.currentComposeDimens
import com.easy.core.ui.compose.xdp

@Composable
internal fun ComposeToolbarHostVerifyScreen(
    hostName: String,
    layoutModeName: String,
    toolbarController: ComposeToolbarController,
    onClose: (() -> Unit)? = null
) {
    val dimens = currentComposeDimens()
    val title = toolbarController.titleState.value?.toString().orEmpty()
    val showStatusBar = toolbarController.showStatusBarState.value ?: true
    val showToolbar = toolbarController.showToolbarState.value ?: true
    val showBottomLine = toolbarController.showBottomLineState.value ?: true

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F7FB))
            .verticalScroll(rememberScrollState())
            .padding(12.xdp),
        verticalArrangement = Arrangement.spacedBy(8.xdp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.xdp)
                .background(Color(0xFF0F766E), RoundedCornerShape(16.xdp))
                .padding(16.xdp),
            contentAlignment = Alignment.BottomStart
        ) {
            Text(
                text = "$hostName / $layoutModeName",
                color = Color.White,
                fontSize = dimens.text(18),
                fontWeight = FontWeight.Bold
            )
        }
        VerifyCard(
            title = "$hostName 验证",
            body = "验证 ComposeToolbarController 在 $hostName 宿主下对标题栏、状态栏背景、横线的统一控制。当前根布局=$layoutModeName。"
        )
        VerifyCard(
            title = "当前状态",
            body = buildString {
                append("layoutMode=")
                append(layoutModeName)
                append("\n")
                append("title=")
                append(title.ifEmpty { "空" })
                append("\nshowStatusBar=")
                append(showStatusBar)
                append("\nshowToolbar=")
                append(showToolbar)
                append("\nshowBottomLine=")
                append(showBottomLine)
            }
        )
        VerifyActionRow(
            title = "快速切换",
            actions = listOf(
                "默认态" to {
                    toolbarController.setTitle("$hostName Toolbar")
                    toolbarController.setShowStatusBar(null)
                    toolbarController.setShowToolbar(null)
                    toolbarController.setShowBottomLine(null)
                    toolbarController.setStatusBarBackgroundColor(null)
                },
                "改标题" to {
                    toolbarController.setShowToolbar(true)
                    toolbarController.setTitle("$hostName 标题已更新")
                },
                "全显示" to {
                    toolbarController.setShowStatusBar(true)
                    toolbarController.setShowToolbar(true)
                    toolbarController.setShowBottomLine(true)
                    toolbarController.setStatusBarBackgroundColor(null)
                },
                "全隐藏" to {
                    toolbarController.setShowStatusBar(false)
                    toolbarController.setShowToolbar(false)
                    toolbarController.setShowBottomLine(false)
                }
            )
        )
        VerifyActionRow(
            title = "单项控制",
            actions = listOf(
                "隐藏状态栏" to {
                    toolbarController.setShowStatusBar(false)
                },
                "显示状态栏" to {
                    toolbarController.setShowStatusBar(true)
                },
                "隐藏标题栏" to {
                    toolbarController.setShowToolbar(false)
                },
                "显示标题栏" to {
                    toolbarController.setShowToolbar(true)
                },
                "状态栏透明" to {
                    toolbarController.setShowStatusBar(true)
                    toolbarController.setStatusBarBackgroundColor(com.easy.core.R.color.transparent)
                },
                "状态栏跟随" to {
                    toolbarController.setStatusBarBackgroundColor(null)
                },
                "隐藏横线" to {
                    toolbarController.setShowBottomLine(false)
                },
                "显示横线" to {
                    toolbarController.setShowBottomLine(true)
                }
            )
        )
        onClose?.let { close ->
            VerifyActionRow(
                title = "其他",
                actions = listOf(
                    "关闭" to close
                )
            )
        }
        OutlinedButton(
            onClick = {},
            enabled = false,
            border = BorderStroke(1.xdp, Color(0xFF0F766E)),
            shape = RoundedCornerShape(10.xdp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = if (layoutModeName == "FrameLayout") {
                    "观察点：头部悬浮在内容上方，顶部内容会进入标题栏区域"
                } else {
                    "观察点：头部在内容上方占位，内容从标题栏下方开始"
                },
                color = Color(0xFF0F766E),
                fontSize = dimens.text(13)
            )
        }
        repeat(6) { index ->
            VerifyCard(
                title = "$hostName 内容 ${index + 1}",
                body = "保留滚动内容，方便观察标题栏隐藏后页面顶部分层是否正确。"
            )
        }
    }
}

@Composable
private fun VerifyCard(title: String, body: String) {
    val dimens = currentComposeDimens()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(12.xdp))
            .padding(12.xdp),
        verticalArrangement = Arrangement.spacedBy(5.xdp)
    ) {
        Text(
            text = title,
            color = Color(0xFF18212F),
            fontSize = dimens.text(17),
            fontWeight = FontWeight.Bold
        )
        Text(
            text = body,
            color = Color(0xFF5C6878),
            fontSize = dimens.text(14)
        )
    }
}

@Composable
private fun VerifyActionRow(
    title: String,
    actions: List<Pair<String, () -> Unit>>
) {
    val dimens = currentComposeDimens()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(12.xdp))
            .padding(12.xdp),
        verticalArrangement = Arrangement.spacedBy(6.xdp)
    ) {
        Text(
            text = title,
            color = Color(0xFF18212F),
            fontSize = dimens.text(15),
            fontWeight = FontWeight.SemiBold
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(6.xdp)
        ) {
            actions.forEach { (label, action) ->
                Button(
                    onClick = action,
                    shape = RoundedCornerShape(9.xdp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF1E88E5))
                ) {
                    Text(
                        text = label,
                        color = Color.White,
                        fontSize = dimens.text(12)
                    )
                }
            }
        }
    }
}
