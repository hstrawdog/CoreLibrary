package com.easy.core.permission

import androidx.fragment.app.FragmentManager

/**
 * @Author : huangqiqiang
 * @Package :  com.easy.core.permission
 * @FileName :   PermissionsFragmentFactory
 * @Date : 2019/6/6 0006  上午 10:45
 * @Email : qiqiang213@gmail.com
 * @Descrive :  工厂 用于生产 PermissionsFragment
 */
object PermissionsFragmentFactory {
    private const val TAG_PERMISSIONS_FRAGMENT = "PermissionsFragment"
    fun getPermissionsFragment(fragmentManager: FragmentManager): PermissionsFragment? {
        var permissionsFragment: PermissionsFragment?
        permissionsFragment = fragmentManager.findFragmentByTag(TAG_PERMISSIONS_FRAGMENT) as PermissionsFragment?
        if (null == permissionsFragment) {
            permissionsFragment = PermissionsFragment.newInstance()
            fragmentManager.beginTransaction()
                .add(permissionsFragment, TAG_PERMISSIONS_FRAGMENT)
                .commitNow()
        }
        return permissionsFragment
    }


}