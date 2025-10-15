package com.easy.example.ui.fragment

import com.easy.core.ui.base.BaseViewBindingActivity
import com.easy.core.utils.FragmentUtils
import com.easy.example.R
import com.easy.example.databinding.ActivityFragmentUtilsBinding

/**
 * @Author : huangqiqiang
 * @Package : com.easy.example.ui.fragment
 * @Date : 13:51
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class FragmentUtilsActivity : BaseViewBindingActivity<ActivityFragmentUtilsBinding>() {

    override fun initView() {
        var  fragmentUtils = FragmentUtils(this)

        var  fragmentA =FragmentA()
        var  fragmentb =FragmentB()

        binding.button96.setOnClickListener {
            fragmentUtils.showOrAddFragment(fragmentA, R.id.fl_layout)
        }
        binding.button97.setOnClickListener {
            fragmentUtils.showOrAddFragment(fragmentb, R.id.fl_layout)
        }

        binding.button98.setOnClickListener {
            fragmentUtils.removeFragment(fragmentA)
        }
        binding.button99.setOnClickListener {
            fragmentUtils.removeFragment(fragmentb)
        }

    }
}
