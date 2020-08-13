package com.hqq.example.ui.jetpack.hilt

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui.jetpack.hilt
 * @FileName :   UserHiltModel
 * @Date : 2020/8/13 0013  上午 10:41
 * @Email : qiqiang213@gmail.com
 * @Descrive : 自定义的构建方式  如果没有module  就会自动构建默认 值
 */
@InstallIn(ApplicationComponent::class)
@Module
object UserHiltModel {

    @Provides
    fun UserMood(): Hair {
        var mood = Hair()
        mood.color = "绿色";
        return mood;
    }

    @Provides
    fun userHilt(har: Hair): UserHilt {
        var user = UserHilt()
        user.mood = "不太好";
        user.hair = har
        return user;
    }


}