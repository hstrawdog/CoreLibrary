package com.easy.core.permission

/**
 * @Author : huangqiqiang
 * @Package : com.core.library.permission
 * @FileName :   onPermissionsResult
 * @Date : 2018/11/22 0022  上午 11:53
 * @Descrive :
 * @Email :  qiqiang213@gmail.com
 */
fun interface PermissionsResult {
    /**
     * 是否拥有权限
     *
     * @param status boolean
     */
    fun onPermissionsResult(status: Boolean)
}