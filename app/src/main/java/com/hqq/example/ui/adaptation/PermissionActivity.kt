package com.hqq.example.ui.adaptation

import android.app.Activity
import android.content.Intent
import com.hqq.core.permission.PermissionsResult
import com.hqq.core.permission.PermissionsUtils
import com.hqq.core.ui.base.BaseDataBindingActivity
import com.hqq.core.utils.ToastUtils.showToast
import com.hqq.example.R
import com.hqq.example.databinding.ActivityPermissionBinding

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.coreapp.ui.activity
 * @FileName :   PermissionActivity
 * @Date : 2019/4/22 0022
 * @Email :  qiqiang213@gmail.com
 * @Descrive : TODO
 */
class PermissionActivity : BaseDataBindingActivity<ActivityPermissionBinding>() {
    override val layoutId: Int = R.layout.activity_permission

    override fun initView() {
        binding?.button7?.setOnClickListener {
            PermissionsUtils.requestCamera(object : PermissionsResult {
                override fun onPermissionsResult(status: Boolean) {
                    showToast("拥有摄像头权限")
                }
            })
        }
        binding?.button8?.setOnClickListener {
            PermissionsUtils.requestStorage(object : PermissionsResult {
                override fun onPermissionsResult(status: Boolean) {
                    showToast("拥有文件读写权限")
                }
            })
        }
    }

    companion object {
        fun open(context: Activity) {
            val starter = Intent(context, PermissionActivity::class.java)
            context.startActivityForResult(starter, -1)
        }
    }
}