package com.hqq.example.permission;

import android.Manifest;

import com.hqq.example.CoreBuildConfig;

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
        if (!new PermissionsHasImpl().hasStorage(CoreBuildConfig.getInstance().getApplication())) {
            new FragmentProxy().requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, permissionsResult);
        } else {
            permissionsResult.onPermissionsResult(true);
        }
    }

    /**
     * 获取摄像头权限
     *
     * @param permissionsResult
     */
    public static void requestCameraPermission(PermissionsResult permissionsResult) {
        if (!new PermissionsHasImpl().hasCamera(CoreBuildConfig.getInstance().getApplication())) {
            new FragmentProxy().requestPermissions(new String[]{Manifest.permission.CAMERA}, permissionsResult);
        } else {
            permissionsResult.onPermissionsResult(true);
        }
    }
}


