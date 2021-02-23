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
    @JvmStatic
    fun requestStorage(permissionsResult: PermissionsResult?) {
        FragmentProxy().requestPermissions(IPermissionsHas.storage, permissionsResult)
    }

    /**
     * 获取摄像头权限
     *
     * @param permissionsResult
     */
    @JvmStatic
    fun requestCamera(permissionsResult: PermissionsResult?) {
        FragmentProxy().requestPermissions(IPermissionsHas.camera, permissionsResult)
    }

    /**
     * 定位权限
     *
     * @param permissionsResult
     */
    @JvmStatic
    fun requestLocation(permissionsResult: PermissionsResult?) {
        FragmentProxy().requestPermissions(IPermissionsHas.location, permissionsResult)
    }

    @JvmStatic
    fun requestBluetooth(permissionsResult: PermissionsResult) {
        // 蓝牙权限 需要定位权限  定位权限需要 读写权限
        FragmentProxy().requestPermissions(IPermissionsHas.bluetooth.plus(IPermissionsHas.location)
                .plus(IPermissionsHas.storage), permissionsResult)
    }
}