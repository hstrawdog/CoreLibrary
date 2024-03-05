package com.easy.example.ui.adaptation.permission

import com.easy.core.permission.PermissionsResult
import com.easy.core.permission.SysPermissionsUtils
import com.easy.core.ui.base.BaseViewBindingFragment
import com.easy.core.utils.ToastUtils
import com.easy.example.databinding.ActivityPermissionBinding

/**
 * @Author : huangqiqiang
 * @Package : com.easy.example.ui.adaptation.permission
 * @Date : 下午 5:03
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class Permission4Fragment : BaseViewBindingFragment<ActivityPermissionBinding>() {
    override fun initView() {
        binding.button7.setOnClickListener {
            SysPermissionsUtils.requestCamera(object : PermissionsResult {
                override fun onPermissionsResult(status: Boolean) {
                    if (status) ToastUtils.showToast("拥有摄像头权限")
                }

            })
        }
        binding.button8.setOnClickListener {
            SysPermissionsUtils.requestStorage(object : PermissionsResult {
                override fun onPermissionsResult(status: Boolean) {
                    if (status) ToastUtils.showToast("拥有文件读写权限")
                }

            })

        }
        binding.button59.setOnClickListener {
            SysPermissionsUtils.requestLocal({
                if (it) ToastUtils.showToast("拥有定位权限")
            })
        }
        binding.button60.setOnClickListener {
            SysPermissionsUtils.requestBluetooth(object : PermissionsResult {
                override fun onPermissionsResult(status: Boolean) {
                    if (status) ToastUtils.showToast("拥有定位权限")
                }

            })
        }

    }
}