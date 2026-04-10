package com.easy.core.ui.compose

import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.easy.core.CoreConfig
import com.easy.core.R
import com.easy.core.annotation.LayoutModel
import com.easy.core.annotation.ToolBarMode

typealias ComposeScreenContent = @Composable () -> Unit
typealias ComposeToolbarActions = @Composable RowScope.() -> Unit

class ComposeToolbarController(
    title: CharSequence? = null,
    actionItems: List<ComposeToolbarActionSpec> = emptyList(),
    scrollProgress: Float? = null
) {
    val titleState: MutableState<CharSequence?> = mutableStateOf(title)
    val actionItemsState: MutableState<List<ComposeToolbarActionSpec>> = mutableStateOf(actionItems)
    val scrollProgressState: MutableState<Float?> = mutableStateOf(scrollProgress)
    val backgroundColorState: MutableState<Int?> = mutableStateOf(null)
    val toolbarBackgroundColorState: MutableState<Int?> = mutableStateOf(null)
    val statusBarBackgroundColorState: MutableState<Int?> = mutableStateOf(null)
    val contentColorState: MutableState<Int?> = mutableStateOf(null)
    val navigationIconResState: MutableState<Int?> = mutableStateOf(null)
    val navigationExpandedIconResState: MutableState<Int?> = mutableStateOf(null)
    val showBottomLineState: MutableState<Boolean?> = mutableStateOf(null)

    /**
     * 动态更新标题文案。
     */
    fun setTitle(title: CharSequence?) {
        titleState.value = title
    }

    /**
     * 动态更新右侧操作项列表。
     */
    fun setActionItems(actionItems: List<ComposeToolbarActionSpec>) {
        actionItemsState.value = actionItems
    }

    /**
     * 动态更新滚动进度，供标题栏透明度/图标切换等效果使用。
     */
    fun setScrollProgress(progress: Float?) {
        scrollProgressState.value = progress
    }

    /**
     * 同时设置状态栏与 toolbar 的统一背景色。
     */
    fun setBackgroundColor(@ColorRes colorRes: Int?) {
        backgroundColorState.value = colorRes
    }

    /**
     * 单独设置 toolbar 区域背景色。
     */
    fun setToolbarBackgroundColor(@ColorRes colorRes: Int?) {
        toolbarBackgroundColorState.value = colorRes
    }

    /**
     * 单独设置状态栏占位区域背景色。
     */
    fun setStatusBarBackgroundColor(@ColorRes colorRes: Int?) {
        statusBarBackgroundColorState.value = colorRes
    }

    /**
     * 设置标题栏前景色，包括标题和默认返回图标着色语义。
     */
    fun setContentColor(@ColorRes colorRes: Int?) {
        contentColorState.value = colorRes
    }

    /**
     * 设置默认导航图标资源。
     */
    fun setNavigationIcon(@DrawableRes iconRes: Int?) {
        navigationIconResState.value = iconRes
    }

    /**
     * 设置滚动切换场景下展开态导航图标资源。
     */
    fun setExpandedNavigationIcon(@DrawableRes iconRes: Int?) {
        navigationExpandedIconResState.value = iconRes
    }

    /**
     * 控制底部分割线显隐。
     */
    fun setShowBottomLine(show: Boolean?) {
        showBottomLineState.value = show
    }
}

sealed interface ComposeToolbarActionSpec {
    class Text(
        val text: CharSequence,
        @ColorRes val color: Int = R.color.color_333,
        val textSize: Int = 14,
        val endPadding: Int = 10,
        val onClick: (() -> Unit)? = null
    ) : ComposeToolbarActionSpec

    class Icon(
        @DrawableRes val iconRes: Int,
        val startPadding: Int = 0,
        val topPadding: Int = 0,
        val endPadding: Int = 10,
        val bottomPadding: Int = 0,
        val onClick: (() -> Unit)? = null
    ) : ComposeToolbarActionSpec

