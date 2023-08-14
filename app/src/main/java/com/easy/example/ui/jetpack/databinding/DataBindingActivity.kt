package com.easy.example.ui.jetpack.databinding

import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.easy.core.ui.base.BaseActivity
import com.easy.example.BR
import com.easy.example.R
import com.easy.example.ui.jetpack.livedata.User

/**
 * @Author : huangqiqiang
 * @Package : com.easy.example.ui.dagger
 * @FileName :   DaggerAActivity
 * @Date : 2020/7/15 0015  下午 4:18
 * @Email :  qiqiang213@gmail.com
 * @Descrive : dataBinding  双向绑定 需要实现BaseObservable 进行观察 以及通知回调
 * 注意 setVariable
 */
class DataBindingActivity : BaseActivity() {
    companion object {
        fun open(context: Activity) {
            val starter = Intent(context, DataBindingActivity::class.java)
            context.startActivityForResult(starter, -1)
        }
    }


    override val layoutViewId: Int = R.layout.activity_dagger_a

    override fun initView() {
        val viewDataBinding = DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, R.layout.item_data_binding, null, false)
        (findViewById<View>(R.id.ll_rootView) as LinearLayout).addView(viewDataBinding.root)
        val user = com.easy.example.ui.jetpack.livedata.User.newInstance()
        viewDataBinding.lifecycleOwner = this
        viewDataBinding.setVariable(BR.user, user)
        viewDataBinding.setVariable(BR.dataBinding, DataBanding())
        user.name = user.name + "M"
    }


}