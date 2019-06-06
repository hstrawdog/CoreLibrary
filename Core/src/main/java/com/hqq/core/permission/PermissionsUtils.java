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
     * @param context
     * @param permissionsResult
     */
    @Deprecated
    public static void showCameraFragment(AppCompatActivity context, PermissionsResult permissionsResult) {
        PermissionsFragment mPermissionsFragment = PermissionsFragment.newInstance();
        if (!new PermissionsHasImpl().hasCamera(context)) {
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
    @Deprecated
    public static void showStorageFragment(AppCompatActivity context, PermissionsResult permissionsResult) {
        PermissionsFragment mPermissionsFragment = PermissionsFragment.newInstance();
        if (!new PermissionsHasImpl().hasStorage(context)) {
            mPermissionsFragment.setPermissionsResult(permissionsResult);
            context.getSupportFragmentManager().beginTransaction().add(mPermissionsFragment, "PermissionsFragment").commitNow();
            mPermissionsFragment.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE});
        } else {
            permissionsResult.PermissionsResult(true);
        }
    }

    /**
     * @param permissionsResult
     */
    public static void requestStorage(PermissionsResult permissionsResult) {
        if (!new PermissionsHasImpl().hasStorage(CoreBuildConfig.getInstance().getApplication())) {
            String[] premissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
            new FragmentProxy().requestPermissions(premissions, permissionsResult);
        } else {
            permissionsResult.PermissionsResult(true);
        }
    }

    public static void showCameraFragment(PermissionsResult permissionsResult) {
        if (!new PermissionsHasImpl().hasCamera(CoreBuildConfig.getInstance().getApplication())) {
            String[] premissions = new String[]{Manifest.permission.CAMERA};
            new FragmentProxy().requestPermissions(premissions, permissionsResult);
        } else {
            permissionsResult.PermissionsResult(true);
        }
    }
}
