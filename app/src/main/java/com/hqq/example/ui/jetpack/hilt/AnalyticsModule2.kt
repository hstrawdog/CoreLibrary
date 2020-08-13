package com.hqq.example.ui.jetpack.hilt

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui.jetpack.hilt
 * @FileName :   AnalyticsModule2
 * @Date  : 2020/8/13 0013  下午 5:57
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
@Module
@InstallIn(ActivityComponent::class)
abstract  class AnalyticsModule2 {
    @Binds
    abstract fun bindAny(user: AnalyticsAdapter): Any

}