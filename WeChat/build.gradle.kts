import  com.hqq.core.plugin.*

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id ("kotlin-kapt")

}

android {
    namespace = "com.hqq.wechat"
    compileSdk = BuildVersionConfig.compileSdkVersion

    defaultConfig {
        minSdk = BuildVersionConfig.minSdkVersion
        targetSdk = BuildVersionConfig.targetSdkVersion

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    api (project(":Core"))
    //微信登录，支付，分享包
    implementation("com.tencent.mm.opensdk:wechat-sdk-android-with-mta:1.4.0")
}