package com.easy.example.ui.recycle

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.easy.core.recycle.PagerSnapHelper
import com.easy.core.ui.base.BaseActivity
import com.easy.core.widget.divider.Divider
import com.easy.core.widget.divider.DividerBuilder
import com.easy.core.widget.divider.DividerItemDecoration
import com.easy.example.R
import com.easy.example.adapter.FullPagerSnapAdapter

/**
 * @Author : huangqiqiang
 * @Package : com.easy.core.app.ui.activity.bannner
 * @FileName :   FullPagerSnapActivity
 * @Date : 2019/6/18 0018  上午 9:50
 * @Email :  qiqiang213@gmail.com
 * @Describe :
 */
class FullPagerSnapActivity : BaseActivity() {
    var mRcList: RecyclerView? = null

    var mFullPagerSnapAdapter: FullPagerSnapAdapter? = null
    override fun getLayoutViewId(): Int {
        return R.layout.activity_full_pager_snap
    }

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
                divider.setRightSideLine(activity, R.color.color_main, R.dimen.x5)
                divider.setBottomSideLine(activity, R.color.color_main, R.dimen.x5)
                return divider.create()
            }
        })
    }
}