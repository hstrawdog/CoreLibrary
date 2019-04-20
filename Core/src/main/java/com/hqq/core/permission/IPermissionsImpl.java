package com.hqq.core.permission;

import android.Manifest;
import android.content.Context;

/**
 * @Author : huangqiqiang
 * @Package : com.core.library.permission
 * @FileName :   IPermissionsImpl
 * @Date : 2018/11/22 0022  上午 9:57
 * @Descrive :
 * @Email :
 */
public class IPermissionsImpl implements IPermissions {


    @Override
    public boolean hasPermission(Context context, String... permissions) {
        boolean result = true;
        if (permissions != null && permissions.length > 0) {
            for (String permission : permissions) {
                if (Manifest.permission.READ_CALENDAR.equals(permission)
                        || Manifest.permission.WRITE_CALENDAR.equals(permission)) {
                    result = result && hasCalendar(context);
                } else if (Manifest.permission.CAMERA.equals(permission)) {
                    result = result && hasCamera(context);
                } else if (Manifest.permission.READ_CONTACTS.equals(permission)
                        || Manifest.permission.WRITE_CONTACTS.equals(permission)
                        || Manifest.permission.GET_ACCOUNTS.equals(permission)) {
                    result = result && hasContacts(context);
                } else if (Manifest.permission.READ_CALENDAR.equals(permission)
                        || Manifest.permission.WRITE_CALENDAR.equals(permission)) {
                    result = result && hasCalendar(context);
                } else if (Manifest.permission.READ_CALENDAR.equals(permission)
                        || Manifest.permission.WRITE_CALENDAR.equals(permission)) {
                    result = result && hasCalendar(context);
                } else if (Manifest.permission.READ_CALENDAR.equals(permission)
                        || Manifest.permission.WRITE_CALENDAR.equals(permission)) {
                    result = result && hasCalendar(context);
                } else if (Manifest.permission.READ_CALENDAR.equals(permission)
                        || Manifest.permission.WRITE_CALENDAR.equals(permission)) {
                    result = result && hasCalendar(context);
                } else if (Manifest.permission.READ_CALENDAR.equals(permission)
                        || Manifest.permission.WRITE_CALENDAR.equals(permission)) {
                    result = result && hasCalendar(context);
                } else if (Manifest.permission.READ_CALENDAR.equals(permission)
                        || Manifest.permission.WRITE_CALENDAR.equals(permission)) {
                    result = result && hasCalendar(context);
                }
            }
        }
        return result;
    }

    @Override
    public boolean hasCalendar(Context context) {
        return false;
    }

    @Override
    public boolean hasCamera(Context context) {
        return PermissionsFragment.hasPermission(context, Manifest.permission.CAMERA);
    }

    @Override
    public boolean hasContacts(Context context) {
        return false;
    }

    @Override
    public boolean hasLocation(Context context) {
        return false;
    }

    @Override
    public boolean hasMicrophone(Context context) {
        return false;
    }

    @Override
    public boolean hasPhone(Context context) {
        return false;
    }

    @Override
    public boolean hasSensors(Context context) {
        return false;
    }

    @Override
    public boolean hasSMS(Context context) {
        return false;
    }

    @Override
    public boolean hasStorage(Context context) {
        return false;
    }
}
