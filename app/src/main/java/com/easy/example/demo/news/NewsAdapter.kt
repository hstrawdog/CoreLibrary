package com.easy.example.demo.news

import android.content.Context
import android.view.ViewGroup
import com.chad.library.adapter4.BaseQuickAdapter
import com.chad.library.adapter4.viewholder.QuickViewHolder
import com.easy.example.R


/**
 * @Author : huangqiqiang
 * @Package : com.easy.example.demo.news
 * @FileName :   NewsAdapter
 * @Date : 2020/8/5 0005  上午 10:37
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class NewsAdapter : BaseQuickAdapter<News.DataBean, QuickViewHolder>() {

    override fun onBindViewHolder(holder: QuickViewHolder, position: Int, item: News.DataBean?) {
        holder.setText(R.id.textView18, item?.title)

    }

    override fun onCreateViewHolder(context: Context, parent: ViewGroup, viewType: Int): QuickViewHolder {
        return QuickViewHolder(R.layout.item_news, parent)
    }
}
