package com.easy.example.ui.fragment

import com.easy.core.ui.base.BaseViewBindingActivity
import com.easy.core.kt.open
import com.easy.example.databinding.ActivityFragmentInexBinding

/**
 * @Author : huangqiqiang
 * @Package : com.easy.example.ui.fragment
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