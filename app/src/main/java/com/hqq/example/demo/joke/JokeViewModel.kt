package com.hqq.example.demo.joke

import com.hqq.core.ui.list.BaseListViewModel
import com.hqq.example.demo.net.HttpManager.getJoke
import com.hqq.example.demo.net.NetCallback

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.demo.joke
 * @FileName :   JokeViewModel
 * @Date : 2020/8/5 0005  下午 2:29
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
class JokeViewModel : BaseListViewModel() {
    override fun onCrete() {
        super.onCrete()
        jokeList
    }

    override fun onLoadMore() {
        super.onLoadMore()
        jokeList
    }

    private val jokeList: Unit
        private get() {
            getJoke(pageCount, object : NetCallback<Joke>() {
                override fun onSuccess(response: Joke) {
                    setData(response.data)
                }
            })
        }
}