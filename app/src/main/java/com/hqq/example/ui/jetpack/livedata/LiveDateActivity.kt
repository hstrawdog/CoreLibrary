package com.hqq.example.ui.jetpack.livedata

import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import com.hqq.core.ui.base.BaseActivity
import com.hqq.core.utils.log.LogUtils.e
import com.hqq.example.R

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.app.ui.databing
 * @FileName :   DataBindingActivity
 * @Date : 2019/10/22 0022  下午 1:37
 * @Email :  qiqiang213@gmail.com
 * @Descrive :
 * LiveData  change 需要在ui前台才会触发 并不能再后台执行
 */
class LiveDateActivity : BaseActivity() {
   lateinit var mTextView2: TextView


    override val mLayoutViewId: Int
        get() = R.layout.activity_data_binding
    override fun initView() {
        mTextView2 = findViewById(R.id.textView2)
        mTextView2.setOnClickListener(View.OnClickListener { view: View? -> onViewClicked(view) })
        LiveUser.getInstance(this).observe(this, Observer { user ->
            e("onChanged        $user")
            mTextView2.setText(user.name + user.level)
        })
    }

    fun onViewClicked(view: View?) {
        var user = LiveUser.getInstance(this).value
        if (null == user) {
            user = User.newInstance()
        }
        user!!.level = user.level + 2
        LiveUser.getInstance(this).value = user
    }

    companion object {
        fun open(context: Activity) {
            val starter = Intent(context, LiveDateActivity::class.java)
            context.startActivityForResult(starter, -1)
        }
    }
}