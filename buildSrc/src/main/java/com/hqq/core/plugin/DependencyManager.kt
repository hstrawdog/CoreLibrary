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


object Versions {

    val appcompat = "1.1.0"
    val coreKtx = "1.3.0"
    val constraintlayout = "2.0.0-beta3"
    val paging = "3.0.0-alpha02"
    val kotlin = "1.4.21"
    val work = "2.2.0"
    val cardview = "1.0.0"
    val recyclerview = "1.0.0"
    val fragment = "1.3.0-alpha06"
    val swiperefreshlayout = "1.0.0"
    val appStartup = "1.0.0-alpha01"
    val lifecycle_version = "2.2.0"
    val viewPage2 = "1.0.0"


}

/**
 *  Androidx  jetpack
 */
object AndroidX {
    val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    val constraintlayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintlayout}"
    val pagingRuntime = "androidx.paging:paging-runtime:${Versions.paging}"
    val workRuntime = "androidx.work:work-runtime:${Versions.work}"
    val workTesting = "androidx.work:work-testing:${Versions.work}"
    val cardview = "androidx.cardview:cardview:${Versions.cardview}"
    val recyclerview = "androidx.recyclerview:recyclerview:${Versions.recyclerview}"
    val swiperefreshlayout = "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swiperefreshlayout}"
    val appStartup = "androidx.startup:startup-runtime:${Versions.appStartup}"
    val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle_version}"
    val liveDataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle_version}"
    val runTimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle_version}"
    val viewpager2 = "androidx.viewpager2:viewpager2:${Versions.viewPage2}"

    // fragment
    val runtime = "androidx.fragment:fragment:${Versions.fragment}"
    val runtimeKtx = "androidx.fragment:fragment-ktx:${Versions.fragment}"

    // 目测是用于测试的一个库 好像也支持Dagger的注入
    val testing = "androidx.fragment:fragment-testing:${Versions.fragment}"
}

/**
 *   AndroidX  Compose   meteria
 *   包含7个子包
 *   介绍:  https://developer.android.google.cn/topic/libraries/support-library/features#material-design
 *   详细内容: https://developer.android.google.cn/jetpack/androidx/releases/compose?hl=zh_cn
 *
 */
object Meteria {
    var materialVersion = "1.1.0"
    val meteria = "androidx.compose.material:material:$materialVersion"

    //在 Jetpack Compose 应用中构建动画，丰富用户的体验
    var animationVersion = "1.0.5"
    val animation = "androidx.compose.animation:animation:$animationVersion"

    // 借助 Kotlin 编译器插件，转换 @Composable functions（可组合函数）并启用优化功能。
    var compilerVersion = "1.0.5"
    val compiler = "androidx.compose.compiler:compiler:$compilerVersion"

}




/**
 *  Android  Activity
 * https://developer.android.google.cn/jetpack/androidx/releases/activity#kts
 */
object Activity {
    var activity_version = "1.4.0"

    // java  貌似这个是默认导入的
    val activity = "androidx.activity:activity:$activity_version"

    //Kotlin 拓展包
    val activityKtx = "androidx.activity:activity-ktx:$activity_version"

}



/**
 *  KT 相关
 */
object Kt {
    val stdlibJdk7 = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    val stdlibJdk8 = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
    val test = "org.jetbrains.kotlin:kotlin-test-junit:${Versions.kotlin}"
    val plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
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
    val room_version = "2.4.1"
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

    val baseRecyclerViewAdapterHelper = "com.github.CymChad:BaseRecyclerViewAdapterHelper:3.0.7"
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
