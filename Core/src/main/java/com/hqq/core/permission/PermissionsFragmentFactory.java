package com.hqq.core.permission;

import android.app.Activity;

import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.permission
 * @FileName :   PermissionsFragmentFactory
 * @Date : 2019/6/6 0006  上午 10:45
 * @Email : qiqiang213@gmail.com
 * @Descrive :  工厂 用于生产 PermissionsFragment
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
            throw new IllegalStateException("不支持的Activity  Activity需要继承 AppCompatActivity ");
        }

        return permissionsFragment;
    }
}
