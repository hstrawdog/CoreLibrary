package com.easy.core.permission

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import androidx.fragment.app.FragmentManager
import com.easy.core.CoreConfig

/**
 * @Author : huangqiqiang
 * @Package :  com.easy.core.permission
 * @FileName :   IPermissionActions
 * @Date : 2019/6/6 0006  上午 11:04
 * @Email : qiqiang213@gmail.com
 * @Describe : 代理关键接口
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
                    var pre = context?.checkSelfPermission(permission!!) == PackageManager.PERMISSION_GRANTED
                    if (context != null) {
                        if (permission != null) {
                            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                                pre = false
                            } else {
                                pre = true
                            }
//                            PermissionChecker.checkSelfPermission(context, permission)
//                            ContextCompat.checkSelfPermission(context, permission)
//                            context.checkSelfPermission(permission)
                        }
                    }
                    has = has && pre
                }
            }
            return has
        }
    }

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
                var pre = context?.checkSelfPermission(permission!!) == PackageManager.PERMISSION_GRANTED
                if (context != null) {
                    if (permission != null) {
                        if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                            pre = false
                        } else {
                            pre = true
                        }
//                            PermissionChecker.checkSelfPermission(context, permission)
//                            ContextCompat.checkSelfPermission(context, permission)
//                            context.checkSelfPermission(permission)
                    }
                }
                has = has && pre
            }
        }
        return has
    }

    /**
     * 请求权限
     *
     * @param permissions 权限组
     * @param listener    回调
     */
    fun requestPermissions(fragmentManager: FragmentManager, permissions: Array<String>, listener: PermissionsResult?)

    /**
     *
     * @param permissions Array<String>
     * @param listener PermissionsResult?
     * @param isShowTip Boolean   是否 需要显示 提示
     */
    fun requestPermissions(fragmentManager: FragmentManager, permissions: Array<String>, listener: PermissionsResult?, isShowTip: Boolean, tipString: String)
    fun requestPermissions(fragmentManager: FragmentManager, permissions: Array<String>, listener: PermissionsResult?,tipString: String)
}