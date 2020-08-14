package com.hqq.example.ui.jetpack.di.hilt

import javax.inject.Inject

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui.jetpack.di.hilt
 * @FileName :   User
 * @Date : 2020/8/13 0013  上午 10:18
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
class UserHilt {

    /**
     *  Inject 表示  ViewModel  才能自动注入
     *  或者使用 provides  自定义注入的属性值
     */
    @Inject
    constructor()

    var name: String = "小强"
    var mood: String = "很好"
    var hair: Hair = Hair()
}