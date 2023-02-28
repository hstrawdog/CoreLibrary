package com.hqq.example.ui.dialog

import com.hqq.core.ui.base.BaseActivity
import com.hqq.core.ui.base.BaseViewBindingActivity
import com.hqq.example.R
import com.hqq.example.databinding.ActivityBottomSheetBinding
import com.hqq.example.dialog.FoldFullBottomSheet
import com.hqq.example.dialog.FullBottomSheet

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.coreapp.ui.activity
 * @FileName :   BottomSheetActivity
 * @Date : 2019/4/29 0029
 * @Email :  qiqiang213@gmail.com
 * @Descrive : TODO
 */
class BottomSheetActivity : BaseViewBindingActivity<ActivityBottomSheetBinding>() {

    override fun initView() {
        binding.button9.setOnClickListener {
            val mFullBottomSheetFragment = FullBottomSheet()
            mFullBottomSheetFragment.show(supportFragmentManager)
        }
        binding.button10.setOnClickListener {
            val mFullBottomSheetFragment = FoldFullBottomSheet()
            mFullBottomSheetFragment.show(supportFragmentManager)
        }
    }
}