package com.easy.example.ui.adaptation.permission

import com.easy.core.permission.IPermissionsHas
import com.easy.core.permission.SysPermissionsUtils
import com.easy.core.ui.base.BaseDataBindingActivity
import com.easy.core.kt.open
import com.easy.core.utils.ToastUtils.showToast
import com.easy.example.R
import com.easy.example.databinding.ActivityPermissionBinding

/**
 * @Author : huangqiqiang
 * @Package : com.easy.coreapp.ui.activity
 * @FileName :   PermissionActivity
 * @Date : 2019/4/22 0022
 * @Email :  qiqiang213@gmail.com
 * @Describe : TODO
 */
class PermissionActivity : BaseDataBindingActivity<ActivityPermissionBinding>() {
    override fun getLayoutId(): Int {
        return R.layout.activity_permission
    }

    override fun initView() {
        SysPermissionsUtils.requestPermissions(supportFragmentManager,IPermissionsHas.camera, IPermissionsHas.storage, IPermissionsHas.bluetooth) {

        }
        binding.button7.setOnClickListener {
            SysPermissionsUtils.requestCamera(supportFragmentManager,{ status ->
                if (status) {
                    showToast("拥有摄像头权限")
                } else {
                    showToast("未拥有摄像头权限")
                }
            })
        }
        binding.button8.setOnClickListener {
            SysPermissionsUtils.requestStorage(supportFragmentManager,{ status ->
                if (status) {
                    showToast("拥有文件读写权限")
                } else {
                    showToast("未拥有文件读写权限")
                }
            })
        }

        binding.button58.setOnClickListener {
            open(Permission4FragmentActivity::class.java)
        }



    }


}