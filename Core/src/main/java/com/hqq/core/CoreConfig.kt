package com.hqq.core

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.pm.ApplicationInfo
import com.google.gson.InstanceCreator
import com.hqq.core.annotation.ToolBarMode
import com.hqq.core.toolbar.DefToolBar
import com.hqq.core.utils.RegexUtils
import java.lang.reflect.Type
import java.util.*

/**
 * @Author : huangqiqiang
 * @Package : com.core.library
 * @FileName :   CoreBuildConfig
 * @Date : 2018/11/23 0023  下午 2:16
 * @Email :  qiqiang213@gmail.com
 * @Descrive :  配置文件
 */
class CoreConfig private constructor() {
    /**
     * 单利对象
     */
    companion object {
        private var instance: CoreConfig? = null
            get() {
                if (field == null) {
                    synchronized(CoreConfig::class.java) {
                        if (field == null) {
                            field = CoreConfig()
                        }
                    }
                }
                return field
            }

        fun get(): CoreConfig {
            return instance!!
        }

        fun getApplicationContext(): Context {
            return get().application.applicationContext
        }
    }

    /**
     *  请求默认的地址
     */
    var baseUrl: String = ""

    /**
     * 读取超时
     */
    var readTimeout: Long = 15

    /**
     * 写超时
     */
    var writeTimeout: Long = 15

    /**
     * 连接超时
     */
    var connectTimeout: Long = 15

    /**
     *  Gson 转义对象
     */
    val instanceCreators: Map<Type, InstanceCreator<*>> = HashMap()


    /**
     * 获取当前的 Activity
     *
     * @return
     */
    val currActivity: Activity? get() = mActivityLifecycle?.activity


    /**
     * Application   主要获取 context
     * 理论奔溃后 会再次执行 Application 中的 onCreate()
     * mApplication  应单是 非空的
     */
    lateinit var application: Application

    /**
     * 是否开启 log日志  BuildConfig.Debug
     */
    var isDebug = true

    /**
     * 默认图
     */
    var defImg = R.drawable.ic_def_img

    /**
     * 状态栏 模式
     */
    @ToolBarMode
    var isStatusMode: Int = ToolBarMode.Companion.LIGHT_MODE

    /**
     * Activity生命周期管理
     */
    private var mActivityLifecycle: ActivityLifecycle? = null

    /**
     *  toolBar 的构建方法 可以重新赋值
     */
    var iCreateToolbar: Class<DefToolBar> = DefToolBar::class.java

    /**
     * @param application Application
     * @param isDebug     是否 开启log日志
     */
    fun init(application: Application) {
        // 设置当前APK  是否是Debug版本
        val info = application.applicationInfo
        this.isDebug = info.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0
        this.application = application

        // 监听Activity 的生命周期
        if (RegexUtils.isNull(mActivityLifecycle)) {
            mActivityLifecycle = ActivityLifecycle()
            application.registerActivityLifecycleCallbacks(mActivityLifecycle)
        }
    }

    fun isInitialized(): Boolean {
        return this::application.isInitialized
    }

}