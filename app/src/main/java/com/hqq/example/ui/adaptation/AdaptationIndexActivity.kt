package com.hqq.example.ui.adaptation

import com.hqq.core.ui.base.BaseListActivity
import com.hqq.example.adapter.MainAdapter
import com.hqq.example.bean.MainBean
import com.hqq.example.ui.screen.DimenActivity
import com.hqq.example.ui.screen.TextViewBuilderSizeActivity

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui
 * @FileName :   AdaptationIndexActivity
 * @Date : 2020/1/2 0002  上午 9:35
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
class AdaptationIndexActivity(override val baseAdapter: MainAdapter= MainAdapter()) : BaseListActivity<MainAdapter>() {
    override fun initData() {
        baseAdapter!!.addData(MainBean("文字适配测试", TextViewBuilderSizeActivity::class.java))
        baseAdapter!!.addData(MainBean("1像素大小测试", DimenActivity::class.java))
    }

}