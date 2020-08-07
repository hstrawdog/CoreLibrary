package com.hqq.core.permission;

import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.content.ContextCompat;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.permission
 * @FileName :   IPermissionActions
 * @Date : 2019/6/6 0006  上午 11:04
 * @Email : qiqiang213@gmail.com
 * @Descrive : 代理关键接口
 */
public interface IPermissionActions {

    /**
     * 判断是否有权限
     *
     * @param context
     * @param permissions
     * @return
     */
    static boolean hasPermission(Context context, String... permissions) {
        boolean has = permissions != null && permissions.length > 0;
        if (has) {
            for (String permission : permissions) {
                has = has && ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
            }
        }
        return has;
    }


    /**
     * 请求权限
     *
     * @param permissions 权限组
     * @param listener    回调
     */
    void requestPermissions(String[] permissions, PermissionsResult listener);


}