    class Custom(val content: ComposeToolbarActions) : ComposeToolbarActionSpec
}

/**
 * Compose 标题栏的最小描述对象。
 * 由宿主提供显示数据，最终交给 compose.RootViewImpl 渲染。
 */
class ComposeToolbarSpec(
    val title: CharSequence? = null,
    val showNavigationIcon: Boolean = true,
    @DrawableRes val navigationIconRes: Int = R.mipmap.ic_back_gray,
    @DrawableRes val navigationExpandedIconRes: Int = R.mipmap.ic_black_whit,
    @ColorRes val backgroundColor: Int = CoreConfig.get().defToolBarColor,
    @ColorRes val toolbarBackgroundColor: Int? = null,
    @ColorRes val statusBarBackgroundColor: Int? = null,
    @ColorRes val contentColor: Int = CoreConfig.get().defTitleColor,
    val toolbarHeight: Int = 44,
    val navigationStartPadding: Int = 8,
    val navigationEndPadding: Int = 13,
    val titleTextSize: Int = 18,
    val titleHorizontalPadding: Int = 44,
    val titleMaxWidth: Int = 120,
    val actionsEndPadding: Int = 4,
    val showBottomLine: Boolean = true,
    val scrollProgress: Float? = null,
    val scrollProgressState: State<Float>? = null,
    val controller: ComposeToolbarController? = null,
    val swapNavigationIconOnScroll: Boolean = false,
    val onNavigationClick: (() -> Unit)? = null,
    val actionItems: List<ComposeToolbarActionSpec> = emptyList(),
    val actions: ComposeToolbarActions = {}
)

/**
 * 页面头部区域描述。
 * 新路径不再固定绑定旧版 IToolBar，而是改为“头部插槽”模型。
 */
sealed interface HeaderSpec {
    /**
     * 不显示标题栏。
     * 如果同时开启了 showStatusBar，则只渲染状态栏占位区域。
     */
    object None : HeaderSpec

    /**
     * 使用 Compose 标题栏实现头部区域。
     */
    class ComposeToolbar(val spec: ComposeToolbarSpec) : HeaderSpec

    /**
     * 兼容纯 View 方式的头部区域工厂。
     * 这为后续新旧逻辑并存提供了过渡入口。
     */
    class ViewFactory(val factory: (ViewGroup) -> View) : HeaderSpec
}

/**
 * 页面内容区域描述。
 * 当前支持 Compose 内容和纯 View 工厂两种模式。
 */
sealed interface ContentSpec {
    /**
     * 以 Compose 作为页面内容。
     */
    class Compose(val content: ComposeScreenContent) : ContentSpec

    /**
     * 以纯代码创建 View 作为页面内容。
     */
    class ViewFactory(val factory: (ViewGroup) -> View) : ContentSpec
}

/**
 * 新页面路径的统一装配配置。
 *
 * 设计目标：
 * 1. 将页面描述从 XML/旧基类逻辑中抽离出来。
 * 2. 让 RootViewBuilder 负责收集配置，RootViewImpl 只负责渲染。
 * 3. 为后续 toolbar、content 全面 Compose 化预留统一模型。
 */
data class RootViewConfig(
    @LayoutModel val layoutMode: Int = LayoutModel.LAYOUT_MODE_LINEAR_LAYOUT,
    @ColorRes val backgroundColor: Int = R.color.bg_color,
    val immersiveStatusBar: Boolean = true,
    @ToolBarMode val statusBarMode: Int = CoreConfig.get().isStatusMode,
    @ColorRes val statusBarColor: Int = CoreConfig.get().defStatusColor,
    val showStatusBar: Boolean = true,
    val headerSpec: HeaderSpec = HeaderSpec.None,
    val contentSpec: ContentSpec,
    val composeDimensSpec: ComposeDimensSpec = ComposeDimensSpec(),
    val isAlwaysPortrait: Boolean = true,
    val isFullScreen: Boolean = false
)
