package com.easy.example.adapter

import android.content.Intent
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.easy.example.R
import com.easy.example.bean.MainBean

/**
 * @Author : huangqiqiang
 * @Package : com.easy.iblibrary.adapter
 * @FileName :   MainAdapter
 * @Date : 2018/11/22 0022  下午 7:45
 * @Describe :
 * @Email :  qiqiang213@gmail.com
 */
class MainAdapter : BaseQuickAdapter<com.easy.example.bean.MainBean<*>, BaseViewHolder>(R.layout.item_main), LoadMoreModule, OnItemClickListener {
    init {
        setOnItemClickListener(this)
    }

    override fun convert(helper: BaseViewHolder, item: com.easy.example.bean.MainBean<*>) {
        helper.setText(R.id.tv_title, item.title)
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        context.startActivity(Intent(context, getItem(position).className))
    }

}