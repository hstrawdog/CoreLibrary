package com.easy.example.ui.recycle

import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.easy.core.ui.list.BaseListActivity
import com.easy.example.adapter.MainAdapter
import com.easy.example.bean.MainBean

/**
 * @Author : huangqiqiang
 * @Package : com.easy.iblibrary.ui.activity
 * @FileName :   LoadMoreActivity
 * @Date : 2018/11/23 0023
 * @Descrive : TODO
 * @Email :  qiqiang213@gmail.com
 */
class LoadMoreActivity(override val adapter: MainAdapter = MainAdapter()) : BaseListActivity() {



    override fun initData() {
        adapter.loadMoreModule.setOnLoadMoreListener {
            onLoadMore()
        }
        listModel.fillingData(data as List<*>)
    }

    override fun onLoadMore() {
        Handler().postDelayed({
            listModel.fillingData(data as List<*>)
            adapter.loadMoreModule.loadMoreComplete()

        }, 2000)
    }

    val data: List<com.easy.example.bean.MainBean<out AppCompatActivity>>
        get() {
            val list: MutableList<com.easy.example.bean.MainBean<out AppCompatActivity>> = mutableListOf()
            for (i in 0..9) {
                list.add(com.easy.example.bean.MainBean("标题 " + (1 + Math.random() * 10).toInt(), RecycleIndexActivity::class.java))
            }
            return list
        }
}