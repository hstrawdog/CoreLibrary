package com.easy.example.ui.jetpack.databinding

import androidx.fragment.app.Fragment
import com.easy.core.ui.base.BaseVmActivity
import com.easy.core.utils.FragmentUtils
import com.easy.example.R
import com.easy.example.databinding.ActivityFragmentTestBinding
import com.easy.example.demo.TestFragment

/**
 * @Author : huangqiqiang
 * @Package : com.easy.example.demo
 * @FileName :   FragmentTestActivity
 * @Date : 2020/7/28 0028  下午 5:51
 * @Email :  qiqiang213@gmail.com
 * @Describe :
 */
class FragmentTestActivity : BaseVmActivity<FragmentTestViewModel, ActivityFragmentTestBinding>() {

    override fun getLayoutId(): Int {
        return   R.layout.activity_fragment_test
    }

//    override fun bindingViewModelId(): Int {
//        return 0
//    }


    override fun initViews() {
        val fragment: Fragment = com.easy.example.demo.TestFragment.newInstance()
        FragmentUtils(supportFragmentManager).showOrAddFragment(fragment, R.id.fl_layout)
    }


}