package com.hqq.example.ui.jetpack.hilt

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui.jetpack.hilt
 * @FileName :   AnalyticsModule2
 * @Date : 2020/8/13 0013  下午 4:40
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
@InstallIn(ActivityComponent::class)
@Module
object AnalyticsModule {
    @JvmStatic
    @Provides
    fun provideAnalyticsService(): AnalyticsService {
        return AnalyticsServiceImpl()
    }
}