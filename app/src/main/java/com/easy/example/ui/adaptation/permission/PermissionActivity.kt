package com.easy.example.ui.adaptation.permission

import com.easy.core.permission.IPermissionsHas
import com.easy.core.permission.PermissionsUtils
import com.easy.core.ui.base.BaseDataBindingActivity
import com.easy.core.ui.base.open
import com.easy.core.utils.ToastUtils.showToast
import com.easy.example.R
import com.easy.example.databinding.ActivityPermissionBinding

/**
 * @Author : huangqiqiang
 * @Package : com.easy.coreapp.ui.activity
 * @FileName :   PermissionActivity
 * @Date : 2019/4/22 0022
 * @Email :  qiqiang213@gmail.com
 * @Descrive : TODO
 */
class PermissionActivity : BaseDataBindingActivity<ActivityPermissionBinding>() {
    override val layoutId: Int = R.layout.activity_permission

    override fun initView() {
        binding.button7.setOnClickListener {
            PermissionsUtils.requestCamera { status ->
                if (status) {
                    showToast("拥有摄像头权限")
                } else {
                    showToast("未拥有摄像头权限")
                }
            }
        }
        binding.button8.setOnClickListener {
            PermissionsUtils.requestStorage { status ->
                if (status) {
                    showToast("拥有文件读写权限")
                } else {
                    showToast("未拥有文件读写权限")
                }
            }
        }

        binding.button58.setOnClickListener {
            open(Permission4FragmentActivity::class.java)
        }

        PermissionsUtils.requestPermissions(IPermissionsHas.camera, IPermissionsHas.storage, IPermissionsHas.bluetooth) {

        }

    }


}