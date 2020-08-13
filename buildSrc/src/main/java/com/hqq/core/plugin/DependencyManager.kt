package com.hqq.core.plugin

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core
 * @FileName :   DependencyManager
 * @Date  : 2020/8/11 0011  下午 2:12
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
object Versions {
    val retrofit = "2.9.0"
    val okhttpLogging = "4.8.0"
    val appcompat = "1.1.0"
    val coreKtx = "1.3.0"
    val constraintlayout = "2.0.0-beta3"
    val paging = "3.0.0-alpha02"
    val timber = "4.7.1"
    val kotlin = "1.3.72"
    val koin = "2.1.5"
    val work = "2.2.0"
    val room = "2.3.0-alpha01"
    val cardview = "1.0.0"
    val recyclerview = "1.0.0"
    val fragment = "1.3.0-alpha06"
    val anko = "0.10.8"
    val swiperefreshlayout = "1.0.0"
    val junit = "4.12"
    val junitExt = "1.1.1"
    val espressoCore = "3.2.0"
    val jDatabinding = "1.0.1"
    val progressview = "1.0.0"
    val runtime = "0.11.0"
    val hit = "2.28-alpha"
    val hitViewModule = "1.0.0-alpha01"
    val appStartup = "1.0.0-alpha01"
    val material = "1.2.0-alpha06"
    val lifecycle_version = "2.2.0"
}

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
    val viewpager2="androidx.viewpager2:viewpager2:1.0.0"
}

object Android {
    val meteria = "com.google.android.material:material:${Versions.material}"
    val activityKtx = "androidx.activity:activity-ktx:${Versions.material}"
    val databindingCompiler = "com.android.databinding:compiler:4.0.1"
}

object Fragment {
    val runtime = "androidx.fragment:fragment:${Versions.fragment}"
    val runtimeKtx = "androidx.fragment:fragment-ktx:${Versions.fragment}"
    val testing = "androidx.fragment:fragment-testing:${Versions.fragment}"
}

object Retrofit {
    val okhttp = "com.squareup.okhttp3:okhttp:4.2.1"
    val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    val converterGson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    val mock = "com.squareup.retrofit2:retrofit-mock:${Versions.retrofit}"
    val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttpLogging}"
}

object Kt {
    val stdlibJdk7 = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    val stdlibJdk8 = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
    val test = "org.jetbrains.kotlin:kotlin-test-junit:${Versions.kotlin}"
    val plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
}

object Glide {
    val glide = "com.github.bumptech.glide:glide:4.11.0"
    val glideCompiler = "com.github.bumptech.glide:compiler:4.11.0"
}

object Hilt {
    val daggerRuntime = "com.google.dagger:hilt-android:${Versions.hit}"
    val daggerCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hit}"
    val viewModule = "androidx.hilt:hilt-lifecycle-viewmodel:${Versions.hitViewModule}"
    val compiler = "androidx.hilt:hilt-compiler:${Versions.hitViewModule}"
}
object Gson {
    val gson = "com.google.code.gson:gson:2.8.6"
}

object Leakcanary {
    val leakcanary = "com.squareup.leakcanary:leakcanary-android:2.4"
}

object BaseRecyclerViewAdapterHelper {
    val baseRecyclerViewAdapterHelper = "com.github.CymChad:BaseRecyclerViewAdapterHelper:3.0.4"
}

object UltraPtr {
    val ultraPtr = "in.srain.cube:ultra-ptr:1.0.11"
}

object Luban {
    val luban = "top.zibin:Luban:1.1.8"
}

object IDimensCompat {
    val iDimensCompat = "com.github.huangqiqiang:IDimensCompat:1.0.1"
}

object IHAlbum {
    val iHAlbum="com.github.huangqiqiang:AlbumApp:1.0.10"
}