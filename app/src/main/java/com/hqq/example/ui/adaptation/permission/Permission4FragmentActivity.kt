package com.hqq.example.ui.adaptation.permission

import com.hqq.core.ui.base.BaseActivity
import com.hqq.core.ui.base.BaseViewBindingActivity
import com.hqq.core.utils.FragmentUtils
import com.hqq.example.R
import com.hqq.example.databinding.ActivityFragmentTestBinding
import com.hqq.example.databinding.ActivityPermissionBinding

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui.adaptation.permission
 * @Date : 下午 3:55
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class Permission4FragmentActivity : BaseViewBindingActivity<ActivityFragmentTestBinding>() {

    override fun initView() {
        FragmentUtils(this).addOrShowFragment(Permission4Fragment(),R.id.fl_layout)
    }


}