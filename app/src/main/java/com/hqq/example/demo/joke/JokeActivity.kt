package com.hqq.example.demo.joke

import android.app.Activity
import android.content.Intent
import com.hqq.core.ui.vm.BaseVmListActivity
import com.hqq.example.R
import com.hqq.example.databinding.ActivityJokeBinding

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.demo.joke
 * @FileName :   JokeActivity
 * @Date : 2020/8/5 0005  下午 2:18
 * @Email :  qiqiang213@gmail.com
 * @Descrive :
 */
class JokeActivity : BaseVmListActivity<ActivityJokeBinding, JokeViewModel, JokeAdapter>() {
    override val bindingViewModelId: Int
        get() = R.layout.activity_joke

    override fun initAdapter(): JokeAdapter {
        return JokeAdapter()
    }

    override fun initData() {
        mAdapter!!.loadMoreModule.setOnLoadMoreListener(this)
    }


    companion object {
        fun open(context: Activity) {
            val starter = Intent(context, JokeActivity::class.java)
            context.startActivityForResult(starter, -1)
        }
    }
}