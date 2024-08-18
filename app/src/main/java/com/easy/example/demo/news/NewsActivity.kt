package com.easy.example.demo.news

import android.app.Activity
import android.content.Intent
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.easy.core.ui.list.BaseVmListActivity
import com.easy.example.R
import com.easy.example.databinding.ActivityNewsBinding
import com.easy.example.ui.web.WebActivity

/**
 * @Author : huangqiqiang
 * @Package : com.easy.example.demo.news
 * @FileName :   NewsActivity
 * @Date : 2020/8/5 0005  上午 10:34
 * @Email :  qiqiang213@gmail.com
 * @Describe :
 */
class NewsActivity(override val adapter: NewsAdapter = NewsAdapter()) :
    BaseVmListActivity<NewsViewModel, ActivityNewsBinding>(), OnItemClickListener {
    companion object {
        fun open(context: Activity) {
            val starter = Intent(context, NewsActivity::class.java)
            context.startActivityForResult(starter, -1)
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_news
    }


    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        WebActivity.open(activity!!, this.adapter!!.getItem(position)!!.url, this.adapter!!.getItem(position)!!.title)
    }

//    override fun bindingViewModelId(): Int {
//        return 0
//    }


    override fun initData() {
        adapter.setOnItemClickListener(this)
    }
}