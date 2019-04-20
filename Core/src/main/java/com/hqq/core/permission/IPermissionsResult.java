package com.hqq.core.permission;

import android.support.annotation.NonNull;

/**
 * @Author : huangqiqiang
 * @Package : com.core.library.permission
 * @FileName :   IPermissionsResult
 * @Date : 2018/11/22 0022  上午 11:53
 * @Descrive :
 * @Email :
 */
public interface IPermissionsResult {

public void PermissionsResult(@NonNull String[] permissions, @NonNull int[] grantResults);
}
