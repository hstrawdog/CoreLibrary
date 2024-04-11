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

    /**
     * 读写文件的权限
     *
     * @param permissionsResult
     */
    @JvmStatic
    fun requestStorage(fragmentManager: FragmentManager,permissionsResult: PermissionsResult?, isNeedShowTip: Boolean = false,
                       tipText: String = "文件读写权限使用说明：保存照片使用") {
        requestPermissions(fragmentManager,IPermissionsHas.storage, tipText, permissionsResult)

    }

    /**
     * 获取摄像头权限
     *
     * @param permissionsResult
     */
    @JvmStatic
    fun requestCamera(fragmentManager: FragmentManager,permissionsResult: PermissionsResult?, isNeedShowTip: Boolean = false,
                      tipText: String = "相机权限使用说明：拍照时使用") {
        requestPermissions(fragmentManager,IPermissionsHas.camera, tipText, permissionsResult)


    }

    /**
     * 定位权限
     *
     * @param permissionsResult
     */
    @JvmStatic
    fun requestLocal(fragmentManager: FragmentManager,permissionsResult: PermissionsResult?, isNeedShowTip: Boolean = false,
                     tipText: String = "定位权限说明：当前位置信息使用") {
        requestPermissions(fragmentManager,IPermissionsHas.location, tipText, permissionsResult)


    }

    /**
     * 相机 以及文件读写
     * @param isNeedShowTip Boolean
     * @param permissionsResult PermissionsResult?
     */
    @JvmStatic
    fun requestCameraAndStorage(fragmentManager: FragmentManager,permissionsResult: PermissionsResult?, isNeedShowTip: Boolean = false,
                                tipText: String = "相机权限使用说明：拍照时使用\n" + "文件读写权限使用说明：保存照片使用") {
        requestPermissions(fragmentManager,IPermissionsHas.camera.plus(IPermissionsHas.storage), tipText, permissionsResult)

    }



    /**
     * 读写文件的权限
     *
     * @param permissionsResult
     */
    @JvmStatic
    fun requestStorage(fragmentManager: FragmentManager,permissionsResult: PermissionsResult?, tipText: String = "文件读写权限使用说明：保存照片使用") {
        requestPermissions(fragmentManager,IPermissionsHas.storage, tipText, permissionsResult)

    }

    /**
     * 获取摄像头权限
     *
     * @param permissionsResult
     */
    @JvmStatic
    fun requestCamera(fragmentManager: FragmentManager,permissionsResult: PermissionsResult?) {

        requestPermissions(fragmentManager,IPermissionsHas.camera, "相机权限使用说明：拍照时使用", permissionsResult)

    }

    /**
     * 定位权限
     *
     * @param permissionsResult
     */
    @JvmStatic
    fun requestLocal(fragmentManager: FragmentManager,permissionsResult: PermissionsResult?) {

        requestPermissions(fragmentManager,IPermissionsHas.localAround, "定位权限说明：当前位置信息使用", permissionsResult)

    }

    /**
     * 定位权限
     *
     * @param permissionsResult
     */
    @JvmStatic
    fun requestLocation(fragmentManager: FragmentManager,permissionsResult: PermissionsResult?) {
        FragmentProxy().requestPermissions(fragmentManager,IPermissionsHas.location, permissionsResult)
    }

    /**
     *  获取蓝牙权限  6.0 之后 蓝牙需要用到定位权限
     * @param permissionsResult PermissionsResult
     */
    @JvmStatic
    fun requestBluetooth(fragmentManager: FragmentManager,permissionsResult: PermissionsResult) {
        // 蓝牙权限 需要定位权限  定位权限需要 读写权限
        FragmentProxy().requestPermissions(fragmentManager,IPermissionsHas.bluetooth.plus(IPermissionsHas.location)
            .plus(IPermissionsHas.storage), permissionsResult)
    }
    /**
     *  读取 蓝牙权限 包含定位与读写
     * @param permissionsResult PermissionsResult
     * 相机 以及文件读写
     * @param isNeedShowTip Boolean
     * @param permissionsResult PermissionsResult?
     */
    @JvmStatic
    fun requestBluetooth(fragmentManager: FragmentManager,permissionsResult: PermissionsResult, tipText: String = "") {
        // 蓝牙权限 需要定位权限  定位权限需要 读写权限
        var permissions = IPermissionsHas.bluetooth.plus(IPermissionsHas.location)
            .plus(IPermissionsHas.storage)
        requestPermissions(fragmentManager,permissions, tipText, permissionsResult)

    }
    @JvmStatic
    fun requestCameraAndStorage(fragmentManager: FragmentManager,permissionsResult: PermissionsResult?) {
        requestPermissions(fragmentManager,IPermissionsHas.cameraAndStorage, "相机权限使用说明：拍照时使用\n" + "文件读写权限使用说明：保存照片使用", permissionsResult)

    }

    /**
     * 申请权限
     * @param call Function0<Unit>
     */
    @JvmStatic
    fun requestCameraAndStorageAndPhone(fragmentManager: FragmentManager,permissionsResult: PermissionsResult?) {
        requestPermissions(fragmentManager,IPermissionsHas.cameraAndStorageAndSystem, "相机权限使用说明：拍照时使用\n" + "文件读写权限使用说明：保存照片使用\n" + "手机状态权限说明：照片信息读取使用", permissionsResult)
    }

    /** 请求权限
     * @see IPermissionsHas
     * @param permissions Array<out Array<String>>
     * @param permissionsResult PermissionsResult
     */
    fun requestPermissions(fragmentManager: FragmentManager,vararg permissions: Array<String>, permissionsResult: PermissionsResult) {
        var permissionArray = emptyArray<String>()
        for (permission in permissions) {
            permissionArray = permissionArray.plus(permission)
        }
        requestPermissions(fragmentManager,permissionArray, "", false, permissionsResult)
    }

    fun requestPermissions(fragmentManager: FragmentManager,vararg permissions: Array<String>, tipText: String, permissionsResult: PermissionsResult) {
        var permissionArray = emptyArray<String>()
        for (permission in permissions) {
            permissionArray = permissionArray.plus(permission)
        }
        requestPermissions(fragmentManager,permissionArray, tipText, tipText.isNotNull(), permissionsResult)
    }

    fun requestPermissions(fragmentManager: FragmentManager,permissions: Array<String>, tipText: String, permissionsResult: PermissionsResult?) {
        requestPermissions(fragmentManager,permissions, tipText, tipText.isNotNull(), permissionsResult)
    }

    /**
     *
     * @param plus Array<String>  权限组
     * @param tipText String  华为  荣耀提示
     * @param permissionsResult PermissionsResult?
     */
    fun requestPermissions(fragmentManager: FragmentManager, plus: Array<String>, tipText: String,
                           isNeedShowTip: Boolean = false, permissionsResult: PermissionsResult?) {
        if (BaseSystemUtil.isHuaWeiSeriesDevice() && isNeedShowTip) {
            if (!IPermissionActions.hasPermission(CoreConfig.applicationContext, *plus)) {
                CoreConfig.get().currActivity?.let {
                    var supportFragmentManager = (it as AppCompatActivity).supportFragmentManager
                    HuaWeiTipDialog().apply {
                        this.tipText = tipText
                        call = {
                            FragmentProxy().requestPermissions(fragmentManager, plus, permissionsResult)
                        }
                    }
                        .show(supportFragmentManager)
                }
            } else {
                permissionsResult?.onPermissionsResult(true)
            }
        } else {
            FragmentProxy().requestPermissions(fragmentManager, plus, permissionsResult)
        }
    }

}