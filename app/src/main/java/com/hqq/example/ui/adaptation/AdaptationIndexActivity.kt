package com.hqq.example.ui.adaptation

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
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
class AdaptationIndexActivity : BaseListActivity<MainAdapter?>() {
    override fun initAdapter(): MainAdapter? {
        return MainAdapter()
    }

    override fun initData() {
        adapter!!.addData(MainBean("文字适配测试", TextViewBuilderSizeActivity::class.java))
        adapter!!.addData(MainBean("1像素大小测试", DimenActivity::class.java))
    }

}