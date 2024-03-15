package com.easy.example.ui.customize

import android.app.Activity
import android.content.Intent
import com.easy.core.ui.base.BaseActivity
import com.easy.example.R

/**
 * @Author : huangqiqiang
 * @Package : com.easy.example.ui.customize
 * @Date : 上午 11:15
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class DiskViewActivity : BaseActivity() {
    companion object {
        fun open(context: Activity) {
            context.startActivityForResult(Intent(context, DiskViewActivity::class.java), -1)
        }
    }

    override fun getLayoutViewId(): Int {
        return  R.layout.activity_disk_view
    }

    override fun initView() {}
}