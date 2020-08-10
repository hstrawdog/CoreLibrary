package com.hqq.example.ui.list

import android.os.Handler
import android.view.View
import com.hqq.core.recycle.HeaderItemDecoration
import com.hqq.core.recycle.HeaderItemDecoration.StickyHeaderInterface
import com.hqq.core.ui.base.BaseListActivity
import com.hqq.core.utils.log.LogUtils.e
import com.hqq.example.R
import com.hqq.example.adapter.MainAdapter
import com.hqq.example.bean.MainBean
import com.hqq.example.ui.bar.ToolBarActivity
import com.hqq.example.ui.view.page.IFragmentActivityBuilder
import java.util.*
import kotlin.collections.ArrayList

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.iblibrary.ui.activity
 * @FileName :   ListActivity
 * @Date : 2018/12/14 0014
 * @Descrive : TODO
 * @Email :  qiqiang213@gmail.com
 */
class ListActivity : BaseListActivity<MainAdapter?>() {
    override fun initAdapter(): MainAdapter? {
        return MainAdapter()
    }

    override fun initData() {
        data
    }

    private val data: Unit
        private get() {
            mLoadingView!!.show()
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
                mLoadingView!!.dismiss()
                mBaseListModel!!.fillingData(list as ArrayList<Nothing>)
            }, 3 * 1000.toLong())
            mRcList!!.addItemDecoration(HeaderItemDecoration(mRcList!!, object : StickyHeaderInterface {
                override fun getHeaderPositionForItem(itemPosition: Int): Int {
                    var itemPosition = itemPosition
                    e("getHeaderPositionForItem----------------  $itemPosition")
                    var headerPosition = 0
                    do {
                        if (isHeader(itemPosition)) {
                            headerPosition = itemPosition
                            break
                        }
                        itemPosition -= 1
                    } while (itemPosition >= 0)
                    return headerPosition
                }

                override fun getHeaderLayout(headerPosition: Int): Int {
                    return R.layout.item_main
                }

                override fun bindHeaderData(header: View?, headerPosition: Int) {}
                override fun isHeader(itemPosition: Int): Boolean {
                    return itemPosition == 6
                }
            }))
        }
}