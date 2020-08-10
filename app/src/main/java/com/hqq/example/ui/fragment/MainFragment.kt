package com.hqq.example.ui.fragment

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import com.hqq.core.ui.base.BaseListFragment
import com.hqq.example.R
import com.hqq.example.adapter.MainAdapter
import com.hqq.example.bean.MainBean
import com.hqq.example.ui.bar.ToolBarActivity
import com.hqq.example.ui.view.page.IFragmentActivityBuilder
import java.util.*
import kotlin.collections.ArrayList

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.iblibrary.ui.activity.fragment
 * @FileName :   MainFragment
 * @Date : 2018/11/23 0023  上午 9:41
 * @Descrive :
 * @Email :  qiqiang213@gmail.com
 */
class MainFragment : BaseListFragment<MainAdapter?>() {
    override fun getLayoutViewId(): Int {
        return R.layout.fragment_i
    }

    /**
     * 只有界面显示的时候才会加载
     *
     * @return
     */
    override val isLazyLoad: Boolean
        get() = true



    override fun initAdapter(): MainAdapter? {
        return MainAdapter()
    }

    override fun initData() {
        initListData()
    }

    override fun onLoadMore() {
        super.onLoadMore()
        data
    }

    private val data: Unit
        private get() {
            Handler().postDelayed({ initListData() }, 3 * 1000.toLong())
        }

    private fun initListData() {

        val list: MutableList<MainBean<*>> = ArrayList()
        list.add(MainBean("ItoolBar 控制 ", ToolBarActivity::class.java))
        list.add(MainBean("fragment 加载", IFragmentActivityBuilder::class.java))
        list.add(MainBean("ItoolBar 控制 ", ToolBarActivity::class.java))
        list.add(MainBean("fragment 加载", IFragmentActivityBuilder::class.java))
        list.add(MainBean("ItoolBar 控制 ", ToolBarActivity::class.java))
        list.add(MainBean("fragment 加载", IFragmentActivityBuilder::class.java))
        list.add(MainBean("ItoolBar 控制 ", ToolBarActivity::class.java))
        list.add(MainBean("fragment 加载", IFragmentActivityBuilder::class.java))
        list.add(MainBean("ItoolBar 控制 ", ToolBarActivity::class.java))
        list.add(MainBean("fragment 加载", IFragmentActivityBuilder::class.java))
        list.add(MainBean("ItoolBar 控制 ", ToolBarActivity::class.java))
        list.add(MainBean("fragment 加载", IFragmentActivityBuilder::class.java))
        list.add(MainBean("ItoolBar 控制 ", ToolBarActivity::class.java))
        list.add(MainBean("fragment 加载", IFragmentActivityBuilder::class.java))
        list.add(MainBean("ItoolBar 控制 ", ToolBarActivity::class.java))
        list.add(MainBean("fragment 加载", IFragmentActivityBuilder::class.java))
        list.add(MainBean("ItoolBar 控制 ", ToolBarActivity::class.java))
        list.add(MainBean("fragment 加载", IFragmentActivityBuilder::class.java))
        list.add(MainBean("ItoolBar 控制 ", ToolBarActivity::class.java))
        list.add(MainBean("fragment 加载", IFragmentActivityBuilder::class.java))
        list.add(MainBean("ItoolBar 控制 ", ToolBarActivity::class.java))
        list.add(MainBean("fragment 加载", IFragmentActivityBuilder::class.java))
        list.add(MainBean("ItoolBar 控制 ", ToolBarActivity::class.java))
        list.add(MainBean("fragment 加载", IFragmentActivityBuilder::class.java))
        list.add(MainBean("ItoolBar 控制 ", ToolBarActivity::class.java))
        list.add(MainBean("fragment 加载", IFragmentActivityBuilder::class.java))
        mBaseListModel!!.fillingData(list as ArrayList<Nothing>)
    }

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
}