package com.hqq.example.ui.jetpack.databinding

import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.hqq.core.ui.base.BaseActivity
import com.hqq.example.BR
import com.hqq.example.R
import com.hqq.example.ui.jetpack.livedata.User

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui.dagger
 * @FileName :   DaggerAActivity
 * @Date : 2020/7/15 0015  下午 4:18
 * @Email :  qiqiang213@gmail.com
 * @Descrive : dataBinding  双向绑定 需要实现BaseObservable 进行观察 以及通知回调
 * 注意 setVariable
 */
class DataBindingActivity : BaseActivity() {
    override val layoutViewId: Int
        get() = R.layout.activity_dagger_a

    override fun initView() {
        val viewDataBinding = DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, R.layout.item_data_binding, null, false)
        (findViewById<View>(R.id.ll_rootView) as LinearLayout).addView(viewDataBinding.root)
        val user = User()
        viewDataBinding.lifecycleOwner = this
        viewDataBinding.setVariable(BR.user, user)
        viewDataBinding.setVariable(BR.dataBinding, DataBanding())
        user.name = "1111"

//        findViewById(R.id.button38).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                LogUtils.e("-----------" + user.getName());
//            }
//        });
    }

    companion object {
        fun open(context: Activity) {
            val starter = Intent(context, DataBindingActivity::class.java)
            context.startActivityForResult(starter, -1)
        }
    }
}