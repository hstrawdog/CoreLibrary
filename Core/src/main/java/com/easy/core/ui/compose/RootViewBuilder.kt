package com.easy.core.ui.compose

import android.app.Activity
import android.content.pm.ActivityInfo
import android.view.Window
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.easy.core.R

/**
 * 新路径中的装配信息收集器。
 *
 * 职责：
 * 1. 根据宿主类型(Activity / Fragment / DialogFragment)创建 builder。
 * 2. 从 ComposeRootViewHost 中汇总 RootViewConfig。
 * 3. 处理少量宿主相关的默认值和窗口配置。
 *
 * 不负责真正渲染 View，渲染职责交给 compose.RootViewImpl。
 */
class RootViewBuilder private constructor(
    private val activity: Activity?,
    private val hostType: HostType,
    private val host: ComposeRootViewHost
) {
    /**
     * 当前 builder 绑定的宿主类型。
     */
    enum class HostType {
        ACTIVITY,
        FRAGMENT,
        DIALOG
    }

    val rootViewImpl: RootViewImpl = RootViewImpl(activity)

    /**
     * 基于宿主接口生成统一配置。
     * Dialog 在未显式指定背景色时，默认改成透明背景。
     */
    fun buildConfig(): RootViewConfig {
        var config = host.provideRootViewConfig()
        if (hostType == HostType.DIALOG && config.backgroundColor == R.color.bg_color) {
            config = config.copy(backgroundColor = R.color.transparent, immersiveStatusBar = false)
        }
        return config
    }

    /**
     * 处理宿主级别配置。
     * 当前仅对 Activity 生效，例如全屏与竖屏策略。
     */
    fun initHost(config: RootViewConfig) {
        if (hostType != HostType.ACTIVITY) {
            return
        }
        if (config.isFullScreen) {
            activity?.requestWindowFeature(Window.FEATURE_NO_TITLE)
            activity?.window?.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        if (config.isAlwaysPortrait) {
            activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }

    companion object {
        /**
         * Activity 宿主入口。
         */
        fun forActivity(activity: Activity, host: ComposeRootViewHost): RootViewBuilder {
            return RootViewBuilder(activity = activity, hostType = HostType.ACTIVITY, host = host)
        }

        /**
         * Fragment 宿主入口。
         */
        fun forFragment(fragment: Fragment, host: ComposeRootViewHost): RootViewBuilder {
            return RootViewBuilder(activity = fragment.activity, hostType = HostType.FRAGMENT, host = host)
        }

        /**
         * DialogFragment 宿主入口。
         */
        fun forDialogFragment(dialogFragment: DialogFragment, host: ComposeRootViewHost): RootViewBuilder {
            return RootViewBuilder(activity = dialogFragment.activity, hostType = HostType.DIALOG, host = host)
        }
    }
}
