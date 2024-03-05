package com.easy.core.plugin

/**
 * @Author : huangqiqiang
 * @Package : com.easy.core
 * @FileName :   DependencyManager
 * @Date  : 2020/8/11 0011  下午 2:12
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
object BuildVersionConfig {
    const val compileSdkVersion = 31
    const val buildToolsVersion = "31.0.0"
    const val minSdkVersion = 23
    const val targetSdkVersion = 31
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
    const val core_version = "1.6.1"
    const val core = "androidx.core:core:$core_version"
    const val coreKtx = "androidx.core:core-ktx:$core_version"
    const val appcompat = "androidx.appcompat:appcompat:1.3.1"
}

/**
 * Kotlin 拓展包
 * https://developer.android.google.cn/kotlin/ktx/extensions-list
 */
object KTX {
    //Kotlin 拓展包
    const val activityKtx = "androidx.activity:activity-ktx:1.3.0"

    // Kotlin
    const val fragmentKtx = "androidx.fragment:fragment-ktx:1.3.6"
}

/**
 *  KT 相关
 */
object Kt {
    const val kotlin = "1.4.21"
    const val stdlibJdk7 = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin"
    const val stdlibJdk8 = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin"
    const val test = "org.jetbrains.kotlin:kotlin-test-junit:$kotlin"
    const val plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin"
}

/**
 * AndroidX 组件
 *
 */
object Components {

    //  CardView
    // https://developer.android.google.cn/jetpack/androidx/releases/cardview
    const val cardview = "androidx.cardview:cardview:1.0.0"

    // ViewPage2
    // https://developer.android.google.cn/jetpack/androidx/releases/viewpager2
    const val viewpager2 = "androidx.viewpager2:viewpager2:1.0.0"

    // constraintLayout
    // https://developer.android.google.cn/jetpack/androidx/releases/constraintlayout
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.1.2"

    // https://developer.android.google.cn/jetpack/androidx/releases/recyclerview
    const val recyclerview = "androidx.recyclerview:recyclerview:1.2.1"
    const val recyclerViewSelection = "androidx.recyclerview:recyclerview-selection:1.1.0"

    //https://developer.android.google.cn/jetpack/androidx/releases/swiperefreshlayout
    const val swiperefreshlayout = "androidx.swiperefreshlayout:swiperefreshlayout:1.0.0"

    //
    const val meteria = "com.google.android.material:material:1.3.0"

}

/**
 * Lifecycle Ktx
 * https://developer.android.google.cn/jetpack/androidx/releases/lifecycle
 */
object Lifecycle {
    const val lifecycle_version = "2.4.0"
    const val arch_version = "2.1.0"

    // ViewModel
    const val lifecycleViewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version"

    // ViewModel utilities for Compose
    const val lifecycle_viewModel_compose = "androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycle_version"

    // LiveData
    const val lifecycleLivedataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version"

    // 仅生命周期（没有 ViewModel 或 LiveData）
    const val lifecycleRunTimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycle_version"

    // ViewModel 的已保存状态模块
    const val lifecycle_viewmodel_savedstate = "androidx.lifecycle:lifecycle-viewmodel-savedstate:$lifecycle_version"

    // Annotation processor
    const val lifecycle_compiler = "androidx.lifecycle:lifecycle-compiler:$lifecycle_version"

    // 或者 - 如果使用 Java8，请使用以下内容而不是生命周期编译器
    const val lifecycle_common_java8 = "androidx.lifecycle:lifecycle-common-java8:$lifecycle_version"

    // optional - helpers for implementing LifecycleOwner in a Service
    const val lifecycle_service = "androidx.lifecycle:lifecycle-service:$lifecycle_version"

    // optional - ProcessLifecycleOwner provides a lifecycle for the whole application process
    const val lifecycle_process = "androidx.lifecycle:lifecycle-process:$lifecycle_version"

    // optional - ReactiveStreams support for LiveData
    const val lifecycle_reactivestreams_ktx = "androidx.lifecycle:lifecycle-reactivestreams-ktx:$lifecycle_version"

    // optional - Test helpers for LiveData
    const val core_testing = "androidx.arch.core:core-testing:$arch_version"

}

/**
 *   AndroidX  Compose   meteria
 *   包含7个子包
 *   介绍:  https://developer.android.google.cn/topic/libraries/support-library/features#material-design
 *   详细内容: https://developer.android.google.cn/jetpack/androidx/releases/compose?hl=zh_cn
 *    火狐 版本 对应1.0.1  gradle:7.0.4       gradle-7.0.2-bin.zip
 */
