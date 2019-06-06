package com.hqq.core.permission;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.permission
 * @FileName :   IPermissionActions
 * @Date : 2019/6/6 0006  上午 11:04
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public interface IPermissionActions {
    void requestPermissions(String[] permissions, PermissionsResult listener);

}
