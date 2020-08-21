package com.hqq.core.permission

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.permission
 * @FileName :   PermissionsFragmentFactory
 * @Date : 2019/6/6 0006  上午 10:45
 * @Email : qiqiang213@gmail.com
 * @Descrive :  工厂 用于生产 PermissionsFragment
 */
object PermissionsFragmentFactory {
    private const val TAG_PERMISSIONS_FRAGMENT = "PermissionsFragment"

    fun getPermissionsFragment(currActivity: Activity?): PermissionsFragment? {
        var permissionsFragment: PermissionsFragment?
        if (currActivity is AppCompatActivity) {
            val fragmentManager = currActivity.supportFragmentManager
            permissionsFragment = fragmentManager.findFragmentByTag(TAG_PERMISSIONS_FRAGMENT) as PermissionsFragment?
            if (null == permissionsFragment) {
                permissionsFragment = PermissionsFragment.Companion.newInstance()
                fragmentManager.beginTransaction().add(permissionsFragment, TAG_PERMISSIONS_FRAGMENT).commitNow()
            }
        } else {
            throw IllegalStateException("不支持的Activity  Activity需要继承 AppCompatActivity ")
        }
        return permissionsFragment
    }
}