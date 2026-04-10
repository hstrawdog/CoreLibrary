package com.easy.core.ui.compose

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.easy.core.CoreConfig
import com.easy.core.R

/**
 * 默认的 Compose 标题栏实现，负责组合状态栏背景、toolbar 内容和右侧操作区。
 */
@Composable
fun ComposeToolbar(
    config: RootViewConfig,
    spec: ComposeToolbarSpec,
    activity: Activity? = null
) {
    val dimens = currentComposeDimens()
    val resolvedBackgroundColorRes = spec.controller?.backgroundColorState?.value ?: spec.backgroundColor
    val resolvedToolbarBackgroundColorRes = spec.controller?.toolbarBackgroundColorState?.value
        ?: spec.toolbarBackgroundColor
        ?: resolvedBackgroundColorRes
    val resolvedStatusBarBackgroundColorRes = spec.controller?.statusBarBackgroundColorState?.value
        ?: spec.statusBarBackgroundColor
        ?: resolvedBackgroundColorRes
    val resolvedContentColorRes = spec.controller?.contentColorState?.value ?: spec.contentColor
    val resolvedNavigationIconRes = spec.controller?.navigationIconResState?.value ?: spec.navigationIconRes
    val resolvedExpandedNavigationIconRes = spec.controller?.navigationExpandedIconResState?.value
        ?: spec.navigationExpandedIconRes
    val resolvedShowBottomLine = spec.controller?.showBottomLineState?.value ?: spec.showBottomLine
    val resolvedTitle = spec.controller?.titleState?.value ?: spec.title
    val resolvedActionItems = spec.controller?.actionItemsState?.value ?: spec.actionItems
    val progress = (
        spec.controller?.scrollProgressState?.value
            ?: spec.scrollProgressState?.value
            ?: spec.scrollProgress
        )?.coerceIn(0f, 1f)
    val statusBarHeight = with(LocalDensity.current) { CoreConfig.get().statusBarHeight.toDp() }
    val toolbarHeight = spec.toolbarHeight.xdp
    val iconStartPadding = spec.navigationStartPadding.xdp
    val iconEndPadding = spec.navigationEndPadding.xdp
    val titleHorizontalPadding = spec.titleHorizontalPadding.xdp
    val toolbarBackgroundColor = colorResource(resolvedToolbarBackgroundColorRes)
    val statusBarBackgroundColor = colorResource(resolvedStatusBarBackgroundColorRes)
    val contentColor = colorResource(resolvedContentColorRes)
    val appCompatActivity = activity as? AppCompatActivity
    val backgroundAlpha = progress ?: 1f
    val titleAlpha = progress ?: 1f
    val bottomLineAlpha = progress ?: 1f
    val navigationPainter = when {
        !spec.swapNavigationIconOnScroll -> painterResource(resolvedNavigationIconRes)
        progress == null -> painterResource(resolvedNavigationIconRes)
        progress < 0.5f -> painterResource(resolvedExpandedNavigationIconRes)
        else -> painterResource(resolvedNavigationIconRes)
    }
    val navigationAlpha = when {
        !spec.swapNavigationIconOnScroll -> progress ?: 1f
        progress == null -> 1f
        progress < 0.5f -> 1f - progress * 2f
        else -> (progress - 0.5f) * 2f
    }.coerceIn(0f, 1f)

    Column(modifier = Modifier.fillMaxWidth()) {
        if (config.showStatusBar) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(statusBarHeight)
            ) {
                Spacer(
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(backgroundAlpha)
                        .background(statusBarBackgroundColor)
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(toolbarHeight)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(backgroundAlpha)
                    .background(toolbarBackgroundColor)
            )
            if (spec.showNavigationIcon) {
                BackHandler(enabled = false) {}
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .fillMaxHeight()
                        .clickable {
                            spec.onNavigationClick?.invoke()
                                ?: appCompatActivity?.onBackPressedDispatcher?.onBackPressed()
                        }
                        .padding(start = iconStartPadding, end = iconEndPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = navigationPainter,
                        contentDescription = "Back",
                        modifier = Modifier.alpha(navigationAlpha)
                    )
                }
            }

            Text(
                text = resolvedTitle?.toString().orEmpty(),
                color = contentColor,
                fontSize = dimens.text(spec.titleTextSize),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .align(Alignment.Center)
                    .alpha(titleAlpha)
                    .padding(horizontal = titleHorizontalPadding)
                    .widthIn(max = spec.titleMaxWidth.xdp)
            )

            Row(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .fillMaxHeight()
                    .padding(end = spec.actionsEndPadding.xdp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                RenderToolbarActionItems(resolvedActionItems)
                spec.actions(this)
            }
        }
        if (resolvedShowBottomLine) {
            Divider(
                modifier = Modifier.alpha(bottomLineAlpha),
                color = colorResource(R.color.toolbar_line_bg),
                thickness = 1.dp
            )
        }
    }
}

