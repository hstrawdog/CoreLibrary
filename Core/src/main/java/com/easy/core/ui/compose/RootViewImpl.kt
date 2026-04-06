package com.easy.core.ui.compose

import android.app.Activity
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import com.easy.core.CoreConfig
import com.easy.core.R
import com.easy.core.annotation.LayoutModel
import com.easy.core.annotation.ToolBarMode
import com.easy.core.utils.statusbar.StatusBarManager

/**
 * 新路径中的根布局渲染器。
 *
 * 与旧版 ui.base.RootViewImpl 的区别：
 * 1. 不再依赖 IToolBar。
 * 2. 按 HeaderSpec / ContentSpec 插槽模型组装页面。
 * 3. 面向 Compose 和纯代码 View 作为主路径，而不是 XML。
 */
class RootViewImpl(private val activity: Activity?) {
    /**
     * 根据统一配置渲染最终根布局。
     */
    fun render(config: RootViewConfig): View {
        applyStatusBarMode(config)
        return when (config.layoutMode) {
            LayoutModel.LAYOUT_MODE_FRAME_LAYOUT -> createFrameRoot(config)
            else -> createLinearRoot(config)
        }
    }

    /**
     * 线性根布局。
     * 默认按“header 在上，content 在下”顺序组装。
     */
    private fun createLinearRoot(config: RootViewConfig): View {
        val layout = LinearLayout(activity)
        layout.orientation = LinearLayout.VERTICAL
        layout.overScrollMode = View.OVER_SCROLL_NEVER
        layout.setBackgroundResource(config.backgroundColor)

        createHeaderView(config, layout)?.let { header ->
            layout.addView(header)
        }

        val contentView = createContentView(config, layout)
        if (contentView.layoutParams == null) {
            contentView.layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                0,
                1f
            )
        }
        layout.addView(contentView)
        return layout
    }

    /**
     * 帧布局根容器。
     * 适合需要头部悬浮在内容上方的场景。
     */
    private fun createFrameRoot(config: RootViewConfig): View {
        val frameLayout = FrameLayout(requireActivity())
        frameLayout.overScrollMode = View.OVER_SCROLL_NEVER
        frameLayout.setBackgroundResource(config.backgroundColor)

        val contentView = createContentView(config, frameLayout)
        if (contentView.layoutParams == null) {
            contentView.layoutParams = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }
        frameLayout.addView(contentView)

        createHeaderView(config, frameLayout)?.let { header ->
            val params = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            params.gravity = Gravity.TOP
            frameLayout.addView(header, params)
        }
        return frameLayout
    }

    /**
     * 根据 HeaderSpec 渲染头部区域。
     */
    private fun createHeaderView(config: RootViewConfig, parent: ViewGroup): View? {
        return when (val header = config.headerSpec) {
            HeaderSpec.None -> {
                if (config.showStatusBar) createStatusBarPlaceholder(config) else null
            }

            is HeaderSpec.ComposeToolbar -> createComposeToolbarView(config, header.spec)
            is HeaderSpec.ViewFactory -> {
                val container = LinearLayout(parent.context)
                container.orientation = LinearLayout.VERTICAL
                if (config.showStatusBar) {
                    container.addView(createStatusBarPlaceholder(config))
                }
                container.addView(header.factory(parent))
                container
            }
        }
    }

    /**
     * 根据 ContentSpec 渲染内容区域。
     */
    private fun createContentView(config: RootViewConfig, parent: ViewGroup): View {
        return when (val content = config.contentSpec) {
            is ContentSpec.Compose -> createComposeView(config, parent, content.content)
            is ContentSpec.ViewFactory -> content.factory(parent)
        }
    }

    /**
     * 将 Compose 内容包装成 ComposeView，方便继续复用现有 View 树装配方式。
     */
    private fun createComposeView(
        config: RootViewConfig,
        parent: ViewGroup,
        content: ComposeScreenContent
    ): ComposeView {
        return ComposeView(parent.context).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            setContent {
                ProvideComposeRoot(config = config) {
                    MaterialTheme {
                        content()
                    }
                }
            }
        }
    }

    /**
     * 创建 Compose 标题栏容器。
     */
    private fun createComposeToolbarView(config: RootViewConfig, spec: ComposeToolbarSpec): ComposeView {
        return ComposeView(requireActivity()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            setContent {
                ProvideComposeRoot(config = config) {
                    MaterialTheme {
                        ComposeToolbar(
                            config = config,
                            spec = spec,
                            activity = activity
                        )
                    }
                }
            }
        }
    }

    /**
     * 为当前页面注入 Compose 尺寸适配环境。
     */
    @Composable
    private fun ProvideComposeRoot(
        config: RootViewConfig,
        content: @Composable () -> Unit
    ) {
        val dimensSpec = config.composeDimensSpec
        if (dimensSpec.enabled) {
            ProvideComposeDimens(
                designWidth = dimensSpec.designWidth,
                minValue = dimensSpec.minValue,
                content = content
            )
        } else {
            content()
        }
    }

    /**
     * 创建状态栏占位 View。
     * 新路径暂时仍沿用状态栏高度计算结果，避免直接影响旧行为。
     */
    private fun createStatusBarPlaceholder(config: RootViewConfig): View {
        return View(requireActivity()).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                CoreConfig.get().statusBarHeight
            )
            setBackgroundResource(config.statusBarColor)
        }
    }

    /**
     * 在真正渲染前应用状态栏深浅模式。
     */
    private fun applyStatusBarMode(config: RootViewConfig) {
        if (!config.immersiveStatusBar) {
            return
        }
        when (config.statusBarMode) {
            ToolBarMode.LIGHT_MODE -> StatusBarManager.setStatusBarModel(activity?.window, true)
            ToolBarMode.DARK_MODE -> StatusBarManager.setStatusBarModel(activity?.window, false)
        }
    }

    /**
     * 新路径渲染时必须拿到 Activity，上层 builder 会保证这一点。
     */
    private fun requireActivity(): Activity {
        return requireNotNull(activity) { "Compose root rendering requires an Activity context." }
    }
}
