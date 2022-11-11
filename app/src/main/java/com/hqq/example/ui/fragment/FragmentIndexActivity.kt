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
        binding.button67.setOnClickListener {
            // 监听 Fragment 的生命周期 与延迟加载
            open(ViewPager2FragmentLiveActivity::class.java)
        }
        binding.button68.setOnClickListener {
            open(TestLiveFragmentActivity::class.java)
        }
    }
}