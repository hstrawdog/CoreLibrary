package com.hqq.example.ui

import com.hqq.core.ui.base.BaseViewBindingActivity
import com.hqq.core.utils.log.LogUtils
import com.hqq.example.databinding.ActivityLogUtilsBinding

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui
 * @Date : 17:27
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class LogUtilsActivity : BaseViewBindingActivity<ActivityLogUtilsBinding>() {

    override fun initView() {
        binding.button83.setOnClickListener {
            LogUtils.v(this.toString())
        }
        binding.button84.setOnClickListener {
            LogUtils.d(this.toString())
        }
        binding.button85.setOnClickListener {
            LogUtils.i(this.toString())
        }
        binding.button86.setOnClickListener {
            LogUtils.w(this.toString())
        }
        binding.button87.setOnClickListener {
            LogUtils.e(this.toString())
        }
        binding.button88.setOnClickListener {
            LogUtils.dInfo(this.toString())

        }
    }
}