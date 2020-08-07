package com.hqq.core.permission;

import android.Manifest;

import com.hqq.core.CoreBuildConfig;

/**
 * @Author : huangqiqiang
 * @Package : com.core.library.permission
 * @FileName :   PermissionsUtils
 * @Date : 2018/11/22 0022  上午 10:47
 * @Email :  qiqiang213@gmail.com
 * @Descrive :   PermissionsFragment   需要用 工厂或者代理来生成
 */
public class PermissionsUtils {

    /**
     * 读写文件的权限
     *
     * @param permissionsResult
     */
    public static void requestStoragePermission(PermissionsResult permissionsResult) {
        new FragmentProxy().requestPermissions(IPermissionsHas.getStorage()
                , permissionsResult);
    }


    /**
     * 获取摄像头权限
     *
     * @param permissionsResult
     */
    public static void requestCameraPermission(PermissionsResult permissionsResult) {
        new FragmentProxy().requestPermissions(IPermissionsHas.getCamera(), permissionsResult);
    }

    /**
     * 定位权限
     *
     * @param permissionsResult
     */
    public static void requestLocationPermission(PermissionsResult permissionsResult) {
        new FragmentProxy().requestPermissions(IPermissionsHas.getLocation(), permissionsResult);
    }


}


