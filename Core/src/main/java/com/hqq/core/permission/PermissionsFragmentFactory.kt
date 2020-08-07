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
    private const val PERMISSIONS_FRAGMENT_TAG = "PermissionsFragment"
    fun getPermissionsFragment(currActivity: Activity?): PermissionsFragment? {
        var permissionsFragment: PermissionsFragment?
        if (currActivity is AppCompatActivity) {
            val fragmentManager = currActivity.supportFragmentManager
            permissionsFragment = fragmentManager.findFragmentByTag(PERMISSIONS_FRAGMENT_TAG) as PermissionsFragment?
            if (null == permissionsFragment) {
                permissionsFragment = PermissionsFragment.Companion.newInstance()
                fragmentManager.beginTransaction().add(permissionsFragment, PERMISSIONS_FRAGMENT_TAG).commitNow()
            }
        } else {
            throw IllegalStateException("不支持的Activity  Activity需要继承 AppCompatActivity ")
        }
        return permissionsFragment
    }
}