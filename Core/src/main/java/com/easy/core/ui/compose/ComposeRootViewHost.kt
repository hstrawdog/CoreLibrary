package com.easy.core.ui.compose

import androidx.annotation.ColorRes
import com.easy.core.CoreConfig
import com.easy.core.R
import com.easy.core.annotation.LayoutModel
import com.easy.core.annotation.ToolBarMode

/**
 * 新页面路径中的宿主接口。
 *
 * BaseComposeActivity / BaseComposeFragment / BaseComposeDialog
 * 通过实现该接口声明页面需要的装配信息，再交给 RootViewBuilder 统一汇总。
 */
interface ComposeRootViewHost {
    /**
     * 指定根布局使用线性布局还是帧布局模式。
     */
    @LayoutModel
    fun provideLayoutMode(): Int = LayoutModel.LAYOUT_MODE_LINEAR_LAYOUT

    /**
     * 指定页面根容器背景色。
     */
    @ColorRes
    fun provideBackgroundColor(): Int = R.color.bg_color

    /**
     * 是否显示顶部状态栏占位区域。
     */
    fun provideShowStatusBar(): Boolean = true

    /**
     * 指定顶部状态栏占位区域的背景色。
     */
    @ColorRes
    fun provideStatusBarColor(): Int = CoreConfig.get().defStatusColor

    /**
     * 指定状态栏图标深浅模式。
     */
    @ToolBarMode
    fun provideStatusBarMode(): Int = CoreConfig.get().isStatusMode

    /**
     * 是否显示默认标题栏区域。
     */
    fun provideShowToolbar(): Boolean = true

    /**
     * 提供可被页面动态修改的标题栏状态控制器。
     */
    fun provideComposeToolbarController(): ComposeToolbarController? = null

    /**
     * 组装默认的 ComposeToolbarSpec。
     */
    fun provideComposeToolbarSpec(): ComposeToolbarSpec {
        return ComposeToolbarSpec(
            title = providePageTitle(),
            controller = provideComposeToolbarController()
        )
    }

    /**
     * 默认标题栏实现。
     * 当前优先给新路径提供 Compose Toolbar，后续也可以由宿主改成自定义 HeaderSpec。
     */
    fun provideHeaderSpec(): HeaderSpec {
        return if (provideShowToolbar()) {
            HeaderSpec.ComposeToolbar(
                provideComposeToolbarSpec()
            )
        } else {
            HeaderSpec.None
        }
    }

    /**
     * 提供页面标题，默认供 ComposeToolbar 使用。
     */
    fun providePageTitle(): CharSequence? = null

    /**
     * 提供页面主体内容。
     */
    fun provideContentSpec(): ContentSpec

    /**
     * 提供当前页面的 Compose 尺寸适配配置。
     */
    fun provideComposeDimensSpec(): ComposeDimensSpec = CoreConfig.get().composeDimensSpec

    /**
     * 是否强制竖屏显示。
     */
    fun isAlwaysPortrait(): Boolean = true

    /**
     * 是否启用全屏窗口模式。
     */
    fun isFullScreen(): Boolean = false

    /**
     * 是否接管状态栏沉浸式模式。
     */
    fun useImmersiveStatusBar(): Boolean = true

    /**
     * 汇总当前页面的根布局配置。
     * 宿主通常只重写单个 provideXxx 方法，避免每个页面自己拼完整配置。
     */
    fun provideRootViewConfig(): RootViewConfig {
        return RootViewConfig(
            layoutMode = provideLayoutMode(),
            backgroundColor = provideBackgroundColor(),
            immersiveStatusBar = useImmersiveStatusBar(),
            statusBarMode = provideStatusBarMode(),
            statusBarColor = provideStatusBarColor(),
            showStatusBar = provideShowStatusBar(),
            headerSpec = provideHeaderSpec(),
            contentSpec = provideContentSpec(),
            composeDimensSpec = provideComposeDimensSpec(),
            isAlwaysPortrait = isAlwaysPortrait(),
            isFullScreen = isFullScreen()
        )
    }
}
