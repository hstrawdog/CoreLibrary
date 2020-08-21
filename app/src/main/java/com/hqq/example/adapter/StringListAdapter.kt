package com.hqq.example.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.hqq.example.R

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.adapter
 * @FileName :   StringListAdapter
 * @Date : 2019/10/28 0028  下午 1:42
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
class StringListAdapter : BaseQuickAdapter<String?, BaseViewHolder>(R.layout.item_main) {
    override fun convert(helper: BaseViewHolder, item: String?) {
        helper.setText(R.id.tv_title, item)
    }
}