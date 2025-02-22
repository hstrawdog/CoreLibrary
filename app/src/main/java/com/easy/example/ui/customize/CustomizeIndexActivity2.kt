package com.easy.example.ui.customize

import android.app.Activity
import android.content.Intent
import android.view.View
import com.chad.library.adapter4.BaseQuickAdapter
import com.easy.core.ui.list.BaseListActivity
import com.easy.example.adapter.MainAdapter
import com.easy.example.bean.MainBean
import com.easy.example.ui.customize.matrix.BaseMatrixActivity
import com.easy.example.ui.customize.matrix.MatrixEventActivity

/**
 * @Author : huangqiqiang
 * @Package : com.easy.core.app.ui.customize
 * @FileName :   CustomizeIndexActivity
 * @Date : 2019/6/25 0025  下午 8:37
 * @Email :  qiqiang213@gmail.com
 * @Describe :
 */
class CustomizeIndexActivity2(override val adapter: MainAdapter=MainAdapter()) : BaseListActivity(),
    BaseQuickAdapter.OnItemClickListener<MainBean<*>> {

    override fun initData() {
        adapter.setOnItemClickListener(this)

        adapter.add(com.easy.example.bean.MainBean("圆形倒计时", com.easy.example.ui.customize.RoundCountdownActivity::class.java))
        adapter.add(com.easy.example.bean.MainBean("贝塞尔曲线鉴定绘制", com.easy.example.ui.customize.BezierActivity::class.java))
        adapter.add(com.easy.example.bean.MainBean("进度条/星星", com.easy.example.ui.customize.ProgressBarViewBuilderActivity::class.java))
        adapter.add(com.easy.example.bean.MainBean("高逼格的鱼", com.easy.example.ui.customize.FishActivity::class.java))
        adapter.add(com.easy.example.bean.MainBean("vie滑动以及回弹", com.easy.example.ui.customize.SwipeMenuLayoutActivity::class.java))
        adapter.add(com.easy.example.bean.MainBean("矩阵的简单使用", BaseMatrixActivity::class.java))
        adapter.add(com.easy.example.bean.MainBean("矩阵的手势", MatrixEventActivity::class.java))
        adapter.add(com.easy.example.bean.MainBean("对比效果", com.easy.example.ui.customize.ComparedActivity::class.java))
        adapter.add(com.easy.example.bean.MainBean("自定义圆形进度条", com.easy.example.ui.customize.SemicircularActivity::class.java))

    }




    companion object {
        fun open(context: Activity) {
            val starter = Intent(context, CustomizeIndexActivity2::class.java)
            context.startActivityForResult(starter, -1)
        }
    }

    override fun onClick(adapter: BaseQuickAdapter<MainBean<*>, *>, view: View, position: Int) {
        startActivity(Intent(this, (adapter as MainAdapter).getItem(position)?.getClassName()))
    }
}