package com.hqq.example.ui.jetpack.hilt

import android.app.Activity
import android.content.Intent
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.hqq.core.ui.binding.BaseBindingActivity
import com.hqq.example.R
import com.hqq.example.databinding.ActivityHiltBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HiltActivity(override val layoutId: Int = R.layout.activity_hilt)
    : BaseBindingActivity<ActivityHiltBinding>() {
    companion object {
        fun open(context: Activity) {
            val starter = Intent(context, HiltActivity::class.java)
            context.startActivityForResult(starter, -1)
        }
    }

    val viewModel: HiltViewModel by viewModels()

    @Inject
    lateinit var mUserHilt: UserHilt

    @Inject
    lateinit var analytics: AnalyticsAdapter

    @Inject
    lateinit var any: Any

    @Inject
    lateinit var analytics2: AnalyticsAdapter
    override fun initView() {
        viewModel.getData()
        viewModel.mData.observe(this, Observer {
            mBinding?.textView21?.setText(it.name + "心情: " + it.mood
                    + " --- 头发  :" + it.hair.color)
        })

        mBinding?.textView22?.setText(analytics.service.analyticsMethods())
        mBinding?.textView23?.setText(analytics2.service.analyticsMethods())
        mBinding?.textView24?.setText((any as AnalyticsAdapter).service.analyticsMethods())
//        mBinding?.textView21?.setText(mUserHilt.name + "心情: " + mUserHilt.mood
//                + " --- 头发  :" + mUserHilt.hair.color)
    }


}
