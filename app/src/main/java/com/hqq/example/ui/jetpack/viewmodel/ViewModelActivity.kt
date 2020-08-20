package com.hqq.example.ui.jetpack.viewmodel

import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.hqq.core.ui.base.BaseActivity
import com.hqq.core.utils.log.LogUtils.e
import com.hqq.example.R
import com.hqq.example.ui.jetpack.livedata.User

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui.jetpack.viewmodel
 * @FileName :   ViewModelActivity
 * @Date : 2020/7/21 0021  上午 9:52
 * @Email :  qiqiang213@gmail.com
 * @Descrive :
 * 正常 ViewModel 搭配LiveData 使用
 */
class ViewModelActivity : BaseActivity() {

    override val layoutViewId: Int
        get() = R.layout.activity_view_model

    override fun initView() {
        val userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        userViewModel.user.observe(this, Observer { user ->
            e(user.toString())
            (findViewById<View>(R.id.textView15) as TextView).text = user.name + user.level
        })
        userViewModel.setUser(User.newInstance())
        findViewById<View>(R.id.button37).setOnClickListener {
            val user = userViewModel.user.value
            user!!.level = (Math.random() * 77).toInt()
            userViewModel.setUser(user)
        }
    }

    companion object {
        fun open(context: Activity) {
            val starter = Intent(context, ViewModelActivity::class.java)
            context.startActivityForResult(starter, -1)
        }
    }
}