package com.easy.example.ui.fragment

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import com.easy.core.ui.list.BaseListFragment
import com.easy.example.R
import com.easy.example.adapter.MainAdapter
import com.easy.example.bean.MainBean
import com.easy.example.ui.bar.ToolBarActivity
import com.easy.example.ui.view.page.IFragmentActivityBuilder
import kotlin.collections.ArrayList

/**
 * @Author : huangqiqiang
 * @Package : com.easy.iblibrary.ui.activity.fragment
 * @FileName :   MainFragment
 * @Date : 2018/11/23 0023  上午 9:41
 * @Descrive :
 * @Email :  qiqiang213@gmail.com
 */
class MainFragment(override val layoutViewId: Int = R.layout.fragment_i,
                   override val isLazyLoad: Boolean = true,//只有界面显示的时候才会加载
                   override val adapter: MainAdapter = MainAdapter()) : BaseListFragment() {

    companion object {
        @JvmStatic
        fun getIFragment(position: Int): Fragment {
            val fragment = MainFragment()
            val bundle = Bundle()
            bundle.putInt("postition", position)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun initData() {
        Handler().postDelayed({ initListData() }, 3 * 1000.toLong())

    }

    override fun onLoadMore() {
        super.onLoadMore()
        data
    }

    private val data: Unit
        get() {
            Handler().postDelayed({ initListData() }, 3 * 1000.toLong())
        }

    private fun initListData() {
        val list: MutableList<com.easy.example.bean.MainBean<*>> = ArrayList()
        list.add(com.easy.example.bean.MainBean("ItoolBar 控制 ", ToolBarActivity::class.java))
        list.add(com.easy.example.bean.MainBean("fragment 加载", IFragmentActivityBuilder::class.java))
        list.add(com.easy.example.bean.MainBean("ItoolBar 控制 ", ToolBarActivity::class.java))
        list.add(com.easy.example.bean.MainBean("fragment 加载", IFragmentActivityBuilder::class.java))
        list.add(com.easy.example.bean.MainBean("ItoolBar 控制 ", ToolBarActivity::class.java))
        list.add(com.easy.example.bean.MainBean("fragment 加载", IFragmentActivityBuilder::class.java))
        list.add(com.easy.example.bean.MainBean("ItoolBar 控制 ", ToolBarActivity::class.java))
        list.add(com.easy.example.bean.MainBean("fragment 加载", IFragmentActivityBuilder::class.java))
        list.add(com.easy.example.bean.MainBean("ItoolBar 控制 ", ToolBarActivity::class.java))
        list.add(com.easy.example.bean.MainBean("fragment 加载", IFragmentActivityBuilder::class.java))
        list.add(com.easy.example.bean.MainBean("ItoolBar 控制 ", ToolBarActivity::class.java))
        list.add(com.easy.example.bean.MainBean("fragment 加载", IFragmentActivityBuilder::class.java))
        list.add(com.easy.example.bean.MainBean("ItoolBar 控制 ", ToolBarActivity::class.java))
        list.add(com.easy.example.bean.MainBean("fragment 加载", IFragmentActivityBuilder::class.java))
        list.add(com.easy.example.bean.MainBean("ItoolBar 控制 ", ToolBarActivity::class.java))
        list.add(com.easy.example.bean.MainBean("fragment 加载", IFragmentActivityBuilder::class.java))
        list.add(com.easy.example.bean.MainBean("ItoolBar 控制 ", ToolBarActivity::class.java))
        list.add(com.easy.example.bean.MainBean("fragment 加载", IFragmentActivityBuilder::class.java))
        list.add(com.easy.example.bean.MainBean("ItoolBar 控制 ", ToolBarActivity::class.java))
        list.add(com.easy.example.bean.MainBean("fragment 加载", IFragmentActivityBuilder::class.java))
        list.add(com.easy.example.bean.MainBean("ItoolBar 控制 ", ToolBarActivity::class.java))
        list.add(com.easy.example.bean.MainBean("fragment 加载", IFragmentActivityBuilder::class.java))
        list.add(com.easy.example.bean.MainBean("ItoolBar 控制 ", ToolBarActivity::class.java))
        list.add(com.easy.example.bean.MainBean("fragment 加载", IFragmentActivityBuilder::class.java))
        list.add(com.easy.example.bean.MainBean("ItoolBar 控制 ", ToolBarActivity::class.java))
        list.add(com.easy.example.bean.MainBean("fragment 加载", IFragmentActivityBuilder::class.java))
        listModel.fillingData(list as ArrayList<*>)
    }



}