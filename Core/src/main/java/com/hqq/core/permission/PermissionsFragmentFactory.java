package com.hqq.core.permission;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.hqq.core.CoreBuildConfig;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.permission
 * @FileName :   PermissionsFragmentFactory
 * @Date : 2019/6/6 0006  上午 10:45
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public class PermissionsFragmentFactory {

    private static String PERMISSIONS_FRAGMENT_TAG = "PermissionsFragment";

    public static PermissionsFragment getPermissionsFragment(Activity currActivity) {
        PermissionsFragment permissionsFragment;
        if (currActivity instanceof AppCompatActivity) {
            FragmentManager fragmentManager = ((AppCompatActivity) currActivity).getSupportFragmentManager();
            permissionsFragment = (PermissionsFragment) fragmentManager.findFragmentByTag(PERMISSIONS_FRAGMENT_TAG);
            if (null == permissionsFragment) {
                permissionsFragment = PermissionsFragment.newInstance();
                fragmentManager.beginTransaction().add(permissionsFragment, PERMISSIONS_FRAGMENT_TAG).commitNow();
            }
        } else {
            throw new IllegalStateException("不支持 Activity 需要继承 AppCompatActivity ");
        }

        return permissionsFragment;
    }
}
