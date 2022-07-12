package com.hqq.example.ui.fragment

import com.hqq.core.ui.base.BaseActivity
import com.hqq.core.ui.base.BaseViewBindingActivity
import com.hqq.core.utils.FragmentUtils
import com.hqq.example.R
import com.hqq.example.databinding.ActivityTestLiveFragmentBinding

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui.fragment
 * @Date : 下午3:20
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class TestLiveFragmentActivity : BaseViewBindingActivity<ActivityTestLiveFragmentBinding>() {

    override fun initView() {

        var fragmentUtils = FragmentUtils(this)
        fragmentUtils.addOrShowFragment(TestLiveFragment(), R.id.fl_layout)

        binding.button63.setOnClickListener {
            fragmentUtils.replaceOrShowFragment(TestLiveFragment(), R.id.fl_layout)
        }
    }
}