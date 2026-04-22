package com.easy.core.ui.compose.refresh

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.easy.core.ui.compose.currentComposeDimens
import com.easy.core.ui.compose.xdp
import kotlin.math.roundToInt

@Composable
fun SmartSwipeRefreshDefaultHeader(
    state: SmartSwipeRefreshState,
    modifier: Modifier = Modifier,
    idleText: String = "下拉可以刷新",
    releaseText: String = "释放立即刷新",
    refreshingText: String = "正在刷新...",
    successText: String = "刷新成功",
    errorText: String = "刷新失败"
) {
    val progress = state.refreshProgress()
    val refreshFlag = state.refreshFlag
    val accentTarget = when (refreshFlag) {
        SmartSwipeStateFlag.SUCCESS -> Color(0xFF16A34A)
        SmartSwipeStateFlag.ERROR -> Color(0xFFE14D4D)
        else -> Color(0xFF1E6EEB)
    }
    val accentColor by animateColorAsState(accentTarget, label = "refresh_accent")
    val arrowRotation by animateFloatAsState(
        targetValue = if (refreshFlag == SmartSwipeStateFlag.TIPS_RELEASE) 180f else 0f,
        label = "refresh_arrow_rotation"
    )
    val indicatorScale by animateFloatAsState(
        targetValue = if (refreshFlag == SmartSwipeStateFlag.REFRESHING) 1f else 0.9f + progress * 0.1f,
        label = "refresh_indicator_scale"
    )
    val title = when (refreshFlag) {
        SmartSwipeStateFlag.REFRESHING -> refreshingText
        SmartSwipeStateFlag.SUCCESS -> successText
        SmartSwipeStateFlag.ERROR -> errorText
        SmartSwipeStateFlag.TIPS_RELEASE -> releaseText
        else -> idleText
    }
    val subtitle = when (refreshFlag) {
        SmartSwipeStateFlag.REFRESHING -> "列表内容更新中"
        SmartSwipeStateFlag.SUCCESS -> "数据已完成刷新"
        SmartSwipeStateFlag.ERROR -> "请稍后重试"
        SmartSwipeStateFlag.TIPS_RELEASE -> "松手后立即开始刷新"
        else -> {
            val percent = (progress * 100).roundToInt().coerceIn(0, 100)
            if (percent > 0) "继续下拉 $percent%" else "继续下拉即可触发"
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 8.xdp, bottom = 10.xdp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(18.xdp))
                .background(Color.White.copy(alpha = 0.96f))
                .padding(horizontal = 16.xdp, vertical = 10.xdp),
            horizontalArrangement = Arrangement.spacedBy(12.xdp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .graphicsLayer {
                        scaleX = indicatorScale
                        scaleY = indicatorScale
                    }
                    .size(30.xdp)
                    .clip(CircleShape)
                    .background(accentColor.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                when (refreshFlag) {
                    SmartSwipeStateFlag.REFRESHING -> {
                        CircularProgressIndicator(
                            modifier = Modifier.size(16.xdp),
                            color = accentColor,
                            strokeWidth = 2.dp
                        )
                    }

                    SmartSwipeStateFlag.SUCCESS -> {
                        RefreshCheckMark(color = accentColor)
                    }

                    SmartSwipeStateFlag.ERROR -> {
                        RefreshErrorMark(color = accentColor)
                    }

                    else -> {
                        RefreshArrow(
                            progress = progress,
                            color = accentColor,
                            modifier = Modifier
                                .size(16.xdp)
                                .graphicsLayer { rotationZ = arrowRotation }
                        )
                    }
                }
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(2.xdp)
            ) {
                Text(
                    text = title,
                    fontSize = currentComposeDimens().text(13),
                    color = Color(0xFF18212F)
                )
                Text(
                    text = subtitle,
                    fontSize = currentComposeDimens().text(11),
                    color = Color(0xFF8A94A6)
                )
            }
        }
    }
}

fun SmartSwipeRefreshState.refreshProgress(): Float {
    if (headerHeight <= 0f) {
        return 0f
    }
    return (indicatorOffset / headerHeight).coerceIn(0f, 1f)
}

@Composable
private fun RefreshArrow(
    progress: Float,
    color: Color,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val strokeWidth = size.minDimension * 0.12f
        drawArc(
            color = color.copy(alpha = 0.22f),
            startAngle = -90f,
            sweepAngle = progress * 320f,
            useCenter = false,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
        )
        drawLine(
            color = color,
            start = center.copy(y = size.height * 0.2f),
            end = center.copy(y = size.height * 0.68f),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )
        drawLine(
            color = color,
            start = center.copy(y = size.height * 0.68f),
            end = androidx.compose.ui.geometry.Offset(size.width * 0.28f, size.height * 0.48f),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )
        drawLine(
            color = color,
            start = center.copy(y = size.height * 0.68f),
            end = androidx.compose.ui.geometry.Offset(size.width * 0.72f, size.height * 0.48f),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )
    }
}

@Composable
private fun RefreshCheckMark(
    color: Color,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.size(16.xdp)) {
        val strokeWidth = size.minDimension * 0.13f
        drawLine(
            color = color,
            start = androidx.compose.ui.geometry.Offset(size.width * 0.2f, size.height * 0.54f),
            end = androidx.compose.ui.geometry.Offset(size.width * 0.43f, size.height * 0.76f),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )
        drawLine(
            color = color,
            start = androidx.compose.ui.geometry.Offset(size.width * 0.43f, size.height * 0.76f),
            end = androidx.compose.ui.geometry.Offset(size.width * 0.8f, size.height * 0.28f),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )
    }
}

@Composable
private fun RefreshErrorMark(
    color: Color,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.size(16.xdp)) {
        val strokeWidth = size.minDimension * 0.13f
        drawCircle(
            color = color.copy(alpha = 0.16f),
            radius = size.minDimension / 2f
        )
        drawLine(
            color = color,
            start = androidx.compose.ui.geometry.Offset(size.width * 0.32f, size.height * 0.2f),
            end = androidx.compose.ui.geometry.Offset(size.width * 0.32f, size.height * 0.6f),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )
        drawLine(
            color = color,
            start = androidx.compose.ui.geometry.Offset(size.width * 0.32f, size.height * 0.75f),
            end = androidx.compose.ui.geometry.Offset(size.width * 0.32f, size.height * 0.8f),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )
    }
}
