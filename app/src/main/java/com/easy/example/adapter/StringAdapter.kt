package com.easy.example.adapter

import android.content.Context
import android.view.ViewGroup
import com.chad.library.adapter4.BaseQuickAdapter
import com.chad.library.adapter4.viewholder.QuickViewHolder
import com.easy.example.R

/**
 * @Author : huangqiqiang
 * @Package : com.easy.coreapp.adapter
 * @FileName :   StringAdapter
 * @Date : 2019/4/29 0029  下午 2:23
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class StringAdapter : BaseQuickAdapter<String, QuickViewHolder>() {


    override fun onBindViewHolder(helper: QuickViewHolder, position: Int, item: String?) {
        helper.setText(R.id.tv_title, item)

    }

    override fun onCreateViewHolder(context: Context, parent: ViewGroup, viewType: Int): QuickViewHolder {
        return QuickViewHolder(R.layout.item_main, parent)
    }


}