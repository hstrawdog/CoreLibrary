package com.hqq.example.ui.jetpack.di.hilt

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Qualifier

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui.jetpack.di.hilt
 * @FileName :   UserHiltModel
 * @Date : 2020/8/13 0013  上午 10:41
 * @Email : qiqiang213@gmail.com
 * @Descrive : 自定义的构建方式  如果没有module  就会自动构建默认 值
 */
@InstallIn(ApplicationComponent::class)
@Module
object AppHiltModel {


    /**
     * 等同 用法
     *  @Binds
     *  abstract fun bindAnalyticsService(analyticsServiceImpl: AnalyticsServiceImpl): AnalyticsService
     *
     *
     * AppHiltBindModel.Binds1  如果卸载  Binds 中会报错
     */
    @AppHiltBindModel.Binds1
    @Provides
    fun getBinds(analyticsServiceImpl: AnalyticsServiceImpl): AnalyticsService {
        return analyticsServiceImpl
    }


    @Provides
    fun getBindBean(@ApplicationContext context: Context, @AppHiltBindModel.Binds1 service: AnalyticsService): BindBean {
        return BindBean(context, service)
    }

    @Provides
    fun UserMood(): Hair {
        var mood = Hair()
        mood.color = "黄色";
        return mood;
    }

    /**
     *  构造 UserHilt 的注入
     */
    @Provides
    fun userHilt(har: Hair): UserHilt {
        var userHilt = UserHilt()
        userHilt.mood = "有点好";
        userHilt.hair = har
        return userHilt;
    }


    /**********************************自定义限定符********************************************/

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class AnalyticsService1

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class AnalyticsService2


    @AnalyticsService1
    @Provides
    fun provideAuthInterceptorOkHttpClient(
            otherInterceptor: AnalyticsServiceImpl
    ): AnalyticsAdapter {
        return AnalyticsAdapter(otherInterceptor)
    }


    @AnalyticsService2
    @Provides
    fun provideOtherInterceptorOkHttpClient(
            otherInterceptor: AnalyticsServiceImpl2
    ): AnalyticsAdapter {
        return AnalyticsAdapter(otherInterceptor)
    }


}