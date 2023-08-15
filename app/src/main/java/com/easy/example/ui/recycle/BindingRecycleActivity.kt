package com.easy.example.ui.recycle

import com.chad.library.adapter.base.BaseQuickAdapter
import com.easy.core.ui.base.BaseActivity
import com.easy.core.ui.list.BaseBindingListActivity
import com.easy.example.R
import com.easy.example.adapter.MainAdapter
import com.easy.example.bean.MainBean
import com.easy.example.databinding.ActivityBindingRecycleBinding
import com.easy.example.ui.file.FileIndexActivity

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


        adapter.addData(MainBean("文件相关", FileIndexActivity::class.java))
        adapter.addData(MainBean("文件相关", FileIndexActivity::class.java))
        adapter.addData(MainBean("文件相关", FileIndexActivity::class.java))
        adapter.addData(MainBean("文件相关", FileIndexActivity::class.java))
        adapter.addData(MainBean("文件相关", FileIndexActivity::class.java))
        adapter.addData(MainBean("文件相关", FileIndexActivity::class.java))
        adapter.addData(MainBean("文件相关", FileIndexActivity::class.java))
        adapter.addData(MainBean("文件相关", FileIndexActivity::class.java))
        adapter.addData(MainBean("文件相关", FileIndexActivity::class.java))
        adapter.addData(MainBean("文件相关", FileIndexActivity::class.java))
        adapter.addData(MainBean("文件相关", FileIndexActivity::class.java))
        adapter.addData(MainBean("文件相关", FileIndexActivity::class.java))
        adapter.addData(MainBean("文件相关", FileIndexActivity::class.java))
        adapter.addData(MainBean("文件相关", FileIndexActivity::class.java))
        adapter.addData(MainBean("文件相关", FileIndexActivity::class.java))

    }
}