package com.hqq.core.permission

import android.content.Context
import java.util.*

/**
 * @Author : huangqiqiang
 * @Package : com.core.library.permission
 * @FileName :   PermissionsHasImpl
 * @Date : 2018/11/22 0022  上午 9:57
 * @Email :  qiqiang213@gmail.com
 * @Descrive : 判断是否拥有权限
 */
class PermissionsHasImpl : IPermissionsHas {
    /**
     * 权限存在依赖关系  如定位权限 需要读写权限
     * 用吃方法判断
     *
     * @param context
     * @param permissions
     * @return
     */
    override fun hasPermission(context: Context?, vararg permissions: String): Boolean {
        var result = true
        if (permissions.isNotEmpty()) {
            for (permission in permissions) {
                if (IPermissionsHas.calendar.contains(permission)) {
                    result = result && hasCalendar(context)
                } else if (IPermissionsHas.camera.contains(permission)) {
                    result = result && hasCamera(context)
                } else if (IPermissionsHas.contacts.contains(permission)) {
                    result = result && hasContacts(context)
                } else if (IPermissionsHas.location.contains(permission)) {
                    result = result && hasLocation(context)
                } else if (IPermissionsHas.microphone.contains(permission)) {
                    result = result && hasMicrophone(context)
                } else if (IPermissionsHas.phone.contains(permission)) {
                    result = result && hasPhone(context)
                } else if (IPermissionsHas.sensors.contains(permission)) {
                    result = result && hasSensors(context)
                } else if (IPermissionsHas.sMS.contains(permission)) {
                    result = result && hasSMS(context)
                } else if (IPermissionsHas.storage.contains(permission)) {
                    result = result && hasStorage(context)
                } else if (IPermissionsHas.bluetooth.contains(permission)) {
                    result = result && hasBluetooth(context)
                }
            }
        }
        return result
    }

    private fun hasBluetooth(context: Context?): Boolean {
        return IPermissionActions.hasPermission(context, *IPermissionsHas.bluetooth)
    }

    override fun hasCalendar(context: Context?): Boolean {
        //(允许程序读取用户的日程信息)
        return IPermissionActions.hasPermission(context, *IPermissionsHas.calendar)
    }

    override fun hasCamera(context: Context?): Boolean {
        //(允许访问摄像头进行拍照)
        return IPermissionActions.hasPermission(context, *IPermissionsHas.camera)
    }

    override fun hasContacts(context: Context?): Boolean {
        return IPermissionActions.hasPermission(context, *IPermissionsHas.contacts)
    }

    override fun hasLocation(context: Context?): Boolean {
        return IPermissionActions.hasPermission(context, *IPermissionsHas.location)
    }

    override fun hasMicrophone(context: Context?): Boolean {
        return IPermissionActions.hasPermission(context, *IPermissionsHas.microphone)
    }

    override fun hasPhone(context: Context?): Boolean {
        return IPermissionActions.hasPermission(context, *IPermissionsHas.phone)
    }

    override fun hasSensors(context: Context?): Boolean {
        return IPermissionActions.hasPermission(context, *IPermissionsHas.sensors)
    }

    override fun hasSMS(context: Context?): Boolean {
        return IPermissionActions.hasPermission(context, *IPermissionsHas.sMS)
    }

    override fun hasStorage(context: Context?): Boolean {
        return IPermissionActions.hasPermission(context, *IPermissionsHas.storage)
    }
}