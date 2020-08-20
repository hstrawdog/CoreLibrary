package com.hqq.example.ui.recycle

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hqq.core.recycle.PagerSnapHelper
import com.hqq.core.ui.base.BaseActivity
import com.hqq.core.widget.divider.Divider
import com.hqq.core.widget.divider.DividerBuilder
import com.hqq.core.widget.divider.DividerItemDecoration
import com.hqq.example.R
import com.hqq.example.adapter.FullPagerSnapAdapter

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.app.ui.activity.bannner
 * @FileName :   FullPagerSnapActivity
 * @Date : 2019/6/18 0018  上午 9:50
 * @Email :  qiqiang213@gmail.com
 * @Descrive :
 */
class FullPagerSnapActivity : BaseActivity() {
    var mRcList: RecyclerView? = null

    override val layoutViewId: Int
        get() = R.layout.activity_full_pager_snap
    var mFullPagerSnapAdapter: FullPagerSnapAdapter? = null
    override fun initView() {
        mRcList = findViewById(R.id.rc_list)
        mRcList?.setLayoutManager(GridLayoutManager(this, 2, RecyclerView.HORIZONTAL, false))
        mFullPagerSnapAdapter = FullPagerSnapAdapter()
        mRcList?.setAdapter(mFullPagerSnapAdapter)
        for (i in 0..34) {
            mFullPagerSnapAdapter!!.addData("item $i")
        }
        PagerSnapHelper(6).attachToRecyclerView(mRcList)
        mRcList?.addItemDecoration(object : DividerItemDecoration() {
            override fun getDivider(parent: RecyclerView?, itemPosition: Int): Divider? {
                val divider: DividerBuilder
                divider = DividerBuilder()
                divider.setRightSideLine(activity!!, R.color.color_main, R.dimen.x5)
                divider.setBottomSideLine(activity!!, R.color.color_main, R.dimen.x5)
                return divider.create()
            }
        })
    }
}