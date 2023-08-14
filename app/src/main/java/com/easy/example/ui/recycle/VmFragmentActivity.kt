package com.easy.example.ui.recycle

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import com.easy.core.databinding.ActivityRecycleViewBinding
import com.easy.core.ui.base.BaseViewBindingActivity
import com.easy.core.ui.list.BaseListViewModel
import com.easy.core.ui.list.BaseVmListFragment
import com.easy.core.utils.FragmentUtils
import com.easy.core.utils.log.LogUtils
import com.easy.example.adapter.MainAdapter
import com.easy.example.bean.MainBean
import com.easy.example.databinding.ActivityVmFragmentBinding

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

    class FragmentA : BaseVmListFragment<ListModel, ActivityRecycleViewBinding>() {
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
        override val adapter: MainAdapter = MainAdapter()
        override fun initData() {

            adapter.loadMoreModule.isEnableLoadMore = false;
            adapter.loadMoreModule.setOnLoadMoreListener(this)

            var tv = TextView(activity)
            tv.setText("1111111111111111111111111111111111111111111")

            adapter.addHeaderView(tv)


        }


    }


    class ListModel : BaseListViewModel() {
        override fun initData(extras: Bundle?) {
            setData(addData())
        }

        override fun onLoadMore() {
            super.onLoadMore()
            setData(addData())
        }

        private fun addData(): List<*> {
            LogUtils.e("----------------loadingMore")
            var map = ArrayList<com.easy.example.bean.MainBean<*>>()
            for (i in 0..9) {
                map.add(com.easy.example.bean.MainBean("i " + i, VmFragmentActivity::class.java))
            }

            if (pageCount > 1) {
                map.removeAt(6)
            }

            return ArrayList<com.easy.example.bean.MainBean<*>>()
        }


    }

}