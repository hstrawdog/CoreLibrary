package com.hqq.example.demo

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.hqq.core.ui.base.BaseListActivity
import com.hqq.example.adapter.MainAdapter
import com.hqq.example.bean.MainBean
import com.hqq.example.demo.joke.JokeActivity
import com.hqq.example.demo.login.LoginActivity
import com.hqq.example.demo.news.NewsActivity
import com.hqq.example.demo.weather.WeatherActivity

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.demo
 * @FileName :   DemoIndexActivity
 * @Date : 2020/8/11 0011  上午 11:31
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
class DemoIndexActivity : BaseListActivity<MainAdapter?>() {
    override fun initAdapter(): MainAdapter? {
        return MainAdapter()
    }

    override fun initData() {
        adapter?.addData(MainBean("聚合天气", WeatherActivity::class.java))
        adapter?.addData(MainBean("热点新闻", NewsActivity::class.java))
        adapter?.addData(MainBean("热门笑话", JokeActivity::class.java))
        adapter?.addData(MainBean("模拟登录", LoginActivity::class.java))
    }
}