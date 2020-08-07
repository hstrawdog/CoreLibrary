package com.hqq.core.widget.wheel.model

import java.io.Serializable

/**
 * 用于滑轮选择器展示的条目
 * <br></br>
 * Author:李玉江[QQ:1032694760]
 * DateTime:2017/04/17 00:28
 * Builder:Android Studio
 *
 */
interface WheelItem : Serializable {
    val name: String
}