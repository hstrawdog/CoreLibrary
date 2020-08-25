package com.hqq.example.ui.customize

import android.app.Activity
import android.content.Intent
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.hqq.core.ui.base.BaseListActivity
import com.hqq.example.adapter.MainAdapter
import com.hqq.example.bean.MainBean

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.app.ui.customize
 * @FileName :   CustomizeIndexActivity
 * @Date : 2019/6/25 0025  下午 8:37
 * @Email :  qiqiang213@gmail.com
 * @Descrive :
 */
class CustomizeIndexActivity(override val baseAdapter: MainAdapter=MainAdapter()) : BaseListActivity<MainAdapter?>(), OnItemClickListener {

    override fun initData() {
        baseAdapter.setOnItemClickListener(this)

        baseAdapter!!.addData(MainBean("圆形倒计时", RoundCountdownActivity::class.java))
        baseAdapter!!.addData(MainBean("贝塞尔曲线鉴定绘制", BezierActivity::class.java))
        baseAdapter!!.addData(MainBean("进度条/星星", ProgressBarViewBuilderActivity::class.java))
        baseAdapter!!.addData(MainBean("高逼格的鱼", FishActivity::class.java))
        baseAdapter!!.addData(MainBean("vie滑动以及回弹", SwipeMenuLayoutActivity::class.java))

    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        startActivity(Intent(this, (adapter as MainAdapter).getItem(position).getClassName()))
    }


    companion object {
        fun open(context: Activity) {
            val starter = Intent(context, CustomizeIndexActivity::class.java)
            context.startActivityForResult(starter, -1)
        }
    }
}