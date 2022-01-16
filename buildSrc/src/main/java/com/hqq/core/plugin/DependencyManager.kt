package com.hqq.core.plugin

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core
 * @FileName :   DependencyManager
 * @Date  : 2020/8/11 0011  下午 2:12
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
object BuildConfig {
    val compileSdkVersion = 30
    val buildToolsVersion = "30.0.2"
    val minSdkVersion = 21
    val targetSdkVersion = 30
}


/**
 * 核心包
 * https://developer.android.google.cn/jetpack/androidx/releases/appcompat
 * 自动导入
 *  androidx.activity:activity:1.0.0
androidx.annotation:annotation:1.1.0
androidx.appcompat:appcompat:1.2.0
androidx.appcompat:appcompat-resources:1.2.0
androidx.arch.core:core-common:2.1.0
androidx.arch.core:core-runtime:2.0.0
androidx.collection:collection:1.1.0
androidx.core:core:1.3.0
androidx.cursoradapter:cursoradapter:1.0.0
androidx.customview:customview:1.0.0
androidx.drawerlayout:drawerlayout:1.0.0
androidx.fragment:fragment:1.1.0
androidx.interpolator:interpolator:1.0.0
androidx.lifecycle:lifecycle-common:2.1.0
androidx.lifecycle:lifecycle-livedata:2.0.0
androidx.lifecycle:lifecycle-livedata-core:2.0.0
androidx.lifecycle:lifecycle-runtime:2.1.0
androidx.lifecycle:lifecycle-viewmodel:2.1.0
androidx.loader:loader:1.0.0
androidx.savedstate:savedstate:1.0.0
androidx.vectordrawable:vectordrawable:1.1.0
androidx.vectordrawable:vectordrawable-animated:1.1.0
androidx.versionedparcelable:versionedparcelable:1.1.0
androidx.viewpager:viewpager:1.0.0
 */
object Appcompat {
    val appcompat = "androidx.appcompat:appcompat:1.1.0"

    val core_version = "1.3.2"
    val core = "androidx.core:core:$core_version"
    val coreKtx = "androidx.core:core-ktx:$core_version"


}


/**
 * Kotlin 拓展包
 * https://developer.android.google.cn/kotlin/ktx/extensions-list
 */
object KTX {
    //Kotlin 拓展包
    val activityKtx = "androidx.activity:activity-ktx:1.3.0"

    // Kotlin
    val fragmentKtx = "androidx.fragment:fragment-ktx:1.3.0"
}


/**
 * AndroidX 组件
 *
 */
object Components {

    //  CardView
    // https://developer.android.google.cn/jetpack/androidx/releases/cardview
    val cardview = "androidx.cardview:cardview:1.0.0"

    // ViewPage2
    // https://developer.android.google.cn/jetpack/androidx/releases/viewpager2
    val viewpager2 = "androidx.viewpager2:viewpager2:1.0.0"

    // constraintLayout
    // https://developer.android.google.cn/jetpack/androidx/releases/constraintlayout
    val constraintLayout = "androidx.constraintlayout:constraintlayout:2.1.2"

    // https://developer.android.google.cn/jetpack/androidx/releases/recyclerview
    val recyclerview = "androidx.recyclerview:recyclerview:1.2.1"
    val recyclerViewSelection = "androidx.recyclerview:recyclerview-selection:1.1.0"

    //https://developer.android.google.cn/jetpack/androidx/releases/swiperefreshlayout
    val swiperefreshlayout = "androidx.swiperefreshlayout:swiperefreshlayout:1.0.0"
    //
    val meteria = "com.google.android.material:material:1.3.0"

}

/**
 * Lifecycle Ktx
 * https://developer.android.google.cn/jetpack/androidx/releases/lifecycle
 */
object Lifecycle {
    val lifecycle_version = "2.2.0"
    val arch_version = "2.1.0"

    // ViewModel
    val lifecycleViewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version"

    // ViewModel utilities for Compose
    val lifecycle_viewModel_compose = "androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycle_version"

    // LiveData
    val lifecycleLivedataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version"

    // 仅生命周期（没有 ViewModel 或 LiveData）
    val lifecycleRunTimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycle_version"

    // ViewModel 的已保存状态模块
    val lifecycle_viewmodel_savedstate = "androidx.lifecycle:lifecycle-viewmodel-savedstate:$lifecycle_version"

    // Annotation processor
    val lifecycle_compiler = "androidx.lifecycle:lifecycle-compiler:$lifecycle_version"

    // 或者 - 如果使用 Java8，请使用以下内容而不是生命周期编译器
    val lifecycle_common_java8 = "androidx.lifecycle:lifecycle-common-java8:$lifecycle_version"

    // optional - helpers for implementing LifecycleOwner in a Service
    val lifecycle_service = "androidx.lifecycle:lifecycle-service:$lifecycle_version"

    // optional - ProcessLifecycleOwner provides a lifecycle for the whole application process
    val lifecycle_process = "androidx.lifecycle:lifecycle-process:$lifecycle_version"

    // optional - ReactiveStreams support for LiveData
    val lifecycle_reactivestreams_ktx = "androidx.lifecycle:lifecycle-reactivestreams-ktx:$lifecycle_version"

    // optional - Test helpers for LiveData
    val core_testing = "androidx.arch.core:core-testing:$arch_version"

}

