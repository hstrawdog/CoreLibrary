package com.easy.readbook.bean

import android.app.Activity

/**
 * @Author : huangqiqiang
 * @Package : com.easy.readbook.bean
 * @Date : 下午 4:56
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
data class ActivityBean(var titile: String, var className: Class<out Activity> ) {

}