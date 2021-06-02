package com.hqq.example.ui.adaptation.permission

import com.hqq.core.permission.PermissionsResult
import com.hqq.core.permission.PermissionsUtils
import com.hqq.core.ui.base.BaseFragment
import com.hqq.core.ui.base.BaseViewBindingFragment
import com.hqq.core.utils.ToastUtils
import com.hqq.example.databinding.ActivityPermissionBinding

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui.adaptation.permission
 * @Date : 下午 5:03
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class Permission4Fragment : BaseViewBindingFragment<ActivityPermissionBinding>() {
    override fun initView() {
        binding.button7.setOnClickListener {
            PermissionsUtils.requestCamera(object : PermissionsResult {
                override fun onPermissionsResult(status: Boolean) {
                    ToastUtils.showToast("拥有摄像头权限")
                }
            })

        }
        binding.button8.setOnClickListener {
            PermissionsUtils.requestStorage(object : PermissionsResult {
                override fun onPermissionsResult(status: Boolean) {
                    ToastUtils.showToast("拥有文件读写权限")
                }
            })
        }

    }
}