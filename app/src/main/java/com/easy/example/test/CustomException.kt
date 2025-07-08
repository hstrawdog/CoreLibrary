package com.easy.example.test

/**
 * @Author : huangqiqiang
 * @Package : com.sww.asp.net
 * @Date : 14:06
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class CustomException(message: String, cause: Throwable? = null) : Exception(message, cause) {
    // 可以添加额外的属性或方法来传递更多的错误信息
    var errorCode: String? = null

    constructor(message: String, errorCode: String, cause: Throwable? = null) : this(message, cause) {
        this.errorCode = errorCode
    }

    override fun toString(): String {
        return "CustomException(errorCode=$errorCode, message=${message}, cause=${cause})"
    }
}

