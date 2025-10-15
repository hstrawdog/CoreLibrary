package com.easy.example.ui.fragment

import com.easy.core.ui.base.BaseViewBindingActivity
import com.easy.core.utils.FragmentUtils
import com.easy.example.R
import com.easy.example.databinding.ActivityTestLiveFragmentBinding

/**
 * @Author : huangqiqiang
 * @Package : com.easy.example.ui.fragment
 * @Date : 下午3:20
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class TestLiveFragmentActivity : BaseViewBindingActivity<ActivityTestLiveFragmentBinding>() {

    override fun initView() {

        var fragmentUtils = FragmentUtils(this)
        fragmentUtils.showOrAddFragment(TestLiveFragment(), R.id.fl_layout)

        binding.button63.setOnClickListener {
            fragmentUtils.replaceOrShowFragment(TestLiveFragment(), R.id.fl_layout)
        }
    }
}