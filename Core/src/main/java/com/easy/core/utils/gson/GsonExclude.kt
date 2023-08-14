package com.easy.core.utils.gson

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * @Author : huangqiqiang
 * @Package : com.qq.readbook.bean.book
 * @Date : 下午 5:54
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
@Target(AnnotationTarget.FIELD)
@Retention(RetentionPolicy.RUNTIME)
annotation class GsonExclude