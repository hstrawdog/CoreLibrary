package com.easy.core.permission

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.easy.core.CoreConfig
import com.easy.core.permission.dialog.HuaWeiTipDialog
import com.easy.core.utils.BaseSystemUtil
import com.easy.core.utils.data.isNotNull

/**
 * @Author : huangqiqiang
 * @Package : com.core.library.permission
 * @FileName :   PermissionsUtils
 * @Date : 2018/11/22 0022  上午 10:47
 * @Email :  qiqiang213@gmail.com
 * @Describe :   PermissionsFragment   需要用 工厂或者代理来生成
 *
 *    不合理的工具
 *    需要整理过
 *      FragmentProxy().requestPermissions 中已经有有断过过是否有权限   本类 在进行判断 有点多余
 */
object SysPermissionsUtils {


    /** 请求权限
     * @see IPermissionsHas
     * @param permissions Array<out Array<String>>
     * @param permissionsResult PermissionsResult
     */
    fun requestPermissions(fragmentManager:FragmentManager, vararg permissions:Array<String>, permissionsResult:PermissionsResult) {
        var permissionArray = emptyArray<String>()
        for (permission in permissions) {
            permissionArray = permissionArray.plus(permission)
        }
        requestPermissions(fragmentManager, permissionArray, "", false, permissionsResult)
    }

    fun requestPermissions(fragmentManager:FragmentManager, vararg permissions:Array<String>, tipText:String, permissionsResult:PermissionsResult) {
        var permissionArray = emptyArray<String>()
        for (permission in permissions) {
            permissionArray = permissionArray.plus(permission)
        }
        requestPermissions(fragmentManager, permissionArray, tipText, isNeedShowTip= tipText.isNotNull(), permissionsResult)
    }

    fun requestPermissions(fragmentManager:FragmentManager, permissions:Array<String>, tipText:String, permissionsResult:PermissionsResult?) {
        requestPermissions(fragmentManager, permissions, tipText,isNeedShowTip= tipText.isNotNull(), permissionsResult)
    }

    /**
     * @param plus Array<String>  权限组
     * @param tipText String  华为  荣耀提示
     * @param permissionsResult PermissionsResult?
     */
    fun requestPermissions(fragmentManager:FragmentManager, plus:Array<String>, tipText:String, isNeedShowTip:Boolean = true, permissionsResult:PermissionsResult?) {

        // 已有权限
        if (IPermissionActions.hasPermission(CoreConfig.applicationContext, *plus)) {
            permissionsResult?.onPermissionsResult(true)
            return
        }
        // 不需要提示
        if (!isNeedShowTip) {
            FragmentProxy().requestPermissions(fragmentManager, plus, permissionsResult)
            return
        }
        // 需要提示
        HuaWeiTipDialog().apply {
            this.tipText = tipText
            call = { agree ->

                if (agree) {
                    FragmentProxy().requestPermissions(fragmentManager, plus, permissionsResult)
                } else {
                    permissionsResult?.onPermissionsResult(false)
                }

            }

        }.show(fragmentManager)
    }

}