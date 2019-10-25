package com.hqq.example.permission;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.permission
 * @FileName :   IPermissionActions
 * @Date : 2019/6/6 0006  上午 11:04
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public interface IPermissionActions {
    /**
     * 请求权限
     *
     * @param permissions 权限组
     * @param listener    回调
     */
    void requestPermissions(String[] permissions, PermissionsResult listener);

}
