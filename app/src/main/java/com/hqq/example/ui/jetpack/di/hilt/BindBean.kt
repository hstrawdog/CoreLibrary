package com.hqq.example.ui.jetpack.di.hilt

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui.jetpack.di.hilt
 * @FileName :   BindBean
 * @Date : 2020/8/14 0014  上午 11:00
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */

class BindBean @Inject constructor(@ApplicationContext val context: Context, val server: AnalyticsService) {
//  @ApplicationContext 默认的限定符 会自动注入 ApplicationContext 对象 这边也就是APP类
    // 当然也可以通过 @Binds  与 @Provides 进行注入

    var name = "名称  BindBean"

}