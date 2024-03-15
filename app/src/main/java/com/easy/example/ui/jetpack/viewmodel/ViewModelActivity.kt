package com.easy.example.ui.jetpack.viewmodel

import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.easy.core.ui.base.BaseActivity
import com.easy.core.utils.log.LogUtils.e
import com.easy.example.R
import com.easy.example.ui.jetpack.livedata.User

/**
 * @Author : huangqiqiang
 * @Package : com.easy.example.ui.jetpack.viewmodel
 * @FileName :   ViewModelActivity
 * @Date : 2020/7/21 0021  上午 9:52
 * @Email :  qiqiang213@gmail.com
 * @Describe :
 * 正常 ViewModel 搭配LiveData 使用
 */
class ViewModelActivity : BaseActivity() {


    override fun getLayoutViewId(): Int {
        return R.layout.activity_view_model
    }

    override fun initView() {
        val userViewModel = ViewModelProvider(this).get(com.easy.example.ui.jetpack.viewmodel.UserViewModel::class.java)
        userViewModel.user.observe(this, Observer { user ->
            e(user.toString())
            (findViewById<View>(R.id.textView15) as TextView).text = user.name + user.level
        })
        userViewModel.setUser(com.easy.example.ui.jetpack.livedata.User.newInstance())
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