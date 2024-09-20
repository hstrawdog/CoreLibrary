package com.easy.example.demo

import com.easy.core.ui.list.BaseListActivity
import com.easy.example.adapter.MainAdapter
import com.easy.example.bean.MainBean
import com.easy.example.demo.joke.JokeActivity
import com.easy.example.demo.login.LoginActivity
import com.easy.example.demo.news.NewsActivity
import com.easy.example.demo.weather.WeatherActivity

/**
 * @Author : huangqiqiang
 * @Package : com.easy.example.demo
 * @FileName :   DemoIndexActivity
 * @Date : 2020/8/11 0011  上午 11:31
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class DemoIndexActivity2() : BaseListActivity() {
    override val adapter: MainAdapter = MainAdapter()



    override fun initData() {
        adapter.add(MainBean("聚合天气", WeatherActivity::class.java))
        adapter.add(MainBean("热点新闻", NewsActivity::class.java))
        adapter.add(MainBean("热门笑话", JokeActivity::class.java))
        adapter.add(MainBean("模拟登录", LoginActivity::class.java))
    }


}