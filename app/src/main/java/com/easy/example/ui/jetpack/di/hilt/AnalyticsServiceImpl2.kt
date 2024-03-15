package com.easy.example.ui.jetpack.di.hilt

import javax.inject.Inject

/**
 * @Author : huangqiqiang
 * @Package : com.easy.example.ui.jetpack.di.hilt
 * @FileName :   AnalyticsServiceImpl
 * @Date : 2020/8/13 0013  下午 4:19
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class AnalyticsServiceImpl2 @Inject constructor() : AnalyticsService {
    override fun analyticsMethods(): String {
        return " 我实现的是 AnalyticsServiceImpl2"
    }
}