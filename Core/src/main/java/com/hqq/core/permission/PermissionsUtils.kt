package com.hqq.core.permission

/**
 * @Author : huangqiqiang
 * @Package : com.core.library.permission
 * @FileName :   PermissionsUtils
 * @Date : 2018/11/22 0022  上午 10:47
 * @Email :  qiqiang213@gmail.com
 * @Descrive :   PermissionsFragment   需要用 工厂或者代理来生成
 */
object PermissionsUtils {
    /**
     * 读写文件的权限
     *
     * @param permissionsResult
     */
    @kotlin.jvm.JvmStatic
    fun requestStoragePermission(permissionsResult: PermissionsResult?) {
        FragmentProxy().requestPermissions(IPermissionsHas.Companion.getStorage()
                , permissionsResult)
    }

    /**
     * 获取摄像头权限
     *
     * @param permissionsResult
     */
    @kotlin.jvm.JvmStatic
    fun requestCameraPermission(permissionsResult: PermissionsResult?) {
        FragmentProxy().requestPermissions(IPermissionsHas.Companion.getCamera(), permissionsResult)
    }

    /**
     * 定位权限
     *
     * @param permissionsResult
     */
    @kotlin.jvm.JvmStatic
    fun requestLocationPermission(permissionsResult: PermissionsResult?) {
        FragmentProxy().requestPermissions(IPermissionsHas.Companion.getLocation(), permissionsResult)
    }
}