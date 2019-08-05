package com.hqq.core.permission;

import android.Manifest;
import android.support.v7.app.AppCompatActivity;

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
     * @param permissionsResult
     */
    public static void requestStorage(PermissionsResult permissionsResult) {
        if (!new PermissionsHasImpl().hasStorage(CoreBuildConfig.getInstance().getApplication())) {
            new FragmentProxy().requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, permissionsResult);
        } else {
            permissionsResult.onPermissionsResult(true);
        }
    }

    /**
     * @param permissionsResult
     */
    public static void showCameraFragment(PermissionsResult permissionsResult) {
        if (!new PermissionsHasImpl().hasCamera(CoreBuildConfig.getInstance().getApplication())) {
            new FragmentProxy().requestPermissions(new String[]{Manifest.permission.CAMERA}, permissionsResult);
        } else {
            permissionsResult.onPermissionsResult(true);
        }
    }
}
