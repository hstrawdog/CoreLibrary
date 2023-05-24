package com.qq.readbook.ui.source

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.hqq.core.ui.list.BaseListActivity
import com.hqq.core.utils.ClipboardTool
import com.hqq.core.utils.gson.GsonUtil
import com.hqq.core.utils.StringUtils
import com.hqq.core.utils.ToastUtils
import com.qq.readbook.R
import com.qq.readbook.bean.book.BookSource
import com.qq.readbook.room.RoomUtils

/**
 * @version V1.0 <描述当前版本功能>
 * 在此写用途
 * @author: huangqiqiang
 * @FileName: com.qq.readbook.ui.source.BookSurceListActivity.java
 * @emain: 593979591@qq.com
 * @date: 2021-03-07 15:37
 */
internal class BookSourceListActivity : BaseListActivity() {
    override val layoutViewId: Int = R.layout.activity_book_source_list

    override val adapter: BookSourceListAdapter = BookSourceListAdapter().apply {
        setOnItemClickListener { adapter, view, position ->

            var json = GsonUtil.toJsonString(getItem(position))
            ClipboardTool.copyContentToClipboard(json)
            ToastUtils.showToast("源复制到剪切板")
        }

    }

    override fun initData() {
        val list = RoomUtils.getBookSourceDao().getAll()
        if (list.isNotEmpty()) {
            adapter.setNewInstance(list)

        }
    }

    class BookSourceListAdapter() : BaseQuickAdapter<BookSource, BaseViewHolder>(R.layout.item_book_source_list) {
        override fun convert(holder: BaseViewHolder, item: BookSource) {
            holder.setText(R.id.tv_name, item.sourceName)
        }
    }

}