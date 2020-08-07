package com.hqq.core.permission;

import android.Manifest;
import android.content.Context;

import java.util.Arrays;

/**
 * @Author : huangqiqiang
 * @Package : com.core.library.permission
 * @FileName :   PermissionsHasImpl
 * @Date : 2018/11/22 0022  上午 9:57
 * @Email :  qiqiang213@gmail.com
 * @Descrive : 判断是否拥有权限
 */
public class PermissionsHasImpl implements IPermissionsHas {

    /**
     * 权限存在依赖关系  如定位权限 需要读写权限
     * 用吃方法判断
     *
     * @param context
     * @param permissions
     * @return
     */
    @Override
    public boolean hasPermission(Context context, String... permissions) {
        boolean result = true;
        if (permissions != null && permissions.length > 0) {
            for (String permission : permissions) {
                if (Arrays.asList(IPermissionsHas.getCalendar()).contains(permission)) {
                    result = result && hasCalendar(context);
                } else if (Arrays.asList(IPermissionsHas.getCamera()).contains(permission)) {
                    result = result && hasCamera(context);
                } else if (Arrays.asList(IPermissionsHas.getContacts()).contains(permission)) {
                    result = result && hasContacts(context);
                } else if (Arrays.asList(IPermissionsHas.getLocation()).contains(permission)) {
                    result = result && hasLocation(context);
                } else if (Arrays.asList(IPermissionsHas.getMicrophone()).contains(permission)) {
                    result = result && hasMicrophone(context);
                } else if (Arrays.asList(IPermissionsHas.getPhone()).contains(permission)) {
                    result = result && hasPhone(context);
                } else if (Arrays.asList(IPermissionsHas.getSensors()).contains(permission)) {
                    result = result && hasSensors(context);
                } else if (Arrays.asList(IPermissionsHas.getSMS()).contains(permission)) {
                    result = result && hasSMS(context);
                } else if (Arrays.asList(IPermissionsHas.getStorage()).contains(permission)) {
                    result = result && hasStorage(context);
                }
            }
        }
        return result;
    }

    @Override
    public boolean hasCalendar(Context context) {
        //(允许程序读取用户的日程信息)
        return IPermissionActions.hasPermission(context,
                IPermissionsHas.getCalendar()
        );

    }

    @Override
    public boolean hasCamera(Context context) {
        //(允许访问摄像头进行拍照)
        return IPermissionActions.hasPermission(context,
                IPermissionsHas.getCamera());
    }

    @Override
    public boolean hasContacts(Context context) {
        return hasStorage(context) && IPermissionActions.hasPermission(context,
                IPermissionsHas.getContacts()
        );
    }

    @Override
    public boolean hasLocation(Context context) {
        return hasStorage(context) && IPermissionActions.hasPermission(context, IPermissionsHas.getLocation());

    }

    @Override
    public boolean hasMicrophone(Context context) {
        return IPermissionActions.hasPermission(context, IPermissionsHas.getMicrophone());

    }

    @Override
    public boolean hasPhone(Context context) {
        return IPermissionActions.hasPermission(context, IPermissionsHas.getPhone());
    }

    @Override
    public boolean hasSensors(Context context) {
        return IPermissionActions.hasPermission(context, IPermissionsHas.getSensors());
    }

    @Override
    public boolean hasSMS(Context context) {
        return IPermissionActions.hasPermission(context,
                IPermissionsHas.getSMS()
        );
    }

    @Override
    public boolean hasStorage(Context context) {
        return IPermissionActions.hasPermission(context, IPermissionsHas.getStorage());
    }
}
