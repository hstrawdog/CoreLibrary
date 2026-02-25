package com.easy.example.ui.recycle

import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.easy.core.ui.list.BaseListActivity
import com.easy.core.utils.log.LogUtils
import com.easy.example.adapter.MainAdapter

/**
 * @Author : huangqiqiang
 * @Package : com.easy.iblibrary.ui.activity
 * @FileName :   LoadMoreActivity
 * @Date : 2018/11/23 0023
 * @Describe : TODO
 * @Email :  qiqiang213@gmail.com
 */
class LoadMoreActivity(override val adapter: MainAdapter = MainAdapter()) : BaseListActivity() {

    override fun initData() {
        onLoadMore()
    }


    override fun onLoadMore() {
        LogUtils.e { "----onLoadMore -----------------$pageCount" }
        Handler().postDelayed({
            if (pageCount == 3) {
                pageCount=1
                listModel.fillingData((ArrayList<Nothing>()))

            } else {
                listModel.fillingData(data as ArrayList<Nothing>)
            }

        }, 1000)
    }

    val data: List<com.easy.example.bean.MainBean<out AppCompatActivity>>
        get() {
            val list: MutableList<com.easy.example.bean.MainBean<out AppCompatActivity>> = mutableListOf()
            for (i in 0..19) {
                list.add(com.easy.example.bean.MainBean("标题 " + (1 + Math.random() * 10).toInt(),
                    RecycleIndexActivity::class.java))
            }
            return list
        }
}