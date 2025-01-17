package com.easy.example.ui.recycle

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter4.BaseQuickAdapter
import com.chad.library.adapter4.viewholder.QuickViewHolder
import com.easy.core.ui.base.BaseActivity
import com.easy.core.ui.list2.ListModel
import com.easy.core.ui.list2.IListModelView
import com.easy.example.R
import com.easy.example.bean.ClassA
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @Author : huangqiqiang
 * @Package : com.easy.iblibrary.ui.activity
 * @FileName :   ListActivity
 * @Date : 2018/12/14 0014
 * @Describe : TODO
 * @Email :  qiqiang213@gmail.com
 */
class ListV2ActivityView : BaseActivity() {

    class ListAdapter : BaseQuickAdapter<ClassA, QuickViewHolder>() {
        override fun onBindViewHolder(holder: QuickViewHolder, position: Int, item: ClassA?) {

        }

        override fun onCreateViewHolder(context: Context, parent: ViewGroup, viewType: Int): QuickViewHolder {

            return QuickViewHolder(R.layout.item_main, parent)
        }

    }


    val listModel: ListModel<ClassA> by lazy {
        ListModel<ClassA>(rootViewImpl, object : IListModelView {
            override fun getContext(): Context {
                return this@ListV2ActivityView
            }

            override fun onRefreshBegin() {
                listModel.pageCount = 1
                getData()
            }

            override fun onLoadMore() {
                listModel.pageCount++
                getData()
            }

        }).apply {
            isLoadMore = true
            adapter = ListAdapter()
        }

    }


    fun getData() {
        CoroutineScope(Dispatchers.IO).launch {

            delay(2000)

            withContext(Dispatchers.Main) {
                var list = ArrayList<ClassA>()

                for (i in 0 until 20) {
                    list.add(ClassA())
                }
                listModel.fillingData(list)

            }

        }

    }

    override fun getLayoutView(parent: ViewGroup): View? {
        return listModel.recycleView
    }


    override fun getLayoutViewId(): Int {
        return 0
    }

    override fun initView() {
        listModel.build()
        getData()

    }

}