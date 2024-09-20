package com.easy.example.demo.joke

import android.content.Context
import android.view.ViewGroup
import com.chad.library.adapter4.BaseQuickAdapter
import com.chad.library.adapter4.QuickAdapterHelper
import com.chad.library.adapter4.viewholder.QuickViewHolder
import com.easy.example.R

/**
 * @Author : huangqiqiang
 * @Package : com.easy.example.demo.joke
 * @FileName :   JokeAdapter
 * @Date : 2020/8/5 0005  下午 2:29
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class JokeAdapter : BaseQuickAdapter<Joke.DataBean, QuickViewHolder>() {


    override fun onCreateViewHolder(context: Context, viewGroup: ViewGroup, i: Int): QuickViewHolder {
        return QuickViewHolder(R.layout.item_joke, viewGroup)
    }


    override fun onBindViewHolder(helper: QuickViewHolder, i: Int, item: Joke.DataBean?) {
        helper.setText(R.id.textView19, item!!.content)
        helper.setText(R.id.textView20, item!!.updatetime)
    }
}
