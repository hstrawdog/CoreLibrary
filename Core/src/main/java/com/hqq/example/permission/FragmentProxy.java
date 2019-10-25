package com.hqq.example.permission;

import com.hqq.example.CoreBuildConfig;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.permission
 * @FileName :   FragmentProxy
 * @Date : 2019/6/6 0006  上午 11:02
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public class FragmentProxy implements IPermissionActions {

    @Override
    public void requestPermissions(String[] permissions, PermissionsResult listener) {
        PermissionsFragment mPermissionsFragment = PermissionsFragmentFactory.getPermissionsFragment(CoreBuildConfig.getInstance().getCurrActivity());
        mPermissionsFragment.setPermissionsResult(listener);
        mPermissionsFragment.requestPermissions(permissions);
    }
}
