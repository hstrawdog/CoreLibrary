package com.hqq.example.permission;

/**
 * @Author : huangqiqiang
 * @Package : com.core.library.permission
 * @FileName :   onPermissionsResult
 * @Date : 2018/11/22 0022  上午 11:53
 * @Descrive :
 * @Email :  qiqiang213@gmail.com
 */
public interface PermissionsResult {
    /**
     * 是否拥有权限
     *
     * @param status boolean
     */
    void onPermissionsResult(boolean status);
}
