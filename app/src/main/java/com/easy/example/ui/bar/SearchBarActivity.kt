package com.easy.example.ui.bar

import android.app.Activity
import android.content.Intent
import com.easy.core.ui.base.BaseActivity
import com.easy.example.R
import com.easy.example.widget.BaseToolBarSearch

/**
 * @Author : huangqiqiang
 * @Package : com.easy.iblibrary.ui.activity
 * @FileName :   SearchBarActivity
 * @Date : 2018/12/14 0014
 * @Descrive : TODO
 * @Email :  qiqiang213@gmail.com
 */
class SearchBarActivity : BaseActivity() {


    override fun initConfig() {
        super.initConfig()

        rootViewImpl.iToolBarBuilder.iCreateToolbar = BaseToolBarSearch()


    }

    override fun getLayoutViewId(): Int {
        return  R.layout.activity_search_bar
    }

    override fun initView() {}

    companion object {
        fun open(context: Activity) {
            val starter = Intent(context, SearchBarActivity::class.java)
            context.startActivityForResult(starter, -1)
        }
    }
}