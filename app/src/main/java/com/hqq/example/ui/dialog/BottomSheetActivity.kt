package com.hqq.example.ui.dialog

import com.hqq.core.ui.base.BaseActivity
import com.hqq.example.R
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
class BottomSheetActivity : BaseActivity() {
    override val layoutViewId: Int = R.layout.activity_bottom_sheet

    override fun initView() {}
    fun onViewClicked() {
        val mFullBottomSheetFragment = FullBottomSheet()
        mFullBottomSheetFragment.show(supportFragmentManager)
    }

    fun onViewClicked1() {
        val mFullBottomSheetFragment = FoldFullBottomSheet()
        mFullBottomSheetFragment.show(supportFragmentManager)
    }
}