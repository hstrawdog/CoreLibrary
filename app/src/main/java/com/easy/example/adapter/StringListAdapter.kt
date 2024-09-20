package com.easy.example.adapter

import android.content.Context
import android.view.ViewGroup
import com.chad.library.adapter4.BaseQuickAdapter
import com.chad.library.adapter4.viewholder.QuickViewHolder
import com.easy.example.R

/**
 * @Author : huangqiqiang
 * @Package : com.easy.example.adapter
 * @FileName :   StringListAdapter
 * @Date : 2019/10/28 0028  下午 1:42
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class StringListAdapter : BaseQuickAdapter<String, QuickViewHolder>() {


    override fun onBindViewHolder(helper: QuickViewHolder, position: Int, item: String?) {
        helper.setText(R.id.tv_title, item)

    }


    override fun onCreateViewHolder(context: Context, parent: ViewGroup, viewType: Int): QuickViewHolder {
        return QuickViewHolder(R.layout.item_main, parent)
    }
}