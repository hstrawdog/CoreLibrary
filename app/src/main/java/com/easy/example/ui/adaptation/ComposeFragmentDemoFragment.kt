package com.easy.example.ui.adaptation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.easy.core.ui.compose.BaseComposeFragment
import com.easy.core.ui.compose.currentComposeDimens
import com.easy.core.ui.compose.xdp

class ComposeFragmentDemoFragment : BaseComposeFragment() {


    @Composable
    override fun initComposeView() {
        val dimens = currentComposeDimens()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F7FB))
                .verticalScroll(rememberScrollState())
                .padding(24.xdp),
            verticalArrangement = Arrangement.spacedBy(16.xdp)
        ) {
            FragmentCard(
                title = "Fragment 默认能力",
                body = "这个示例只重写标题和页面内容，没有重写 toolbar spec，也没有重新创建 toolbarController。"
            )
            FragmentCard(
                title = "当前验证点",
                body = "BaseComposeFragment 默认提供了 ComposeToolbar 和 composeToolbarController，业务页大多数情况下只需要关注内容。"
            )
            Button(
                onClick = {
                    composeToolbarController.setTitle("Fragment 标题已更新")
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF1E88E5)),
                shape = RoundedCornerShape(18.xdp)
            ) {
                Text(
                    text = "修改 Fragment 标题",
                    color = Color.White,
                    fontSize = dimens.text(24)
                )
            }
        }
    }

    @Composable
    private fun FragmentCard(title: String, body: String) {
        val dimens = currentComposeDimens()
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(24.xdp))
                .padding(24.xdp),
            verticalArrangement = Arrangement.spacedBy(10.xdp)
        ) {
            Text(
                text = title,
                color = Color(0xFF18212F),
                fontSize = dimens.text(32),
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = body,
                color = Color(0xFF5C6878),
                fontSize = dimens.text(28)
            )
        }
    }
}
