package com.easy.example.demo.news

import androidx.lifecycle.LifecycleOwner
import com.easy.core.ui.list.BaseListViewModel
import com.easy.example.demo.net.HttpManager.getNews
import com.easy.example.demo.net.NetCallback

/**
 * @Author : huangqiqiang
 * @Package : com.easy.example.demo.news
 * @FileName :   NewViewModel
 * @Date : 2020/8/5 0005  上午 10:36
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
class NewsViewModel : BaseListViewModel() {
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        getNews(object : NetCallback<com.easy.example.demo.news.News>() {
            override fun onSuccess(response: com.easy.example.demo.news.News) {
                if (response != null) {
                    setData(response.data)
                } else {
                    setData(ArrayList<com.easy.example.demo.news.News.DataBean>())
                }
            }

            override fun onFail(code: Int, message: String) {
                showToast(message)
            }
        })
    }
}