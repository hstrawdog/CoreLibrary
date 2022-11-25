package com.hqq.example.adapter

import android.content.Intent
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.hqq.example.R
import com.hqq.example.bean.MainBean

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.iblibrary.adapter
 * @FileName :   MainAdapter
 * @Date : 2018/11/22 0022  下午 7:45
 * @Descrive :
 * @Email :  qiqiang213@gmail.com
 */
class MainAdapter : BaseQuickAdapter<MainBean<*>, BaseViewHolder>(R.layout.item_main), LoadMoreModule, OnItemClickListener {
    init {
        setOnItemClickListener(this)
    }

    override fun convert(helper: BaseViewHolder, item: MainBean<*>) {
        helper.setText(R.id.tv_title, item.title)
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        context.startActivity(Intent(context, getItem(position).className))
    }

}