package com.easy.example.ui.adaptation.permission

import com.easy.core.ui.base.BaseViewBindingActivity
import com.easy.core.utils.FragmentUtils
import com.easy.example.R
import com.easy.example.databinding.ActivityFragmentTestBinding

/**
 * @Author : huangqiqiang
 * @Package : com.easy.example.ui.adaptation.permission
 * @Date : 下午 3:55
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class Permission4FragmentActivity : BaseViewBindingActivity<ActivityFragmentTestBinding>() {

    override fun initView() {
        FragmentUtils(this).showOrAddFragment(Permission4Fragment(),R.id.fl_layout)
    }


}