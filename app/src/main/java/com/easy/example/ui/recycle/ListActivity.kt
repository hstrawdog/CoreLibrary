package com.easy.example.ui.recycle

import android.view.View
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.easy.core.recycle.HeaderItemDecoration
import com.easy.core.recycle.HeaderItemDecoration.StickyHeaderInterface
import com.easy.core.ui.list.BaseListActivity
import com.easy.example.R

/**
 * @Author : huangqiqiang
 * @Package : com.easy.iblibrary.ui.activity
 * @FileName :   ListActivity
 * @Date : 2018/12/14 0014
 * @Descrive : TODO
 * @Email :  qiqiang213@gmail.com
 */
class ListActivity(override val adapter: ListAdapter = ListAdapter()) : BaseListActivity() {

    override val layoutViewId: Int = R.layout.activity_list;

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

        adapter.addData(ListBean(1))
        adapter.addData(ListBean(1))
        adapter.addData(ListBean(1))
        adapter.addData(ListBean(1))
        adapter.addData(ListBean())
        adapter.addData(ListBean())
        adapter.addData(ListBean())
        adapter.addData(ListBean())
        adapter.addData(ListBean())
        adapter.addData(ListBean())
        adapter.addData(ListBean())
        adapter.addData(ListBean())
        adapter.addData(ListBean())
        adapter.addData(ListBean())
        adapter.addData(ListBean())
        adapter.addData(ListBean())
        adapter.addData(ListBean())
        adapter.addData(ListBean())
        adapter.addData(ListBean())
        adapter.addData(ListBean())
        adapter.addData(ListBean())
        adapter.addData(ListBean())
        adapter.addData(ListBean())
        adapter.addData(ListBean())
        adapter.addData(ListBean())
        adapter.addData(ListBean())
        adapter.addData(ListBean())
        adapter.addData(ListBean())
        adapter.addData(ListBean())
        adapter.addData(ListBean())
        adapter.addData(ListBean())
        adapter.addData(ListBean())
        adapter.addData(ListBean())
        adapter.addData(ListBean())
        adapter.addData(ListBean())
        adapter.addData(ListBean())
        adapter.addData(ListBean())
        adapter.addData(ListBean())
        adapter.addData(ListBean())
        adapter.addData(ListBean())

    }

    class ListBean(itemType1: Int = 0) : MultiItemEntity {
        override var itemType: Int = itemType1

    }


    class ListAdapter : BaseMultiItemQuickAdapter<ListBean, BaseViewHolder> {
        constructor() {
            addItemType(0, R.layout.item_main)
            addItemType(1, R.layout.item_head)
        }

        override fun convert(holder: BaseViewHolder, item: ListBean) {

        }

    }


}