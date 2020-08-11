package com.hqq.core

import android.app.Activity
import android.app.Application
import com.hqq.core.annotation.ToolBarMode
import com.hqq.core.toolbar.BaseDefToolBarImpl
import com.hqq.core.toolbar.IToolBar
import com.hqq.core.utils.RegexUtils

/**
 * @Author : huangqiqiang
 * @Package : com.core.library
 * @FileName :   CoreBuildConfig
 * @Date : 2018/11/23 0023  下午 2:16
 * @Email :  qiqiang213@gmail.com
 * @Descrive :  配置文件
 */
class CoreBuildConfig private constructor() {
    /**
     * 默认 标题栏 内容
     */
    var defItoobar: Class<out IToolBar?> = BaseDefToolBarImpl::class.java
        private set

    /**
     * Application   主要获取 context
     * 理论奔溃后 会再次执行 Application 中的 onCreate()
     * mApplication  应单是 非空的
     */
    var application: Application? = null
        private set

    /**
     * 是否开启 log日志  BuildConfig.Debug
     */
    var isDebug = true
        private set

    /**
     * 默认图
     */
    var defImg = R.drawable.ic_def_img

    /**
     * 状态栏 模式
     */
    var isStatusMode: Int = ToolBarMode.Companion.LIGHT_MODE
        private set

    /**
     * Activity生命周期管理
     */
    private var mActivityLifecycle: ActivityLifecycle? = null

    /**
     * [.init]
     *
     * @param application Application
     */
    @Deprecated("")
    fun init(application: Application?) {
        init(application, true)
    }

    /**
     * @param application Application
     * @param isDebug     是否 开启log日志
     */
    fun init(application: Application?, isDebug: Boolean) {
        this.application = application
        this.isDebug = isDebug
        // 监听Activity 的生命周期
        if (RegexUtils.isNull(mActivityLifecycle)) {
            mActivityLifecycle = ActivityLifecycle()
            application!!.registerActivityLifecycleCallbacks(mActivityLifecycle)
        }
    }

    val defIToolbar: Class<*>
        get() = defItoobar

    fun setDefIToolbar(defIToolbar: Class<out IToolBar?>) {
        defItoobar = defIToolbar
    }

    fun setStatusModeLight(@ToolBarMode statusModeLight: Int): CoreBuildConfig {
        isStatusMode = statusModeLight
        return this
    }

    /**
     * 获取当前的 Activity
     *
     * @return
     */
    val currActivity: Activity?
        get() = mActivityLifecycle?.activity

    companion object {
        /**
         * 单利维持对象
         */
        var singleton: CoreBuildConfig = instance
        val instance: CoreBuildConfig
            get() {

                if (singleton == null) {
                    synchronized(CoreBuildConfig::class.java) {
                        if (singleton == null) {
                            singleton = CoreBuildConfig()
                        }
                    }
                }
                return singleton
            }
    }
}