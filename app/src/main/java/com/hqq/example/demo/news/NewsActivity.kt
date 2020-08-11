package com.hqq.example.demo.news

import android.app.Activity
import android.content.Intent
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.hqq.core.ui.vm.BaseVmListActivity
import com.hqq.example.R
import com.hqq.example.databinding.ActivityNewsBinding
import com.hqq.example.ui.web.WebActivity

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.demo.news
 * @FileName :   NewsActivity
 * @Date : 2020/8/5 0005  上午 10:34
 * @Email :  qiqiang213@gmail.com
 * @Descrive :
 */
class NewsActivity : BaseVmListActivity<ActivityNewsBinding, NewsViewModel, NewsAdapter>() {
    companion object {
        fun open(context: Activity) {
            val starter = Intent(context, NewsActivity::class.java)
            context.startActivityForResult(starter, -1)
        }
    }

    override val layoutId: Int
        get() = R.layout.activity_news
    override val bindingViewModelId: Int
        get() = 0

    override fun initAdapter(): NewsAdapter {
        return NewsAdapter()
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        WebActivity.open(mActivity!!, mAdapter!!.getItem(position)!!.url, mAdapter!!.getItem(position)!!.title)
    }


    override fun initData() {


    }
}