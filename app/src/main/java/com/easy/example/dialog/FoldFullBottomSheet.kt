package com.easy.example.dialog

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.easy.core.ui.dialog.BaseBottomDialog
import com.easy.example.R
import com.easy.example.adapter.StringAdapter

/**
 * @Author : huangqiqiang
 * @Package : com.easy.coreapp.dialog
 * @FileName :   FullBottomSheet
 * @Date : 2019/4/29 0029  下午 2:06
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class FoldFullBottomSheet : BaseBottomDialog() {
    var mStringAdapter = StringAdapter()


    override fun getDialogWeight(): Int {
        TODO("Not yet implemented")
    }

    override fun getLayoutViewId(): Int {
      return R.layout.dialog_bottom_sheet
    }
    override fun initView() {
        val recyclerView: RecyclerView = rootView!!.findViewById(R.id.rc_list)
        recyclerView.layoutManager = LinearLayoutManager(context)
        for (i in 0..19) {
            mStringAdapter.add("    $i")
        }
        recyclerView.adapter = mStringAdapter
    }

    override val transparentBottomSheetStyle: Int
        get() = R.style.TransparentBottomSheetStyle_behavior


}