/**
 * 渲染标题栏右侧的文本、图标和自定义操作项。
 */
@Composable
private fun RowScope.RenderToolbarActionItems(
    actionItems: List<ComposeToolbarActionSpec>
) {
    val dimens = currentComposeDimens()
    actionItems.forEach { actionItem ->
        when (actionItem) {
            is ComposeToolbarActionSpec.Text -> {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .clickable(enabled = actionItem.onClick != null) {
                            actionItem.onClick?.invoke()
                        }
                        .padding(end = actionItem.endPadding.xdp)
                        .align(Alignment.CenterVertically),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = actionItem.text.toString(),
                        color = colorResource(actionItem.color),
                        fontSize = dimens.text(actionItem.textSize)
                    )
                }
            }

            is ComposeToolbarActionSpec.Icon -> {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .clickable(enabled = actionItem.onClick != null) {
                            actionItem.onClick?.invoke()
                        }
                        .padding(
                            start = actionItem.startPadding.xdp,
                            top = actionItem.topPadding.xdp,
                            end = actionItem.endPadding.xdp,
                            bottom = actionItem.bottomPadding.xdp
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(actionItem.iconRes),
                        contentDescription = null
                    )
                }
            }

            is ComposeToolbarActionSpec.Custom -> actionItem.content(this)
        }
    }
}

/**
 * 预览默认标题栏样式，便于独立检查状态栏背景、标题和操作区布局。
 */
@Preview(showBackground = true, widthDp = 393, heightDp = 140)
@Composable
private fun ComposeToolbarPreview() {
    val toolbarSpec = remember {
        ComposeToolbarSpec(
            title = "Compose Toolbar",
            actionItems = listOf(
                ComposeToolbarActionSpec.Text(text = "编辑"),
                ComposeToolbarActionSpec.Icon(iconRes = R.drawable.ic_more)
            )
        )
    }
    val config = remember {
        RootViewConfig(
            showStatusBar = true,
            headerSpec = HeaderSpec.None,
            contentSpec = ContentSpec.Compose {}
        )
    }
    ProvideComposeDimens(designWidth = 375) {
        MaterialTheme {
            ComposeToolbar(
                config = config,
                spec = toolbarSpec
            )
        }
    }
}

/**
 * 预览滚动联动场景下的标题栏透明度和导航图标切换效果。
 */
@Preview(showBackground = true, widthDp = 393, heightDp = 140)
@Composable
private fun ComposeToolbarScrolledPreview() {
    val toolbarSpec = remember {
        ComposeToolbarSpec(
            title = "Scroll Toolbar",
            backgroundColor = R.color.transparent,
            toolbarBackgroundColor = R.color.white,
            statusBarBackgroundColor = R.color.white,
            contentColor = R.color.color_333,
            scrollProgress = 0.72f,
            swapNavigationIconOnScroll = true,
            actionItems = listOf(
                ComposeToolbarActionSpec.Text(text = "完成"),
                ComposeToolbarActionSpec.Icon(iconRes = R.drawable.ic_more)
            )
        )
    }
    val config = remember {
        RootViewConfig(
            showStatusBar = true,
            headerSpec = HeaderSpec.None,
            contentSpec = ContentSpec.Compose {}
        )
    }
    ProvideComposeDimens(designWidth = 375) {
        MaterialTheme {
            ComposeToolbar(
                config = config,
                spec = toolbarSpec
            )
        }
    }
}
