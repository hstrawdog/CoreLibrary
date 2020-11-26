package com.hqq.example.ui.bar

import android.app.Activity
import android.content.Intent
import com.hqq.core.ui.base.BaseActivity
import com.hqq.example.R
import com.hqq.example.widget.BaseToolBarSearch

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.iblibrary.ui.activity
 * @FileName :   SearchBarActivity
 * @Date : 2018/12/14 0014
 * @Descrive : TODO
 * @Email :  qiqiang213@gmail.com
 */
class SearchBarActivity : BaseActivity() {

    override val layoutViewId: Int
        get() = R.layout.activity_search_bar

    override fun initConfig() {
        super.initConfig()

        iCreateRootView.iRootViewImpl.iCreateToolbar=BaseToolBarSearch()


    }

    override fun initView() {}

    companion object {
        fun open(context: Activity) {
            val starter = Intent(context, SearchBarActivity::class.java)
            context.startActivityForResult(starter, -1)
        }
    }
}