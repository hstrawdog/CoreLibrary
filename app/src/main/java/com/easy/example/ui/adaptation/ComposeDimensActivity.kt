package com.easy.example.ui.adaptation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.easy.core.ui.compose.BaseComposeActivity
import com.easy.core.ui.compose.currentComposeDimens
import com.easy.core.ui.compose.xdp
import java.util.Locale

class ComposeDimensActivity : BaseComposeActivity() {

    override fun providePageTitle(): CharSequence = "Compose 屏宽适配"

    @Composable
    override fun InitComposeView() {
        val dimens = currentComposeDimens()
        val scaleText = String.format(Locale.US, "%.3f", dimens.scale)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF4F6FB))
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 48.xdp, vertical = 40.xdp),
            verticalArrangement = Arrangement.spacedBy(32.xdp)
        ) {
            Text(
                text = "Compose runtime xdp",
                fontSize = dimens.text(64),
                fontWeight = FontWeight.Bold,
                color = Color(0xFF18212F)
            )

            Text(
                text = "designWidth=${dimens.designWidth.toInt()}, screenWidthDp=${dimens.screenWidthDp.toInt()}, scale=$scaleText",
                fontSize = dimens.text(30),
                color = Color(0xFF5E6B7A)
            )

            PreviewCard()
            MetricCard()
            UsageCard()
        }
    }

    @Composable
    private fun PreviewCard() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(36.xdp))
                .background(Color.White)
                .padding(48.xdp),
            verticalArrangement = Arrangement.spacedBy(28.xdp)
        ) {
            Text(
                text = "20.xdp / dimens.text(32)",
                fontSize = currentComposeDimens().text(40),
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF18212F)
            )

            Text(
                text = "这套写法保留了旧工具最核心的换算方式: 当前屏宽dp / 设计稿宽度。布局统一用 xdp，文字也走同一套比例。",
                fontSize = currentComposeDimens().text(30),
                color = Color(0xFF5E6B7A)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(320.xdp)
                    .clip(RoundedCornerShape(32.xdp))
                    .background(Color(0xFF1E88E5))
                    .padding(40.xdp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(24.xdp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(96.xdp)
                            .clip(RoundedCornerShape(28.xdp))
                            .background(Color(0xFFFFFFFF))
                    )

                    Text(
                        text = "旧 XML 里的 @dimen/x40、@dimen/s32，迁到 Compose 后可以直接写成 40.xdp、dimens.text(32)。",
                        fontSize = currentComposeDimens().text(32),
                        color = Color.White,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }

    @Composable
    private fun MetricCard() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(36.xdp))
                .background(Color.White)
                .padding(48.xdp),
            verticalArrangement = Arrangement.spacedBy(24.xdp)
        ) {
            Text(
                text = "Legacy mapping",
                fontSize = currentComposeDimens().text(40),
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF18212F)
            )

            MetricRow(label = "x20", value = "20.xdp")
            MetricRow(label = "x48", value = "48.xdp")
            MetricRow(label = "s28", value = "dimens.text(28)")
            MetricRow(label = "s36", value = "dimens.text(36)")
        }
    }

    @Composable
    private fun MetricRow(label: String, value: String) {
        val dimens = currentComposeDimens()
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                fontSize = dimens.text(30),
                color = Color(0xFF5E6B7A)
            )
            Spacer(modifier = Modifier.width(20.xdp))
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(999.xdp))
                    .background(Color(0xFFE9F2FF))
                    .padding(horizontal = 24.xdp, vertical = 12.xdp)
            ) {
                Text(
                    text = value,
                    fontSize = dimens.text(28),
                    color = Color(0xFF1E88E5),
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }

    @Composable
    private fun UsageCard() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(36.xdp))
                .background(Color(0xFF18212F))
                .padding(48.xdp),
            verticalArrangement = Arrangement.spacedBy(20.xdp)
        ) {
            val dimens = currentComposeDimens()
            Text(
                text = "Usage",
                fontSize = dimens.text(40),
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )

            Text(
                text = "CoreConfig.composeDimensSpec / page override",
                fontSize = dimens.text(28),
                color = Color(0xFFDCE6F5)
            )

            Text(
                text = "Modifier.padding(32.xdp)",
                fontSize = dimens.text(28),
                color = Color(0xFFDCE6F5)
            )

            Text(
                text = "Text(fontSize = dimens.text(30))",
                fontSize = dimens.text(28),
                color = Color(0xFFDCE6F5)
            )
        }
    }
}
