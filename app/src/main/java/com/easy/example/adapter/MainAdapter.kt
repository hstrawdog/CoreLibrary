package com.easy.example.adapter

import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter4.BaseQuickAdapter
import com.chad.library.adapter4.viewholder.QuickViewHolder
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
class MainAdapter : BaseQuickAdapter<MainBean<*>, QuickViewHolder>(),
    BaseQuickAdapter.OnItemClickListener<MainBean<*>> {

    init {

        setOnItemClickListener(this)

    }

    override fun onCreateViewHolder(context: Context, parent: ViewGroup, viewType: Int): QuickViewHolder {

        return QuickViewHolder(R.layout.item_main,parent)
    }

    override fun onBindViewHolder(helper: QuickViewHolder, position: Int, item: MainBean<*>?) {
        helper.setText(R.id.tv_title, item?.title)
    }

    override fun onClick(adapter: BaseQuickAdapter<MainBean<*>, *>, view: View, position: Int) {
        context.startActivity(Intent(context, getItem(position)?.className))
    }



}