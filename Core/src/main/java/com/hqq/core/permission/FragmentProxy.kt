package com.hqq.core.permission

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.hqq.core.CoreConfig

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.permission
 * @FileName :   FragmentProxy
 * @Date : 2019/6/6 0006  上午 11:02
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 * 代理类
 */
class FragmentProxy : IPermissionActions {

    override fun requestPermissions(permissions: Array<String>, listener: PermissionsResult?) {
        // PermissionsHasImpl 判断
        if (!PermissionsHasImpl().hasPermission(CoreConfig.get().application, *permissions)) {
            val mPermissionsFragment =
                    PermissionsFragmentFactory.getPermissionsFragment((CoreConfig.get().currActivity as AppCompatActivity).supportFragmentManager)
            mPermissionsFragment!!.requestPermissions(permissions, listener)
        } else {
            listener!!.onPermissionsResult(true)
        }
    }


    fun requestPermissions(fragment: Fragment, permissions: Array<String>, listener: PermissionsResult?) {
        if (!PermissionsHasImpl().hasPermission(CoreConfig.get().application, *permissions)) {
            val mPermissionsFragment = PermissionsFragmentFactory.getPermissionsFragment(fragment.childFragmentManager)
            mPermissionsFragment!!.requestPermissions(permissions, listener)
        } else {
            listener!!.onPermissionsResult(true)
        }
    }
}