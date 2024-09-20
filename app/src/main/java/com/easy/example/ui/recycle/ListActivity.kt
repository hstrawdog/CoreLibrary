package com.easy.example.ui.recycle

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter4.BaseMultiItemAdapter
import com.chad.library.adapter4.viewholder.QuickViewHolder
import com.easy.core.recycle.HeaderItemDecoration
import com.easy.core.recycle.HeaderItemDecoration.StickyHeaderInterface
import com.easy.core.ui.list.BaseListActivity
import com.easy.example.R

/**
 * @Author : huangqiqiang
 * @Package : com.easy.iblibrary.ui.activity
 * @FileName :   ListActivity
 * @Date : 2018/12/14 0014
 * @Describe : TODO
 * @Email :  qiqiang213@gmail.com
 */
class ListActivity(override val adapter: ListAdapter = ListAdapter()) : BaseListActivity() {

    override fun getLayoutViewId(): Int {
        return R.layout.activity_list;
    }

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

        adapter.add(ListBean(1))
        adapter.add(ListBean(1))
        adapter.add(ListBean(1))
        adapter.add(ListBean(1))
        adapter.add(ListBean())
        adapter.add(ListBean())
        adapter.add(ListBean())
        adapter.add(ListBean())
        adapter.add(ListBean())
        adapter.add(ListBean())
        adapter.add(ListBean())
        adapter.add(ListBean())
        adapter.add(ListBean())
        adapter.add(ListBean())
        adapter.add(ListBean())
        adapter.add(ListBean())
        adapter.add(ListBean())
        adapter.add(ListBean())
        adapter.add(ListBean())
        adapter.add(ListBean())
        adapter.add(ListBean())
        adapter.add(ListBean())
        adapter.add(ListBean())
        adapter.add(ListBean())
        adapter.add(ListBean())
        adapter.add(ListBean())
        adapter.add(ListBean())
        adapter.add(ListBean())
        adapter.add(ListBean())
        adapter.add(ListBean())
        adapter.add(ListBean())
        adapter.add(ListBean())
        adapter.add(ListBean())
        adapter.add(ListBean())
        adapter.add(ListBean())
        adapter.add(ListBean())
        adapter.add(ListBean())
        adapter.add(ListBean())
        adapter.add(ListBean())
        adapter.add(ListBean())

    }

    class ListBean(itemType1: Int = 0) {
        var itemType: Int = itemType1

    }


    class ListAdapter : BaseMultiItemAdapter<ListBean> {
        constructor() {
            addItemType(0, object : OnMultiItemAdapterListener<ListBean, QuickViewHolder> { // 类型 1
                override fun onCreate(context: Context, parent: ViewGroup, viewType: Int): QuickViewHolder {
                    // 创建 viewholder
                    return QuickViewHolder(R.layout.item_main, parent)
                }

                override fun onBind(holder: QuickViewHolder, position: Int, item: ListBean?) {
                    // 绑定 item 数据
                }
            }).addItemType(1, object : OnMultiItemAdapterListener<ListBean, QuickViewHolder> { // 类型 1
                override fun onCreate(context: Context, parent: ViewGroup, viewType: Int): QuickViewHolder {
                    // 创建 viewholder
                    return QuickViewHolder(R.layout.item_head, parent)
                }

                override fun onBind(holder: QuickViewHolder, position: Int, item: ListBean?) {
                    // 绑定 item 数据
                }
            })
                .onItemViewType { position, list ->
                    list[position].itemType
                }


        }


    }


}