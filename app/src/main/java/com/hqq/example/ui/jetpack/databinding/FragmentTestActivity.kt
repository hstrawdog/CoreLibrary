package com.hqq.example.ui.jetpack.databinding

import androidx.fragment.app.Fragment
import com.hqq.core.ui.base.BaseVmActivity
import com.hqq.core.utils.FragmentUtils
import com.hqq.example.R
import com.hqq.example.databinding.ActivityFragmentTestBinding
import com.hqq.example.demo.TestFragment

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.demo
 * @FileName :   FragmentTestActivity
 * @Date : 2020/7/28 0028  下午 5:51
 * @Email :  qiqiang213@gmail.com
 * @Descrive :
 */
class FragmentTestActivity : BaseVmActivity<FragmentTestViewModel, ActivityFragmentTestBinding>() {

    override val layoutId: Int
        get() = R.layout.activity_fragment_test

    override fun initViews() {
        val fragment: Fragment = TestFragment.newInstance()
        FragmentUtils(supportFragmentManager).addOrShowFragment(fragment, R.id.fl_layout)
    }

    override val bindingViewModelId: Int
        get() = 0

}