object Compose {

    //在 Jetpack Compose 应用中构建动画，丰富用户的体验
    const val animation = "androidx.compose.animation:animation:1.0.1"

    // 借助 Kotlin 编译器插件，转换 @Composable functions（可组合函数）并启用优化功能。
    const val compiler = "androidx.compose.compiler:compiler:1.0.1"

    //使用现成可用的构建块编写 Jetpack Compose 应用，并扩展 Foundation 以构建您自己的设计系统元素。
    const val foundation = "androidx.compose.foundation:foundation:1.0.1"

    //使用现成可用的 Material Design 组件构建 Jetpack Compose UI。这是更高层级的 Compose 入口点，旨在提供与 www.material.io 上描述的组件一致的组件
    const val meteria = "androidx.compose.material:material:1.0.1"

    //使用 Material Design 3（下一代 Material Design）组件构建 Jetpack Compose 界面。Material 3 包括更新后的主题和组件，以及动态配色等 Material You 个性化功能，旨在与新的 Android 12 视觉风格和系统界面相得益彰
    const val material3 = "androidx.compose.material3:material3:1.0.0-alpha03"

    // Compose 编程模型和状态管理的基本组件，以及 Compose 编译器插件的目标核心运行时。
    const val runtimeVersion = "1.0.1"

    const val runtime = "androidx.compose.runtime:runtime:$runtimeVersion"
    const val runtimeLivedata = "androidx.compose.runtime:runtime-livedata:$runtimeVersion"
    const val runtimeRxjava2 = "androidx.compose.runtime:runtime-rxjava2:$runtimeVersion"

    //与设备互动所需的 Compose UI 的基本组件，包括布局、绘图和输入。
    const val ui = "androidx.compose.ui:ui:1.0.1"

}

/**
 * 可在应用启动时简单、高效地初始化组件。
 * https://developer.android.google.cn/jetpack/androidx/releases/startup
 */
object Startup {
    const val appStartup = "androidx.startup:startup-runtime:1.1.0"
}

/**
 *  网络请求
 *  https://github.com/square/okhttp/tree/master/okhttp-logging-interceptor
 *  https://github.com/square/okhttp/tags
 *  https://github.com/square/retrofit/tags
 */
object Retrofit {
    private const val retrofitVersion = "2.9.0"
    private const val okhttpVersion = "4.9.3"

    const val okhttp = "com.squareup.okhttp3:okhttp:$okhttpVersion"

    // 需要与 okhttp 保持一致
    const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:$okhttpVersion"

    const val retrofit = "com.squareup.retrofit2:retrofit:$retrofitVersion"
    const val converterGson = "com.squareup.retrofit2:converter-gson:$retrofitVersion"
}

/**
 * 图片加载 Glide
 * https://github.com/bumptech/glide/tags
 */
object Glide {
    private const val glideVersion = "4.12.0"
    const val glide = "com.github.bumptech.glide:glide:$glideVersion"
    const val glideCompiler = "com.github.bumptech.glide:compiler:$glideVersion"
}

// -------------  图片预览效果  缩放等 -------------
object PhotoView {
    const val library = "com.bm.photoview:library:1.4.1"
}

object SubsamplingScaleImageView {
    const val core = "com.davemorrissey.labs:subsampling-scale-image-view:3.10.0"
}

/**
 * CoilKt  图片加载器  Kotlin
 *  https://github.com/coil-kt/coil/blob/main/README-zh.md
 */
object CoilKt {
    private const val coilVersion = "2.2.2"

    // 标准加载库
    const val coilKt = "io.coil-kt:coil:$coilVersion"

    // compose 适配
    const val coilCompose = "io.coil-kt:coil-compose:$coilVersion"

    // 加载gif
    const val coilGif = "io.coil-kt:coil-gif:$coilVersion"

    // 加载 svg
    const val coilSvg = "io.coil-kt:coil-svg:$coilVersion"

    // 加载 video
    const val coilVideo = "io.coil-kt:coil-video:$coilVersion"

    const val coilSkyDoves = "com.github.skydoves:landscapist-coil:2.1.2"
}

/**
 *  注解 依赖注入   dagger
 *  https://developer.android.google.cn/jetpack/androidx/releases/hilt?hl=zh_cn
 */
object Hilt {
    private const val hiltVersion = "2.44"
    private const val hitViewModule = "1.0.0-alpha03"

    const val daggerRuntime = "com.google.dagger:hilt-android:$hiltVersion"
    const val hiltCompiler = "com.google.dagger:hilt-compiler:2.44"

