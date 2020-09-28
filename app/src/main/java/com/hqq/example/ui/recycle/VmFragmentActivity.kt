package com.hqq.example.ui.recycle

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import com.hqq.core.databinding.ActivityRecycleViewBinding
import com.hqq.core.ui.base.BaseViewBindingActivity
import com.hqq.core.ui.list.BaseListViewModel
import com.hqq.core.ui.list.BaseVmListFragment
import com.hqq.core.utils.FragmentUtils
import com.hqq.core.utils.log.LogUtils
import com.hqq.example.adapter.MainAdapter
import com.hqq.example.bean.MainBean
import com.hqq.example.databinding.ActivityVmFragmentBinding

class
VmFragmentActivity : BaseViewBindingActivity<ActivityVmFragmentBinding>() {
    companion object {
        fun open(context: Activity) {
            context.startActivityForResult(Intent(context, VmFragmentActivity::class.java), -1)
        }
    }


    override fun initView() {
        FragmentUtils(supportFragmentManager).addOrShowFragment(FragmentA.newInstance(), binding.flLayout.id)
    }

    class FragmentA : BaseVmListFragment<ActivityRecycleViewBinding, ListModel>() {
        companion object {
            fun newInstance(): FragmentA {
                val args = Bundle()
                val fragment = FragmentA()
                fragment.arguments = args
                return fragment
            }
        }

        override val bindingViewModelId: Int
            get() = -1
        override val baseAdapter: MainAdapter = MainAdapter()
        override fun initData() {

            baseAdapter.loadMoreModule.isEnableLoadMore = false;
            baseAdapter.loadMoreModule.setOnLoadMoreListener(this)

            var tv = TextView(activity)
            tv.setText("1111111111111111111111111111111111111111111")

            baseAdapter.addHeaderView(tv)


        }


    }


    class ListModel : BaseListViewModel() {

        override fun initData() {

            setData(addData())
        }

        override fun onLoadMore() {
            super.onLoadMore()
            setData(addData())
        }

        private fun addData(): List<*> {
            LogUtils.e("----------------loadingMore")
            var map = ArrayList<MainBean<*>>()
            for (i in 0..9) {
                map.add(MainBean("i " + i, VmFragmentActivity::class.java))
            }

            if (pageCount > 1) {
                map.removeAt(6)
            }

            return ArrayList<MainBean<*>>()
        }


    }

}