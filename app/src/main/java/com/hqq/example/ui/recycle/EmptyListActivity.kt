package com.hqq.example.ui.recycle

import com.hqq.core.ui.base.BaseListActivity
import com.hqq.example.adapter.MainAdapter
import java.util.*
import kotlin.collections.ArrayList

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui.recycle
 * @FileName :   EmptyListActivity
 * @Date : 2019/12/30 0030  下午 8:40
 * @Email :  qiqiang213@gmail.com
 * @Descrive :
 */
class EmptyListActivity : BaseListActivity<MainAdapter?>() {
    override fun initAdapter(): MainAdapter? {
        return MainAdapter()
    }

    override fun initData() {
        mBaseListModel!!.fillingData(ArrayList<String>() as ArrayList<Nothing>)
    }
}