    const val daggerCompiler = "com.google.dagger:hilt-android-compiler:$hiltVersion"

//    const val viewModule = "androidx.hilt:hilt-lifecycle-viewmodel:$hitViewModule"

    //注解处理器
//    const val compiler = "androidx.hilt:hilt-compiler:$hitViewModule"
}

/**
 *   数据库 Room
 *   https://developer.android.google.cn/jetpack/androidx/releases/room?hl=zh_cn
 */
object Room {
    const val room_version = "2.2.5"
    const val runtime = "androidx.room:room-runtime:$room_version"

    //注解处理器
    const val compiler = "androidx.room:room-compiler:$room_version"

    // 分页
    const val paging = "androidx.room:room-paging:$room_version"
}

/**
 *  JSON Gson
 *  https://github.com/google/gson/tags
 */
object Gson {
    const val gson = "com.google.code.gson:gson:2.8.9"
}

/**
 *  内存检测 Leakcanary
 *  https://github.com/square/leakcanary/tags
 */
object Leakcanary {
    const val leakcanary = "com.squareup.leakcanary:leakcanary-android:2.8.1"
}

/**
 *  RC  适配器 BaseRecyclerViewAdapterHelper
 *  https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
object BaseRecyclerViewAdapterHelper {
//    const val baseRecyclerViewAdapterHelper = "com.github.CymChad:BaseRecyclerViewAdapterHelper:3.0.6"

    const val baseRecyclerViewAdapterHelper = "io.github.cymchad:BaseRecyclerViewAdapterHelper:3.0.14"
}

/**
 *   下拉刷新  ultra-ptr
 *   https://github.com/liaohuqiu/android-Ultra-Pull-To-Refresh
 */
object UltraPtr {
    const val ultraPtr = "in.srain.cube:ultra-ptr:1.0.11"
}

/**
 *  压缩 Luban
 *  https://github.com/Curzibn/Luban/tree/master
 */
object Luban {
    //  不支持webp png 不压缩
    const val luban = "top.zibin:Luban:1.1.8"
}

/**
 *  屏幕适配
 *  https://github.com/huangqiqiang/IDimensCompat
 */
object DimensCompat {
    const val iDimensCompat = "com.github.huangqiqiang:IDimensCompat:1.0.1"
}

/**
 *  相册
 *  https://github.com/huangqiqiang/AlbumApp/tags
 */
object Album {
    const val iHAlbum = "com.github.huangqiqiang:AlbumApp:1.1.33"
}


/**
 *  XML 解析器
 *  https://github.com/jhy/jsoup
 */
object Jsoup {
    const val jsoup = "org.jsoup:jsoup:1.14.1"

}

/**
 *  Work  任务管理
 *   https://developer.android.com/guide/background/persistent
 */
object Work {
    //    const val work_version = "2.8.0"
    const val work_version = "2.7.1"

    // 核心  正常 使用这个就可以
    const val work = "androidx.work:work-runtime:$work_version"

    // Kotlin + coroutines
    const val work_ktx = "androidx.work:work-runtime-ktx:$work_version"

    // optional - RxJava2 support
    const val work_rxjava2 = "androidx.work:work-rxjava2:$work_version"

    // optional - GCMNetworkManager support
    const val work_gcm = "androidx.work:work-gcm:$work_version"

    // optional - Test helpers androidTestImplementation(
    const val work_testing = "androidx.work:work-testing:$work_version"

    // optional - Multiprocess support
    const val work_multiprocess = "androidx.work:work-multiprocess:$work_version"

}

/**
 *  dataStore  SharedPreferences升级方案
 *  https://developer.android.com/topic/libraries/architecture/datastore?hl=zh-cn
 *
 */
object DataStore {
    //  DataStore  核心
    const val core = "androidx.datastore:datastore-core:1.0.0"

    // preferences DataStore  支持
    const val preferences = "androidx.datastore:datastore-preferences:1.0.0"

    // optional - RxJava2 support
    const val preferences_rxjava = "androidx.datastore:datastore-preferences-rxjava2:1.0.0"

    // optional - RxJava3 support
    const val preferences_rxjava3 = "androidx.datastore:datastore-preferences-rxjava3:1.0.0"

    // datastore  DataStore  支持
    const val datastore = "androidx.datastore:datastore:1.0.0"

    // optional - RxJava2 support
    const val datastore_rxjava2 = "androidx.datastore:datastore-rxjava2:1.0.0"

    // optional - RxJava3 support
    const val datastore_rxjava3 = "androidx.datastore:datastore-rxjava3:1.0.0"
}


