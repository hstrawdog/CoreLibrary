package com.easy.example.ui.recycle

import com.easy.core.ui.list.BaseListActivity
import com.easy.example.adapter.MainAdapter

/**
 * @Author : huangqiqiang
 * @Package : com.easy.example.ui.recycle
 * @FileName :   EmptyListActivity
 * @Date : 2019/12/30 0030  下午 8:40
 * @Email :  qiqiang213@gmail.com
 * @Describe :
 */
class EmptyListActivity(override val adapter: MainAdapter = MainAdapter()) : BaseListActivity() {

    override fun initData() {
        listModel.fillingData(ArrayList<String>() as ArrayList<Nothing>)
    }
}