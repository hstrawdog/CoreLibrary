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
    val kotlin = "1.4.0"
    val work = "2.2.0"
    val room = "2.2.5"
    val cardview = "1.0.0"
    val recyclerview = "1.0.0"
    val fragment = "1.3.0-alpha06"
    val anko = "0.10.8"
    val swiperefreshlayout = "1.0.0"
    val hit = "2.28-alpha"
    val hitViewModule = "1.0.0-alpha01"
    val appStartup = "1.0.0-alpha01"
    val material = "1.2.0-alpha06"
    val lifecycle_version = "2.2.0"
    val viewPage2 = "1.0.0"
    val dataBinding_Compoler = "4.0.1"

    val retrofit = "2.9.0"
    val okhttpLogging = "4.8.0"
    val okhttp = "4.2.1"

    val gldie = "4.11.0"

    val gson = "2.8.6"

    val Leakcanary = "2.4"

    val BRVAH = "3.0.4"

    val ultraPtr = "1.0.11"

    val Luban = "1.1.8"
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
}

/**
 * Android
 */
object Android {
    val meteria = "com.google.android.material:material:${Versions.material}"
    val activityKtx = "androidx.activity:activity-ktx:${Versions.material}"
    val dataBindingCompiler = "com.android.databinding:compiler:${Versions.dataBinding_Compoler}"
}

/**
 *   Fragment
 */
object Fragment {
    val runtime = "androidx.fragment:fragment:${Versions.fragment}"
    val runtimeKtx = "androidx.fragment:fragment-ktx:${Versions.fragment}"

    // 目测是用于测试的一个库 好像也支持Dagger的注入
    val testing = "androidx.fragment:fragment-testing:${Versions.fragment}"
}

/**
 *  网络请求
 */
object Retrofit {
    val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
    val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    val converterGson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    val mock = "com.squareup.retrofit2:retrofit-mock:${Versions.retrofit}"
    val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttpLogging}"
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
 * 图片加载
 */
object Glide {
    val glide = "com.github.bumptech.glide:glide:${Versions.gldie}"
    val glideCompiler = "com.github.bumptech.glide:compiler:${Versions.gldie}"
}

/**
 *  注解 依赖注入   dagger
 */
object Hilt {
    val daggerRuntime = "com.google.dagger:hilt-android:${Versions.hit}"
    val daggerCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hit}"
    val viewModule = "androidx.hilt:hilt-lifecycle-viewmodel:${Versions.hitViewModule}"
    val compiler = "androidx.hilt:hilt-compiler:${Versions.hitViewModule}"
}

/**
 *   数据库
 */
object Room {
    val runtime = "androidx.room:room-runtime:${Versions.room}"
    val compiler = "androidx.room:room-compiler:${Versions.room}"
}

/**
 *  JSON
 */
object Gson {
    val gson = "com.google.code.gson:gson:${Versions.gson}"
}

/**
 *  内存检测
 */
object Leakcanary {
    val leakcanary = "com.squareup.leakcanary:leakcanary-android:${Versions.Leakcanary}"
}

/**
 *  RC  适配器
 */
object BaseRecyclerViewAdapterHelper {
    val baseRecyclerViewAdapterHelper = "com.github.CymChad:BaseRecyclerViewAdapterHelper:${Versions.BRVAH}"
}

/**
 *   下拉刷新
 */
object UltraPtr {
    val ultraPtr = "in.srain.cube:ultra-ptr:${Versions.ultraPtr}"
}

/**
 *  压缩
 */
object Luban {
    val luban = "top.zibin:Luban:${Versions.Luban}"
}

/**
 *  屏幕适配
 */
object DimensCompat {
    val iDimensCompat = "com.github.huangqiqiang:IDimensCompat:1.0.1"
}

/**
 *  相册
 */
object Album {
    val iHAlbum = "com.github.huangqiqiang:AlbumApp:1.0.10"
}

/**
 *  XML 解析器
 */
object Jsoup {
    val jsoup = "org.jsoup:jsoup:1.13.1"

}