/**
 *   AndroidX  Compose   meteria
 *   包含7个子包
 *   介绍:  https://developer.android.google.cn/topic/libraries/support-library/features#material-design
 *   详细内容: https://developer.android.google.cn/jetpack/androidx/releases/compose?hl=zh_cn
 *
 */
object ComposeMeteria {
    val meteria = "androidx.compose.material:material:1.0.5"

    //在 Jetpack Compose 应用中构建动画，丰富用户的体验
    var animationVersion = "1.0.5"
    val animation = "androidx.compose.animation:animation:$animationVersion"

    // 借助 Kotlin 编译器插件，转换 @Composable functions（可组合函数）并启用优化功能。
    var compilerVersion = "1.0.5"
    val compiler = "androidx.compose.compiler:compiler:$compilerVersion"

}

/**
 * 可在应用启动时简单、高效地初始化组件。
 * https://developer.android.google.cn/jetpack/androidx/releases/startup
 */
object Startup {
    val appStartup = "androidx.startup:startup-runtime:1.1.0"
}

/**
 *  KT 相关
 */
object Kt {
    var kotlin = "1.4.21"
    val stdlibJdk7 = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${kotlin}"
    val stdlibJdk8 = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${kotlin}"
    val test = "org.jetbrains.kotlin:kotlin-test-junit:${kotlin}"
    val plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${kotlin}"
}

/**
 *  网络请求
 *  https://github.com/square/okhttp/tree/master/okhttp-logging-interceptor
 *  https://github.com/square/okhttp/tags
 *  https://github.com/square/retrofit/tags
 */
object Retrofit {
    val retrofitVsrsion = "2.9.0"
    val okhttpVsrsion = "4.9.3"

    val okhttp = "com.squareup.okhttp3:okhttp:$okhttpVsrsion"

    // 需要与 okhttp 保持一致
    val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:$okhttpVsrsion"

    val retrofit = "com.squareup.retrofit2:retrofit:$retrofitVsrsion"
    val converterGson = "com.squareup.retrofit2:converter-gson:$retrofitVsrsion"
}

/**
 * 图片加载 Glide
 * https://github.com/bumptech/glide/tags
 */
object Glide {
    val gldieVsrsion = "4.12.0"

    val glide = "com.github.bumptech.glide:glide:$gldieVsrsion"
    val glideCompiler = "com.github.bumptech.glide:compiler:$gldieVsrsion"
}

/**
 *  注解 依赖注入   dagger
 *  https://developer.android.google.cn/jetpack/androidx/releases/hilt?hl=zh_cn
 */
object Hilt {
    val hiltVersion = "2.28-alpha"
    val hitViewModule = "1.0.0-alpha01"

    val daggerRuntime = "com.google.dagger:hilt-android:$hiltVersion"
    val daggerCompiler = "com.google.dagger:hilt-android-compiler:$hiltVersion"

    val viewModule = "androidx.hilt:hilt-lifecycle-viewmodel:$hitViewModule"

    //注解处理器
    val compiler = "androidx.hilt:hilt-compiler:$hitViewModule"
}

/**
 *   数据库 Room
 *   https://developer.android.google.cn/jetpack/androidx/releases/room?hl=zh_cn
 */
object Room {
    val room_version = "2.2.5"
    val runtime = "androidx.room:room-runtime:$room_version"

    //注解处理器
    val compiler = "androidx.room:room-compiler:$room_version"

    // 分页
    val paging = "androidx.room:room-paging:$room_version"
}

/**
 *  JSON Gson
 *  https://github.com/google/gson/tags
 */
object Gson {
    val gson = "com.google.code.gson:gson:2.8.9"
}

/**
 *  内存检测 Leakcanary
 *  https://github.com/square/leakcanary/tags
 */
object Leakcanary {
    val leakcanary = "com.squareup.leakcanary:leakcanary-android:2.8.1"
}

/**
 *  RC  适配器 BaseRecyclerViewAdapterHelper
 *  https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
object BaseRecyclerViewAdapterHelper {

    val baseRecyclerViewAdapterHelper = "com.github.CymChad:BaseRecyclerViewAdapterHelper:3.0.4"
}

/**
 *   下拉刷新  ultra-ptr
 *   https://github.com/liaohuqiu/android-Ultra-Pull-To-Refresh
 */
object UltraPtr {

    val ultraPtr = "in.srain.cube:ultra-ptr:1.0.11"
}

/**
 *  压缩 Luban
 *  https://github.com/Curzibn/Luban/tree/master
 */
object Luban {
    //  不支持webp png 不压缩
    val luban = "top.zibin:Luban:1.1.8"
}

/**
 *  屏幕适配
 *  https://github.com/huangqiqiang/IDimensCompat
 */
object DimensCompat {
    val iDimensCompat = "com.github.huangqiqiang:IDimensCompat:1.0.1"
}

/**
 *  相册
 *  https://github.com/huangqiqiang/AlbumApp/tags
 */
object Album {
    val iHAlbum = "com.github.huangqiqiang:AlbumApp:1.1.30"
}

/**
 *  XML 解析器
 *  https://github.com/jhy/jsoup
 */
object Jsoup {
    val jsoup = "org.jsoup:jsoup:1.14.1"

}
