package com.easy.core.ui.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.max

/**
 * Runtime dimens adapter for Compose.
 *
 * The designWidth keeps the same meaning as the old generator's "base width":
 * if your design draft is 1080 wide, pass 1080 here.
 */
data class ComposeDimensSpec(
    val enabled: Boolean = true,
    val designWidth: Int = 750,
    val minValue: Float = 0.5f
)

/**
 * Runtime dimens adapter for Compose.
 *
 * The designWidth keeps the same meaning as the old generator's "base width":
 * if your design draft is 1080 wide, pass 1080 here.
 */
@Immutable
data class ComposeDimens(
    val designWidth: Float,
    val screenWidthDp: Float,
    val scale: Float,
    val minValue: Float = 0.5f
) {
    /**
     * 将 Int 设计稿值换算成运行时 Dp。
     */
    fun xdp(value: Int): Dp = xdp(value.toFloat())

    /**
     * 将 Float 设计稿值换算成运行时 Dp。
     */
    fun xdp(value: Float): Dp = scaled(value).dp

    /**
     * 将 Int 设计稿字号换算成运行时 TextUnit。
     */
    fun text(value: Int): TextUnit = text(value.toFloat())

    /**
     * 将 Float 设计稿字号换算成运行时 TextUnit。
     */
    fun text(value: Float): TextUnit = scaled(value).sp

    /**
     * 根据屏幕宽度和设计宽度计算最终缩放值，并保证最小值下限。
     */
    private fun scaled(value: Float): Float {
        return max(value * scale, minValue)
    }
}

private val DefaultComposeDimens = ComposeDimens(
    designWidth = 360f,
    screenWidthDp = 360f,
    scale = 1f
)

val LocalComposeDimens = compositionLocalOf { DefaultComposeDimens }

/**
 * 基于当前屏幕宽度记忆化生成 ComposeDimens。
 */
@Composable
fun rememberComposeDimens(
    designWidth: Int,
    minValue: Float = 0.5f
): ComposeDimens {
    val configuration = LocalConfiguration.current
    val safeDesignWidth = designWidth.coerceAtLeast(1).toFloat()
    val safeScreenWidthDp = configuration.screenWidthDp
        .takeIf { it > 0 }
        ?.toFloat()
        ?: DefaultComposeDimens.screenWidthDp

    return remember(safeDesignWidth, safeScreenWidthDp, minValue) {
        ComposeDimens(
            designWidth = safeDesignWidth,
            screenWidthDp = safeScreenWidthDp,
            scale = safeScreenWidthDp / safeDesignWidth,
            minValue = minValue
        )
    }
}

/**
 * 向当前组合树注入 Compose 尺寸适配能力。
 */
@Composable
fun ProvideComposeDimens(
    designWidth: Int,
    minValue: Float = 0.5f,
    content: @Composable () -> Unit
) {
    val dimens = rememberComposeDimens(
        designWidth = designWidth,
        minValue = minValue
    )
    CompositionLocalProvider(LocalComposeDimens provides dimens) {
        content()
    }
}

/**
 * 读取当前组合树中的 ComposeDimens。
 */
@Composable
@ReadOnlyComposable
fun currentComposeDimens(): ComposeDimens = LocalComposeDimens.current

/**
 * 将 Int 设计稿值直接转换成当前页面的自适应 Dp。
 */
val Int.xdp: Dp
    @Composable
    @ReadOnlyComposable
    get() = LocalComposeDimens.current.xdp(this)

/**
 * 将 Float 设计稿值直接转换成当前页面的自适应 Dp。
 */
val Float.xdp: Dp
    @Composable
    @ReadOnlyComposable
    get() = LocalComposeDimens.current.xdp(this)

/**
 * 使用当前 ComposeDimens 将 Int 值转换成自适应 Dp。
 */
@Composable
@ReadOnlyComposable
fun adaptiveDp(value: Int): Dp = LocalComposeDimens.current.xdp(value)

/**
 * 使用当前 ComposeDimens 将 Int 字号转换成自适应 TextUnit。
 */
@Composable
@ReadOnlyComposable
fun adaptiveText(value: Int): TextUnit = LocalComposeDimens.current.text(value)
