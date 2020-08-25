package com.hqq.example.ui.list

import android.os.Handler
import android.view.View
import com.hqq.core.recycle.HeaderItemDecoration
import com.hqq.core.recycle.HeaderItemDecoration.StickyHeaderInterface
import com.hqq.core.ui.base.BaseListActivity
import com.hqq.example.R
import com.hqq.example.adapter.MainAdapter
import com.hqq.example.bean.MainBean
import com.hqq.example.ui.bar.ToolBarActivity
import com.hqq.example.ui.view.page.IFragmentActivityBuilder

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.iblibrary.ui.activity
 * @FileName :   ListActivity
 * @Date : 2018/12/14 0014
 * @Descrive : TODO
 * @Email :  qiqiang213@gmail.com
 */
class ListActivity(override val baseAdapter: MainAdapter? = MainAdapter()) : BaseListActivity<MainAdapter?>() {
    override fun initData() {

        listView?.addItemDecoration(HeaderItemDecoration(listView!!, object : StickyHeaderInterface {
            override fun getHeaderPositionForItem(itemPosition: Int): Int {
                return if (itemPosition >= 6) 6 else -1
            }

            override fun getHeaderLayout(headerPosition: Int): Int {
                return R.layout.head_list_activity
            }

            override fun bindHeaderData(header: View?, headerPosition: Int) {

            }

            override fun isHeader(itemPosition: Int): Boolean {
                return itemPosition == 6
            }
        }))

        baseAdapter?.loadMoreModule?.setOnLoadMoreListener(this)



        data
    }

    override fun onLoadMore() {
        data
    }

    private val data: Unit
        private get() {
            loadingView!!.show()
            Handler().postDelayed({
                val list: MutableList<MainBean<*>> = ArrayList()
                list.add(MainBean("ItoolBar 控制 ", ToolBarActivity::class.java))
                list.add(MainBean("fragment 加载", IFragmentActivityBuilder::class.java))
                list.add(MainBean("ItoolBar 控制 ", ToolBarActivity::class.java))
                list.add(MainBean("fragment 加载", IFragmentActivityBuilder::class.java))
                list.add(MainBean("ItoolBar 控制 ", ToolBarActivity::class.java))
                list.add(MainBean("fragment 加载", IFragmentActivityBuilder::class.java))
                list.add(MainBean("ItoolBar 控制 ", ToolBarActivity::class.java))
                list.add(MainBean("fragment 加载", IFragmentActivityBuilder::class.java))
                list.add(MainBean("ItoolBar 控制 ", ToolBarActivity::class.java))
                list.add(MainBean("fragment 加载", IFragmentActivityBuilder::class.java))
                list.add(MainBean("ItoolBar 控制 ", ToolBarActivity::class.java))
                list.add(MainBean("fragment 加载", IFragmentActivityBuilder::class.java))
                list.add(MainBean("ItoolBar 控制 ", ToolBarActivity::class.java))
                list.add(MainBean("fragment 加载", IFragmentActivityBuilder::class.java))
                list.add(MainBean("ItoolBar 控制 ", ToolBarActivity::class.java))
                list.add(MainBean("fragment 加载", IFragmentActivityBuilder::class.java))
                list.add(MainBean("ItoolBar 控制 ", ToolBarActivity::class.java))
                list.add(MainBean("fragment 加载", IFragmentActivityBuilder::class.java))
                list.add(MainBean("ItoolBar 控制 ", ToolBarActivity::class.java))
                list.add(MainBean("fragment 加载", IFragmentActivityBuilder::class.java))
                list.add(MainBean("ItoolBar 控制 ", ToolBarActivity::class.java))
                list.add(MainBean("fragment 加载", IFragmentActivityBuilder::class.java))
                list.add(MainBean("ItoolBar 控制 ", ToolBarActivity::class.java))
                list.add(MainBean("fragment 加载", IFragmentActivityBuilder::class.java))
                list.add(MainBean("ItoolBar 控制 ", ToolBarActivity::class.java))
                list.add(MainBean("fragment 加载", IFragmentActivityBuilder::class.java))
                loadingView!!.dismiss()
                mBaseListModel!!.fillingData(list as ArrayList<Nothing>)
            }, 3 * 1000.toLong())
        }
}