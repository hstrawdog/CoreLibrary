package com.hqq.core.permission;

import android.Manifest;
import android.support.v7.app.AppCompatActivity;

/**
 * @Author : huangqiqiang
 * @Package : com.core.library.permission
 * @FileName :   PermissionsUtils
 * @Date : 2018/11/22 0022  上午 10:47
 * @Descrive :
 * @Email :
 */
public class PermissionsUtils {
    /**
     * @param context
     * @param permissionsResult
     */
    public static void showCameraFragment(AppCompatActivity context, PermissionsResult permissionsResult) {
        PermissionsFragment mPermissionsFragment = PermissionsFragment.newInstance();
        if (!new IPermissionsImpl().hasCamera(context)) {
            mPermissionsFragment.setPermissionsResult(permissionsResult);
            context.getSupportFragmentManager().beginTransaction().add(mPermissionsFragment, "PermissionsFragment").commitNow();
            mPermissionsFragment.requestPermissions(new String[]{Manifest.permission.CAMERA});
        } else {
            permissionsResult.PermissionsResult(true);
        }
    }

    /**
     * @param context
     * @param permissionsResult
     */
    public static void showStorageFragment(AppCompatActivity context, PermissionsResult permissionsResult) {
        PermissionsFragment mPermissionsFragment = PermissionsFragment.newInstance();
        if (!new IPermissionsImpl().hasStorage(context)) {
            mPermissionsFragment.setPermissionsResult(permissionsResult);
            context.getSupportFragmentManager().beginTransaction().add(mPermissionsFragment, "PermissionsFragment").commitNow();
            mPermissionsFragment.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,});
        } else {
            permissionsResult.PermissionsResult(true);
        }
    }
}
