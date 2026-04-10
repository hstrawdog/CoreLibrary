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
                .padding(horizontal = 24.xdp, vertical = 20.xdp),
            verticalArrangement = Arrangement.spacedBy(16.xdp)
        ) {
            Text(
                text = "Compose runtime xdp",
                fontSize = dimens.text(32),
                fontWeight = FontWeight.Bold,
                color = Color(0xFF18212F)
            )

            Text(
                text = "designWidth=${dimens.designWidth.toInt()}, screenWidthDp=${dimens.screenWidthDp.toInt()}, scale=$scaleText",
                fontSize = dimens.text(15),
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
                .clip(RoundedCornerShape(18.xdp))
                .background(Color.White)
                .padding(24.xdp),
            verticalArrangement = Arrangement.spacedBy(14.xdp)
        ) {
            Text(
                text = "20.xdp / dimens.text(16)",
                fontSize = currentComposeDimens().text(20),
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF18212F)
            )

            Text(
                text = "当前基线改为 375dp。布局里的 20.xdp、文字里的 dimens.text(16) 都直接对应设计稿里的 dp 标注。",
                fontSize = currentComposeDimens().text(15),
                color = Color(0xFF5E6B7A)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.xdp)
                    .clip(RoundedCornerShape(16.xdp))
                    .background(Color(0xFF1E88E5))
                    .padding(20.xdp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.xdp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.xdp)
                            .clip(RoundedCornerShape(14.xdp))
                            .background(Color(0xFFFFFFFF))
                    )

                    Text(
                        text = "现在推荐直接按设计稿 dp 标注写：20.xdp、dimens.text(16)。如果设计图给的是 375dp 基线，数值可以直接照抄。",
                        fontSize = currentComposeDimens().text(16),
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
                .clip(RoundedCornerShape(18.xdp))
                .background(Color.White)
                .padding(24.xdp),
            verticalArrangement = Arrangement.spacedBy(12.xdp)
        ) {
            Text(
                text = "375dp mapping",
                fontSize = currentComposeDimens().text(20),
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF18212F)
            )

            MetricRow(label = "x20", value = "20.xdp")
            MetricRow(label = "x24", value = "24.xdp")
            MetricRow(label = "s14", value = "dimens.text(14)")
            MetricRow(label = "s18", value = "dimens.text(18)")
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
                fontSize = dimens.text(15),
                color = Color(0xFF5E6B7A)
            )
            Spacer(modifier = Modifier.width(10.xdp))
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(500.xdp))
                    .background(Color(0xFFE9F2FF))
                    .padding(horizontal = 12.xdp, vertical = 6.xdp)
            ) {
                Text(
                    text = value,
                    fontSize = dimens.text(14),
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
                .clip(RoundedCornerShape(18.xdp))
                .background(Color(0xFF18212F))
                .padding(24.xdp),
            verticalArrangement = Arrangement.spacedBy(10.xdp)
        ) {
            val dimens = currentComposeDimens()
            Text(
                text = "Usage",
                fontSize = dimens.text(20),
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )

            Text(
                text = "CoreConfig.composeDimensSpec / page override",
                fontSize = dimens.text(14),
                color = Color(0xFFDCE6F5)
            )

            Text(
                text = "Modifier.padding(16.xdp)",
                fontSize = dimens.text(14),
                color = Color(0xFFDCE6F5)
            )

            Text(
                text = "Text(fontSize = dimens.text(15))",
                fontSize = dimens.text(14),
                color = Color(0xFFDCE6F5)
            )
        }
    }
}
