package com.easy.core.permission

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import com.easy.core.CoreConfig
import com.easy.core.permission.dialog.HuaWeiTipDialog


/**
 * @Author : huangqiqiang
 * @Package : com.core.library.permission
 * @FileName :   PermissionsUtils
 * @Date : 2018/11/22 0022  上午 10:47
 * @Email :  qiqiang213@gmail.com
 * @Descrive :   PermissionsFragment   需要用 工厂或者代理来生成
 */
object PermissionsUtils {

    /** 请求权限
     * @see IPermissionsHas
     * @param permissions Array<out Array<String>>
     * @param permissionsResult PermissionsResult
     */
    fun requestPermissions(vararg permissions: Array<String>, permissionsResult: PermissionsResult) {
        var permission = arrayOf("")
        for (permission in permissions) {
            permission.plus(permission)
        }
        FragmentProxy().requestPermissions(permission, permissionsResult)
    }

    /**
     * 读写文件的权限
     *
     * @param permissionsResult
     */
    @JvmStatic
    fun requestStorage(isNeedShowTip: Boolean = false, permissionsResult: PermissionsResult?) {
        if (isNeedShowTip && Build.BRAND.equals("HUAWEI")) {
            if (!IPermissionActions.hasPermission(CoreConfig.applicationContext, *IPermissionsHas.storage)) {
                CoreConfig.get().currActivity?.let {
                    var supportFragmentManager = (it as AppCompatActivity).supportFragmentManager
                    HuaWeiTipDialog().apply {
                        tipText = "文件读写权限使用说明：保存照片使用"
                        call = {
                            FragmentProxy().requestPermissions(IPermissionsHas.storage, permissionsResult)
                        }
                    }
                        .show(supportFragmentManager)
                }
            } else {
                permissionsResult?.onPermissionsResult(true)
            }
        } else {
            FragmentProxy().requestPermissions(IPermissionsHas.storage, permissionsResult)

        }
    }

    /**
     * 获取摄像头权限
     *
     * @param permissionsResult
     */
    @JvmStatic
    fun requestCamera(isNeedShowTip: Boolean = false, permissionsResult: PermissionsResult?) {
        if (isNeedShowTip && Build.BRAND.equals("HUAWEI")) {
            if (!IPermissionActions.hasPermission(CoreConfig.applicationContext, *IPermissionsHas.camera)) {
                CoreConfig.get().currActivity?.let {
                    var supportFragmentManager = (it as AppCompatActivity).supportFragmentManager

                    HuaWeiTipDialog().apply {
                        tipText = "相机权限使用说明：拍照时使用"
                        call = {
                            FragmentProxy().requestPermissions(IPermissionsHas.camera, permissionsResult)

                        }
                    }
                        .show(supportFragmentManager)
                }
            } else {
                permissionsResult?.onPermissionsResult(true)
            }
        } else {
            FragmentProxy().requestPermissions(IPermissionsHas.camera, permissionsResult)

        }


    }

    /**
     * 定位权限
     *
     * @param permissionsResult
     */
    @JvmStatic
    fun requestLocal(isNeedShowTip: Boolean = false, permissionsResult: PermissionsResult?) {

        if (isNeedShowTip && Build.BRAND.equals("HUAWEI")) {
            if (!IPermissionActions.hasPermission(CoreConfig.applicationContext, *IPermissionsHas.location)) {
                CoreConfig.get().currActivity?.let {
                    var supportFragmentManager = (it as AppCompatActivity).supportFragmentManager

                    HuaWeiTipDialog().apply {
                        tipText = "定位权限说明：当前位置信息使用"
                        call = {
                            FragmentProxy().requestPermissions(IPermissionsHas.location, permissionsResult)
                        }
                    }
                        .show(supportFragmentManager)
                }
            } else {
                permissionsResult?.onPermissionsResult(true)
            }
        } else {
            FragmentProxy().requestPermissions(IPermissionsHas.location, permissionsResult)
        }


    }

    /**
     * 相机 以及文件读写
     * @param isNeedShowTip Boolean
     * @param permissionsResult PermissionsResult?
     */
    @JvmStatic
    fun requestCameraAndStorage(isNeedShowTip: Boolean = false, permissionsResult: PermissionsResult?) {
        if (isNeedShowTip && Build.BRAND.equals("HUAWEI")) {
            if (!IPermissionActions.hasPermission(CoreConfig.applicationContext, *IPermissionsHas.camera.plus(IPermissionsHas.storage))) {
                CoreConfig.get().currActivity?.let {
                    var supportFragmentManager = (it as AppCompatActivity).supportFragmentManager

                    HuaWeiTipDialog().apply {
                        tipText = "相机权限使用说明：拍照时使用\n" + "文件读写权限使用说明：保存照片使用"
                        call = {
                            FragmentProxy().requestPermissions(IPermissionsHas.camera.plus(IPermissionsHas.storage), permissionsResult)
                        }
                    }
                        .show(supportFragmentManager)
                }
            } else {
                permissionsResult?.onPermissionsResult(true)
            }
        } else {
            FragmentProxy().requestPermissions(IPermissionsHas.camera.plus(IPermissionsHas.storage), permissionsResult)
        }

    }


}