package com.easy.example.ui.file.image

import com.easy.core.ui.list.BaseListActivity
import com.easy.example.adapter.MainAdapter
import com.easy.example.bean.MainBean

/**
 * @Author : huangqiqiang
 * @Package : com.easy.example.ui.file.image
 * @Date : 17:29
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class ImageIndexActivity2 : BaseListActivity() {
    override val adapter: MainAdapter = MainAdapter()

    override fun initData() {

        adapter.add(com.easy.example.bean.MainBean("正方形图片Style", com.easy.example.ui.file.image.ImageViewSquareActivity::class.java))
        adapter.add(com.easy.example.bean.MainBean("横向矩形图片Style", com.easy.example.ui.file.image.ImageViewRectangleActivity::class.java))
        adapter.add(com.easy.example.bean.MainBean("纵向矩形图片Style", com.easy.example.ui.file.image.ImageViewRectangle2Activity::class.java))
        adapter.add(com.easy.example.bean.MainBean("Coil图片加载", ImageLoadCoilActivity::class.java))

    }
}