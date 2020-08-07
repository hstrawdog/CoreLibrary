package com.hqq.example.ui.jetpack.package1

import android.content.Context
import android.content.Intent
import com.hqq.core.ui.vm.BaseViewModel
import com.hqq.core.ui.vm.BaseVmActivity
import com.hqq.example.R
import com.hqq.example.databinding.ActivityVmTestBinding

/**
 * @version V1.0 <描述当前版本功能>
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui.jetpack.package1
 * @FileName :   VmTestActivity
 * @Date : 2020/7/27  下午11:03
 * @Email : qiqiang213@gmail.com
 * @Descrive :
</描述当前版本功能> */
class VmTestActivity : BaseVmActivity<ActivityVmTestBinding?, BaseViewModel?>() {
    override fun getBindingViewModelId(): Int {
        return 0
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_vm_test
    }

    override fun initViews() {
        mBinding!!.button49.setOnClickListener { mViewModel!!.setShowLoading(true) }
    }

    companion object {
        fun open(context: Context) {
            val starter = Intent(context, VmTestActivity::class.java)
            context.startActivity(starter)
        }
    }
}