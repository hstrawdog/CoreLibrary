package com.easy.example.demo.joke

import android.app.Activity
import android.content.Intent
import com.easy.core.ui.list.BaseVmListActivity
import com.easy.example.R
import com.easy.example.databinding.ActivityJokeBinding

/**
 * @Author : huangqiqiang
 * @Package : com.easy.example.demo.joke
 * @FileName :   JokeActivity
 * @Date : 2020/8/5 0005  下午 2:18
 * @Email :  qiqiang213@gmail.com
 * @Descrive :
 */
class JokeActivity(override val adapter: com.easy.example.demo.joke.JokeAdapter = com.easy.example.demo.joke.JokeAdapter())
    : BaseVmListActivity<JokeViewModel, ActivityJokeBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_joke
    override val bindingViewModelId: Int = 0

    override fun initData() {
        adapter.loadMoreModule.setOnLoadMoreListener(this)
    }


    companion object {
        fun open(context: Activity) {
            val starter = Intent(context, JokeActivity::class.java)
            context.startActivityForResult(starter, -1)
        }
    }
}