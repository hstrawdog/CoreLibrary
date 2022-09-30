package com.hqq.example.ui.fragment

import com.hqq.core.ui.base.BaseActivity
import com.hqq.core.ui.base.BaseViewBindingActivity
import com.hqq.core.ui.base.open
import com.hqq.example.R
import com.hqq.example.databinding.ActivityFragmentInexBinding

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui.fragment
 * @Date : 14:43
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class FragmentIndexActivity : BaseViewBindingActivity<ActivityFragmentInexBinding>() {


    override fun initView() {

        binding.button64.setOnClickListener {

            open(FragmentIndexActivity::class.java)
        }
    }
}