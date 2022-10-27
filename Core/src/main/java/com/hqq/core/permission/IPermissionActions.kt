package com.hqq.core.permission

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.permission
 * @FileName :   IPermissionActions
 * @Date : 2019/6/6 0006  上午 11:04
 * @Email : qiqiang213@gmail.com
 * @Descrive : 代理关键接口
 */
interface IPermissionActions {
    companion object {
        /**
         * 判断是否有权限
         *
         * @param context
         * @param permissions
         * @return
         */
        fun hasPermission(context: Context?, vararg permissions: String?): Boolean {
            var has = permissions.isNotEmpty()
            if (has) {
                for (permission in permissions) {
                    val pre = context?.checkSelfPermission(permission!!) == PackageManager.PERMISSION_GRANTED
                    if (context != null) {
                        if (permission != null) {
                            PermissionChecker.checkSelfPermission(context, permission)
                            ContextCompat.checkSelfPermission(context, permission)
                            context.checkSelfPermission(permission)
                        }
                    }
                    has = has && pre
                }
            }
            return has
        }
    }

    /**
     * 请求权限
     *
     * @param permissions 权限组
     * @param listener    回调
     */
    fun requestPermissions(permissions: Array<String>, listener: PermissionsResult?)

}