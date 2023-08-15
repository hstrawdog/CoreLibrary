package com.easy.example.ui.jetpack.package1

import android.content.Context
import android.content.Intent
import com.easy.core.ui.base.BaseViewModel
import com.easy.core.ui.base.BaseVmActivity
import com.easy.example.R
import com.easy.example.databinding.ActivityVmTestBinding

/**
 * @version V1.0 <描述当前版本功能>
 * @Author : huangqiqiang
 * @Package : com.easy.example.ui.jetpack.package1
 * @FileName :   VmTestActivity
 * @Date : 2020/7/27  下午11:03
 * @Email : qiqiang213@gmail.com
 * @Descrive :
</描述当前版本功能> */
class VmTestActivity : BaseVmActivity<BaseViewModel, ActivityVmTestBinding, >() {


    override fun getLayoutId(): Int {
        return R.layout.activity_vm_test
    }

    override fun bindingViewModelId(): Int {
     return  0
    }

    override fun initViews() {
        binding.button49.setOnClickListener { viewMode.showLoading(true) }
    }

    companion object {
        fun open(context: Context) {
            val starter = Intent(context, VmTestActivity::class.java)
            context.startActivity(starter)
        }
    }
}