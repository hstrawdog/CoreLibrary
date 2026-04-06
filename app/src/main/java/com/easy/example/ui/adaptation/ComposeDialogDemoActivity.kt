package com.easy.example.ui.adaptation

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
import com.easy.core.ui.compose.BaseComposeActivity
import com.easy.core.ui.compose.currentComposeDimens
import com.easy.core.ui.compose.xdp

class ComposeDialogDemoActivity : BaseComposeActivity() {

    override fun providePageTitle(): CharSequence = "Compose Dialog Demo"

    @Composable
    override fun InitComposeView() {
        val dimens = currentComposeDimens()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F7FB))
                .padding(24.xdp),
            verticalArrangement = Arrangement.spacedBy(16.xdp)
        ) {
            Text(
                text = "这个页面只负责弹出 ComposeDialog，dialog 本身也尽量只关注内容。",
                color = Color(0xFF18212F),
                fontSize = dimens.text(30),
                fontWeight = FontWeight.SemiBold
            )
            Button(
                onClick = {
                    ComposeSimpleDialog().show(supportFragmentManager)
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF1E88E5))
            ) {
                Text(
                    text = "打开 Compose Dialog",
                    color = Color.White,
                    fontSize = dimens.text(24)
                )
            }
        }
    }
}
