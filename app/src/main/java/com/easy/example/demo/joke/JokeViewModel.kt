package com.easy.example.demo.joke

import androidx.lifecycle.LifecycleOwner
import com.easy.core.ui.list.BaseListViewModel
import com.easy.example.demo.net.HttpManager.getJoke
import com.easy.example.demo.net.NetCallback

/**
 * @Author : huangqiqiang
 * @Package : com.easy.example.demo.joke
 * @FileName :   JokeViewModel
 * @Date : 2020/8/5 0005  下午 2:29
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class JokeViewModel : BaseListViewModel() {
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        jokeList
    }

    override fun onLoadMore() {
        super.onLoadMore()
        jokeList
    }

    private val jokeList: Unit
        private get() {
            getJoke(pageCount, object : NetCallback<com.easy.example.demo.joke.Joke>() {
                override fun onSuccess(response: com.easy.example.demo.joke.Joke) {
                    setData(response.data)
                }
            })
        }
}