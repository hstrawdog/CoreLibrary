package com.hqq.core.permission

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import com.hqq.core.utils.ToastUtils
import com.hqq.core.utils.VersionUtils
import java.util.*

/**
 * @Author : huangqiqiang
 * @Package : com.core.library.permission
 * @FileName :   PermissionsFragment
 * @Date : 2018/11/22 0022  上午 10:41
 * @Email :  qiqiang213@gmail.com
 * @Descrive :
 */
class PermissionsFragment : Fragment(), IPermissionActions {

    companion object {
        fun newInstance(): PermissionsFragment {
            val args = Bundle()
            val fragment = PermissionsFragment()
            fragment.arguments = args
            return fragment
        }
    }


    var OPEN_SETTING_CODE = 0x55
    var CODE = 0x5186
    var mPermissionsResult: PermissionsResult? = null
    /**
     * 申请的权限组
     */
    lateinit var mPermissions: Array<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    /**
     * 发现 系统选择 与 厂商权限不一致
     * 取消判断是否拥有权限 直接去申请
     * @param
     */
    override fun requestPermissions(permissions: Array<String>, listener: PermissionsResult?) {
        mPermissions = permissions
        mPermissionsResult = listener
        if (IPermissionActions.Companion.hasPermission(context, *permissions)) {
            mPermissionsResult!!.onPermissionsResult(true)
        } else {
            requestPermissions(permissions, CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0x55) {
            OPEN_SETTING_CODE++
            // 从设置界面过来 重新再去检测权限
            requestPermissions(mPermissions, mPermissionsResult)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val granted: MutableList<String> = ArrayList()
        val denied: MutableList<String> = ArrayList()
        if (grantResults == null) {
            for (perm in permissions) {
                if (IPermissionActions.Companion.hasPermission(context, perm)) {
                    granted.add(perm)
                } else {
                    denied.add(perm)
                }
            }
        } else {
            var index = 0
            val length = permissions.size
            while (index < length) {
                val perm = permissions[index]
                if (grantResults[index] == PackageManager.PERMISSION_GRANTED) {
                    granted.add(perm)
                } else {
                    denied.add(perm)
                }
                index++
            }
        }
        if (denied.isEmpty() && !granted.isEmpty()) {
            //   ToastUtils.showToast(getContext(), "同意了所有权限");
            if (mPermissionsResult != null) {
                mPermissionsResult!!.onPermissionsResult(true)
            }
        } else {
            ToastUtils.showToast(context, "拒绝权限,会导致功能无法继续执行")
            mPermissionsResult!!.onPermissionsResult(false)
            // 打开设置界面
            val intent = Intent()
            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            val uri = Uri.fromParts("package", VersionUtils.getPackageName(context), null)
            intent.data = uri
            startActivityForResult(intent, OPEN_SETTING_CODE)

            //勾选了对话框中”Don’t ask again”的选项, 返回false
            for (deniedPermission in permissions) {
                val flag = shouldShowRequestPermissionRationale(deniedPermission)
                if (!flag) {
                    //拒绝授权
                    return
                }
            }
        }
    }


}