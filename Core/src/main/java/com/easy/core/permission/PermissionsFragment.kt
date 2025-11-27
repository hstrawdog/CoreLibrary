package com.easy.core.permission

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.easy.core.CoreConfig
import com.easy.core.ui.dialog.SelectDialog
import com.easy.core.utils.AppTool
import com.easy.core.utils.ToastUtils

/**
 * @Author : huangqiqiang
 * @Package : com.core.library.permission
 * @FileName :   PermissionsFragment
 * @Date : 2018/11/22 0022  上午 10:41
 * @Email :  qiqiang213@gmail.com
 * @Describe :
 */
class PermissionsFragment : Fragment() {

    companion object {
        fun newInstance(): PermissionsFragment {
            val args = Bundle()
            val fragment = PermissionsFragment()
            fragment.arguments = args
            return fragment
        }
    }

    var mPermissionsResult: PermissionsResult? = null

    /**
     * 申请的权限组
     */
    lateinit var mPermissions: Array<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }


    val registerForActivityResult = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
        // 请求结果，返回一个map ，其中 key 为权限名称，value 为是否权限是否赋予
        var success = true
        for (mutableEntry in it) {
            success = success && mutableEntry.value
        }
        if (mPermissionsResult != null) {
            if (success) {
                mPermissionsResult?.onPermissionsResult(true)
            } else {
                if (CoreConfig.get().goSettingPermission) {
                    SelectDialog.Builder()
                        .setTitle("提示")
                        .setContent("权限被禁止,会导致功能无法继续执行.\n是否前往设置中开启")
                        .setPositiveButton("确定") { dialog, which -> // 打开设置界面
                            val intent = Intent()
                            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                            val uri = Uri.fromParts("package", AppTool.getPackageName(context), null)
                            intent.data = uri
                            // 重新在检查一遍权限
                            startActivityForResult(intent, 0x55)
                            dialog.dismiss()

                        }
                        .setOnCancelListener("取消") { dialog, which ->
                            dialog.dismiss()
                            mPermissionsResult?.onPermissionsResult(false)
                        }
                        .create()
                        .show(childFragmentManager)
                } else {
                    ToastUtils.showToast(context, "拒绝权限,会导致功能无法继续执行")
                    mPermissionsResult?.onPermissionsResult(false)

                }
            }
        }
    }

    /**
     *   上一个动作 已经 有权限判断
     *   这边 直接进行权限申请 避免多余动作
     * @param permissions Array<String>
     * @param listener PermissionsResult?
     */
    fun requestPermissions(permissions: Array<String>, listener: PermissionsResult?) {
        mPermissions = permissions
        mPermissionsResult = listener
//        if (IPermissionActions.hasPermission(context, *permissions)) {
//            mPermissionsResult!!.onPermissionsResult(true)
//        } else {
        registerForActivityResult.launch(permissions)
//        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 0x55) {
            // 从设置界面过来 重新再去检测权限
            requestPermissions(mPermissions, mPermissionsResult)
        }
    }


}