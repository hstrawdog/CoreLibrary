package com.hqq.example.ui.jetpack.hilt

import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui.jetpack.hilt
 * @FileName :   AppModule
 * @Date : 2020/8/13 0013  下午 4:55
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
object AppAnnotation {
    @Qualifier
    @Retention(RUNTIME)
    annotation class AnalyticsServiceImpl

    @Qualifier
    @Retention(RUNTIME)
    internal annotation class AnalyticsServiceImpl2

}
