package com.hqq.example.ui.list

import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.hqq.core.ui.base.BaseListActivity
import com.hqq.example.adapter.MainAdapter
import com.hqq.example.bean.MainBean
import com.hqq.example.ui.recycle.RecycleIndexActivity
import java.util.*

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.iblibrary.ui.activity
 * @FileName :   LoadMoreActivity
 * @Date : 2018/11/23 0023
 * @Descrive : TODO
 * @Email :  qiqiang213@gmail.com
 */
class LoadMoreActivity(override val baseAdapter: MainAdapter? = MainAdapter()) : BaseListActivity<MainAdapter?>() {

    override fun initData() {
        mBaseListModel?.fillingData(data as List<Nothing>)
    }

    override fun onLoadMore() {
        Handler().postDelayed({
            if (mBaseListModel != null) {
                mBaseListModel!!.fillingData(data as List<Nothing>)
                baseAdapter!!.loadMoreModule.loadMoreComplete()
            }
        }, 2000)
    }

    val data: List<MainBean<out AppCompatActivity>>
        get() {
            val list: MutableList<MainBean<out AppCompatActivity>> = mutableListOf()
            for (i in 0..9) {
                list.add(MainBean("标题 " + (1 + Math.random() * 10).toInt(), RecycleIndexActivity::class.java))
            }
            return list
        }
}