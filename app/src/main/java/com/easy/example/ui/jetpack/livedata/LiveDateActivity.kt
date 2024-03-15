// LintOptions: ignoreWarnings=true
 package com.easy.example.ui.jetpack.livedata

import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import com.easy.core.lifecycle.SingleLiveEvent
import com.easy.core.ui.base.BaseViewBindingActivity
import com.easy.core.utils.log.LogUtils.e
import com.easy.example.R
import com.easy.example.databinding.ActivityDataBindingBinding

/**
 * @Author : huangqiqiang
 * @Package : com.easy.core.app.ui.databing
 * @FileName :   DataBindingActivity
 * @Date : 2019/10/22 0022  下午 1:37
 * @Email :  qiqiang213@gmail.com
 * @Describe :
 * LiveData  change 需要在ui前台才会触发 并不能再后台执行
 */
class LiveDateActivity : BaseViewBindingActivity<ActivityDataBindingBinding>() {
    companion object {
        fun open(context: Activity) {
            val starter = Intent(context, LiveDateActivity::class.java)
            context.startActivityForResult(starter, -1)
        }
    }

    lateinit var mTextView2: TextView

    var live = SingleLiveEvent<String>()

    override fun initView() {
        mTextView2 = findViewById(R.id.textView2)

        LiveUser.getInstance(this)
            .observe(this, Observer { user ->
                e("onChanged button19        $user")
                mTextView2.setText(user.name + user.level)
            })


        binding.button19.setOnClickListener {
            onViewClicked(it)
        }
        binding.button191.setOnClickListener {
            com.easy.example.ui.jetpack.livedata.LiveUser.getInstance(this)
                .observe(this, Observer { user ->
                    e("onChanged  button191       $user")
                    binding.textView21.setText(user.name + user.level)
                })
        }

        live.observe(this) {
            binding.textView22.text = it;
        }
        binding.button22.setOnClickListener {

            live.value = System.currentTimeMillis()
                .toString()
        }
        binding.button24.setOnClickListener {
            live.observe(this) {
                binding.textView23.text = it;
            }
        }
        binding.button25.setOnClickListener {
            live.removeObservers(this)
        }
    }

    fun onViewClicked(view: View?) {
        var user = LiveUser.getInstance(this).value
        if (null == user) {
            user = User.newInstance()
        }
        user!!.level = user.level + 2
        LiveUser.getInstance(this).value = user
    }


}