package com.hqq.example.dialog

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hqq.core.ui.base.BaseBottomDialog
import com.hqq.example.R
import com.hqq.example.adapter.StringAdapter

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.coreapp.dialog
 * @FileName :   FullBottomSheet
 * @Date : 2019/4/29 0029  下午 2:06
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
class FoldFullBottomSheet : BaseBottomDialog() {
    var mStringAdapter = StringAdapter()
    override val layoutViewId: Int
        get() = R.layout.dialog_bottom_sheet

    override fun initView() {
        val recyclerView: RecyclerView = mRootView!!.findViewById(R.id.rc_list)
        recyclerView.layoutManager = LinearLayoutManager(context)
        for (i in 0..19) {
            mStringAdapter.addData("    $i")
        }
        recyclerView.adapter = mStringAdapter
    }

    override val transparentBottomSheetStyle: Int
        get() = R.style.TransparentBottomSheetStyle_behavior
}