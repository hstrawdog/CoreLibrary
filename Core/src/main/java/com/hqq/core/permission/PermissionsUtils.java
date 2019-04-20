package com.hqq.core.permission;

import android.Manifest;
import android.support.annotation.NonNull;
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
    public static void showFragment(AppCompatActivity context) {
        PermissionsFragment mPermissionsFragment;
        mPermissionsFragment = new PermissionsFragment();
        if (! new IPermissionsImpl().hasCamera(context)) {
            mPermissionsFragment.setIPermissionsResult(new IPermissionsResult() {
                @Override
                public void PermissionsResult(@NonNull String[] permissions, @NonNull int[] grantResults) {

                }
            });
            context.getSupportFragmentManager().beginTransaction().add(mPermissionsFragment, "123").commitNow();
            mPermissionsFragment.requestPermissions(new String[]{Manifest.permission.CAMERA,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.SEND_SMS });
        }
    }
}
