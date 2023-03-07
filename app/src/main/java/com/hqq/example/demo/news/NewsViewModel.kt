package com.hqq.example.demo.news

import androidx.lifecycle.LifecycleOwner
import com.hqq.core.ui.list.BaseListViewModel
import com.hqq.example.demo.net.HttpManager.getNews
import com.hqq.example.demo.net.NetCallback

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.demo.news
 * @FileName :   NewViewModel
 * @Date : 2020/8/5 0005  上午 10:36
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
class NewsViewModel : BaseListViewModel() {
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        getNews(object : NetCallback<News>() {
            override fun onSuccess(response: News) {
                if (response != null) {
                    setData(response.data)
                } else {
                    setData(ArrayList<News.DataBean>())
                }
            }

            override fun onFail(code: Int, message: String) {
                showToast(message)
            }
        })
    }
}