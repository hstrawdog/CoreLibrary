package com.easy.example.ui.adaptation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.easy.core.R
import com.easy.core.ui.compose.BaseComposeDialog
import com.easy.core.ui.compose.currentComposeDimens
import com.easy.core.ui.compose.xdp

class ComposeSimpleDialog : BaseComposeDialog() {

    override fun providePageTitle(): CharSequence = "Compose Dialog"

    override fun provideBackgroundColor(): Int = R.color.white

    @Composable
    override fun initComposeView() {
        val dimens = currentComposeDimens()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F7FB))
                .padding(24.xdp),
            verticalArrangement = Arrangement.spacedBy(16.xdp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(28.xdp))
                    .padding(28.xdp),
                verticalArrangement = Arrangement.spacedBy(16.xdp)
            ) {
                Text(
                    text = "全屏 Dialog 默认能力",
                    color = Color(0xFF18212F),
                    fontSize = dimens.text(34),
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "这个 full-screen dialog 现在按 Activity 一样走内容头部：状态栏背景占位 + toolbar 都由页面根布局负责，window 本身不再单独改状态栏。",
                    color = Color(0xFF5C6878),
                    fontSize = dimens.text(28)
                )
                Button(
                    onClick = {
                        composeToolbarController.setTitle("Dialog 标题已更新")
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF1E88E5))
                ) {
                    Text(
                        text = "修改 Dialog 标题",
                        color = Color.White,
                        fontSize = dimens.text(24)
                    )
                }
                Button(
                    onClick = { dismiss() },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF374151))
                ) {
                    Text(
                        text = "关闭",
                        color = Color.White,
                        fontSize = dimens.text(24)
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(28.xdp))
                    .padding(28.xdp),
                verticalArrangement = Arrangement.spacedBy(12.xdp)
            ) {
                repeat(6) { index ->
                    Text(
                        text = "Dialog 内容块 ${index + 1}",
                        color = Color(0xFF18212F),
                        fontSize = dimens.text(28)
                    )
                }
            }
        }
    }
}
