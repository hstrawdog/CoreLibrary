package com.easy.core

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.pm.ApplicationInfo
import com.google.gson.InstanceCreator
import com.easy.core.R
import com.easy.core.annotation.ToolBarMode
import com.easy.core.common.TAG
import com.easy.core.toolbar.DefToolBar
import com.easy.core.utils.data.DataUtils
import com.easy.core.utils.ScreenUtils
import com.easy.core.utils.log.LogUtils
import com.google.gson.JsonObject
import okhttp3.Interceptor
import okhttp3.ResponseBody.Companion.toResponseBody
import java.lang.reflect.Type

/**
 * @Author : huangqiqiang
 * @Package : com.core.library
 * @FileName :   CoreBuildConfig
 * @Date : 2018/11/23 0023  下午 2:16
 * @Email :  qiqiang213@gmail.com
 * @Describe :  配置文件
 */
class CoreConfig private constructor() {
    /**
     * 单利对象
     */
    companion object {
        private var instance:CoreConfig? = null
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

        fun get():CoreConfig {
            return instance!!
        }

        val applicationContext:Context
            get() {
                return get().application.applicationContext
            }
    }

    //region Net 网络相关

    /**
     *  请求默认的地址
     */
    var baseUrl:String = ""

    /**
     * 读取超时 时间
     */
    var readTimeout:Long = 15

    /**
     * 写超时
     */
    var writeTimeout:Long = 15

    /**
     * 连接超时
     */
    var connectTimeout:Long = 15
    //endregion
    //region 状态栏 相关
    /**
     * 状态栏 模式
     */
    @ToolBarMode
    var isStatusMode:Int = ToolBarMode.LIGHT_MODE

    /**
     *  toolBar 的构建方法 可以重新赋值
     */
    var iCreateToolbar:Class<DefToolBar> = DefToolBar::class.java

    /**
     *  状态栏高度
     */
    var statusBarHeight:Int = 0

    /**
     *  状态栏颜色
     */
    var defStatusColor = R.color.toolbar_status_color

    /**
     *  标题栏背景颜色
     */
    var defToolBarColor = R.color.toolbar_bg_color

    /**
     * 默认标题字体颜色
     */
    var defTitleColor = R.color.toolbar_text_color
    //endregion

    /**
     *  Gson 转义对象
     */
    val instanceCreators:Map<Type, InstanceCreator<*>> = HashMap()

    /**
     *  okhttp 的拦截器
     */
    val interceptorList:ArrayList<Interceptor> = ArrayList()

    /**
     * 获取当前的 Activity
     *
     * @return
     */
    val currActivity:Activity? get() = mActivityLifecycle?.activity

    /**
     * Activity生命周期管理
     */
    var mActivityLifecycle:ActivityLifecycle? = null

    /**
     * Application   主要获取 context
     * 理论奔溃后 会再次执行 Application 中的 onCreate()
     * mApplication  应单是 非空的
     */
    lateinit var application:Application

    /**
     * 是否开启 log日志  BuildConfig.Debug
     */
    var isDebug = true

    /**
     * 默认图  加载中的图片
     */
    var defImg = R.drawable.ic_def_img

    /**
     *  加载失败图片
     */
    var defErrorImg = R.drawable.ic_def_img

    /**
     *  默认配置使用  菊花加载
     */
    var defLoadingImage = R.mipmap.ic_loading

    /**
     *    git加载动画
     */
    var gifLoading = ""

    /**
     *  是否显示gif
     */
    var isShowGif = false

    /**
     * 是否跳转到设置界面 设置权限
     */
    var goSettingPermission = true

    /**
     *  是否显示华为权限提示
     */
    var isShowPermissionTip = false


    /**
     * @param application Application
     * @param isDebug     是否 开启log日志
     */
    fun init(application:Application) {
        // 设置当前APK  是否是Debug版本
        val info = application.applicationInfo
        this.isDebug = info.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0
        this.application = application
        statusBarHeight = ScreenUtils.getStatusBarHeight4Resources(applicationContext)
        // 监听Activity 的生命周期
        if (DataUtils.checkIsNull(mActivityLifecycle)) {
            mActivityLifecycle = ActivityLifecycle()
            application.registerActivityLifecycleCallbacks(mActivityLifecycle)
        }


        // 创建一个 拦截器 打印请求日志
        CoreConfig.get().interceptorList.add(Interceptor { chain ->
            val request = chain.request()
            LogUtils.dMark(TAG.TAG_REQUEST, "-------------自定义拦截请求 start ----------------")

            // 打印 URL 和方法
            val url = request.url
            LogUtils.dMark(TAG.TAG_REQUEST, "Request URL: $url")
            LogUtils.dMark(TAG.TAG_REQUEST, "Request Method: ${request.method.uppercase()}")

            // 打印查询参数
            if (url.querySize > 0) {
                val json = JsonObject()
                for (i in 0 until url.querySize) {
                    val name = url.queryParameterName(i)
                    val value = url.queryParameterValue(i)
                    json.addProperty(name, value)
                }
                LogUtils.dMark(TAG.TAG_REQUEST, "Query Params: ${json.toString()}")
            }

            // 打印请求体
            request.body?.let { body ->
                val buffer = okio.Buffer()
                body.writeTo(buffer)
                val charset = body.contentType()?.charset(Charsets.UTF_8) ?: Charsets.UTF_8
                LogUtils.dMark(TAG.TAG_REQUEST, "Request Body: ${buffer.readString(charset)}")
            }

            // 打印请求头
            LogUtils.dMark(TAG.TAG_REQUEST, "Request Headers: ${request.headers}")

            // 记录开始时间
            val startNs = System.nanoTime()

            // 发送请求
            val response = chain.proceed(request)

            // 计算耗时
            val tookMs = (System.nanoTime() - startNs) / 1_000_000
            LogUtils.dMark(TAG.TAG_REQUEST, "Request took: ${tookMs}ms")

            // 响应体处理
            val responseBody = response.body
            val contentType = responseBody?.contentType()
            val mediaType = contentType?.type ?: ""
            val subtype = contentType?.subtype ?: ""

            val isTextResponse = mediaType == "text" || subtype.contains("json") || subtype.contains("xml") || subtype.contains("html")
            LogUtils.dMark(TAG.TAG_REQUEST, "------------- 响应内容  ----------------")

            val content = if (responseBody != null && isTextResponse) {
                responseBody.string()
            } else {
                "非文本响应，内容忽略"
            }

            LogUtils.d("tagRequest", "Response Code: ${response.code}")
            LogUtils.dMark(TAG.TAG_REQUEST, "Response Body: $content")

            LogUtils.dMark(TAG.TAG_REQUEST, "-------------自定义拦截请求 end ----------------")

            // 重新构建响应体返回上层
            val newResponseBody = if (isTextResponse) {
                content.toByteArray().toResponseBody(contentType)
            } else {
                responseBody
            }

            response.newBuilder().body(newResponseBody).build()
        })

    }

    fun isInitialized():Boolean {
        return this::application.isInitialized
    }

}