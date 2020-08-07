package com.hqq.core.permission;

import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.content.ContextCompat;

import com.hqq.core.CoreBuildConfig;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.permission
 * @FileName :   FragmentProxy
 * @Date : 2019/6/6 0006  上午 11:02
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 * 代理类
 */
public class FragmentProxy implements IPermissionActions {


    @Override
    public void requestPermissions(String[] permissions, PermissionsResult listener) {
        // PermissionsHasImpl 判断
        if (!new PermissionsHasImpl().hasPermission(CoreBuildConfig.getInstance().getApplication(), permissions)) {
            PermissionsFragment mPermissionsFragment = PermissionsFragmentFactory.getPermissionsFragment(CoreBuildConfig.getInstance().getCurrActivity());
            mPermissionsFragment.requestPermissions(permissions, listener);
        } else {
            listener.onPermissionsResult(true);
        }
    }

}
