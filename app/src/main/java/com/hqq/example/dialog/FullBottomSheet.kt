package com.hqq.example.dialog

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hqq.core.ui.base.BaseBottomDialog
import com.hqq.core.utils.ResourcesUtils.getDimen
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
class FullBottomSheet : BaseBottomDialog() {
    var mStringAdapter = StringAdapter()
    override fun getLayoutViewId(): Int {
        return R.layout.dialog_bottom_sheet
    }

    override fun initView() {
        val recyclerView: RecyclerView = mRootView!!.findViewById(R.id.rc_list)
        recyclerView.layoutManager = LinearLayoutManager(context)
        for (i in 0..19) {
            mStringAdapter.addData("    $i")
        }
        recyclerView.adapter = mStringAdapter
    }

    override val height: Int
        get() = getDimen(context!!, R.dimen.x750).toInt()
}