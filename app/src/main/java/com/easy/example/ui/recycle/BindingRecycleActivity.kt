package com.easy.example.ui.recycle


import com.easy.core.ui.list.BaseBindingListActivity
import com.easy.example.adapter.MainAdapter
import com.easy.example.bean.MainBean
import com.easy.example.databinding.ActivityBindingRecycleBinding
import com.easy.example.ui.file.FileIndexActivity2

/**
 * @Author : huangqiqiang
 * @Package : com.easy.example.ui.recycle
 * @Date : 23:13
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class BindingRecycleActivity : BaseBindingListActivity<ActivityBindingRecycleBinding>() {

    override val adapter: MainAdapter = MainAdapter()


    override fun initData() {


        adapter.add(MainBean("文件相关", FileIndexActivity2::class.java))
        adapter.add(MainBean("文件相关", FileIndexActivity2::class.java))
        adapter.add(MainBean("文件相关", FileIndexActivity2::class.java))
        adapter.add(MainBean("文件相关", FileIndexActivity2::class.java))
        adapter.add(MainBean("文件相关", FileIndexActivity2::class.java))
        adapter.add(MainBean("文件相关", FileIndexActivity2::class.java))
        adapter.add(MainBean("文件相关", FileIndexActivity2::class.java))
        adapter.add(MainBean("文件相关", FileIndexActivity2::class.java))
        adapter.add(MainBean("文件相关", FileIndexActivity2::class.java))
        adapter.add(MainBean("文件相关", FileIndexActivity2::class.java))
        adapter.add(MainBean("文件相关", FileIndexActivity2::class.java))
        adapter.add(MainBean("文件相关", FileIndexActivity2::class.java))
        adapter.add(MainBean("文件相关", FileIndexActivity2::class.java))
        adapter.add(MainBean("文件相关", FileIndexActivity2::class.java))
        adapter.add(MainBean("文件相关", FileIndexActivity2::class.java))

    }
}