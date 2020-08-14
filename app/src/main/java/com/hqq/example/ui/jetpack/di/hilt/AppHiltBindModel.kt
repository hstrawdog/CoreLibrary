package com.hqq.example.ui.jetpack.di.hilt

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Qualifier

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui.jetpack.di.hilt
 * @FileName :   AppHiltBindModel
 * @Date : 2020/8/14 0014  上午 11:03
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
@Module
@InstallIn(ActivityComponent::class)
abstract class AppHiltBindModel {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class Binds1

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class Binds2


    /**
     * 如果 AnalyticsService 是一个接口，则您无法通过构造函数注入它，而应向 Hilt 提供绑定信息，方法是在 Hilt 模块内创建一个带有 @Binds 注释的抽象函数。
     *
     *  理解 应该是将所有的   AnalyticsService 接口 都用  AnalyticsServiceImpl 给 实现
     */
//    @Binds1
//    @Binds
//    abstract fun bindAnalyticsService(analyticsServiceImpl: AnalyticsServiceImpl): AnalyticsService

    @Binds2
    @Binds
    abstract fun bindAnalyticsService2(analyticsServiceImpl: AnalyticsServiceImpl2): AnalyticsService


}