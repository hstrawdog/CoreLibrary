package com.hqq.example.ui.file.image

import com.hqq.core.ui.list.BaseListActivity
import com.hqq.example.adapter.MainAdapter
import com.hqq.example.bean.MainBean

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui.file.image
 * @Date : 17:29
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class ImageIndexActivity : BaseListActivity() {
    override val adapter: MainAdapter= MainAdapter()

    override fun initData() {

        adapter.addData(MainBean("正方形图片Style", ImageViewSquareActivity::class.java))
        adapter.addData(MainBean("横向矩形图片Style", ImageViewRectangleActivity::class.java))
        adapter.addData(MainBean("纵向矩形图片Style", ImageViewRectangle2Activity::class.java))

    }
}