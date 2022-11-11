package com.hqq.example.ui.jetpack.di.hilt

import android.app.Activity
import android.content.Intent
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.hqq.core.ui.base.BaseDataBindingActivity
import com.hqq.example.R
import com.hqq.example.databinding.ActivityHiltBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui.jetpack.di.hilt
 * @FileName :   HiltActivity
 * @Date : 2020/8/14 0014  下午 3:40
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 * 包含Hilt内容
 * 1.默认注入
 * 2.搭配Jetpack 中 ViewModel 的使用
 * 3.@Bings 的注入
 * 4.@Provides 的注入
 * 5.通过限定符注入 类中的 接口
 * 6. 内置的限定符使用  ApplicationComponent 默认会自动注入
 *
 */
@AndroidEntryPoint
class HiltActivity(override val layoutId: Int = R.layout.activity_hilt)
    : BaseDataBindingActivity<ActivityHiltBinding>() {
    companion object {
        fun open(context: Activity) {
            val starter = Intent(context, HiltActivity::class.java)
            context.startActivityForResult(starter, -1)
        }
    }

    /**
     *  jetpack 套餐的用用法 用 by viewModels()
     *  注入 UserHilt 内置对象
     *  UserHilt 通过 provides 的注入内置 hair  对象
     */
    val viewModel: HiltViewModel by viewModels()

    @Inject
    lateinit var bindBean: BindBean

    /**
     *  AnalyticsAdapter  内置  AnalyticsService 接口
     *  可以用 provides 注入 AnalyticsService 的实现类 ---> BindBean
     */
    @AppHiltModel.AnalyticsService1
    @Inject
    lateinit var analytics: AnalyticsAdapter

    /**
     * 自定义限定符    Qualifier约束provides 的对象   通过 provides 去注入类中接口实现对象
     */
    @AppHiltModel.AnalyticsService2
    @Inject
    lateinit var analytics2: AnalyticsAdapter

    /**
     *  通过@Binds 注入  AnalyticsService 的实现类
     */
    @AppHiltBindModel.Binds1
    @Inject
    lateinit var analyticsService: AnalyticsService

    /**
     *  通过@Binds 注入  AnalyticsService 的实现类
     */
    @AppHiltBindModel.Binds2
    @Inject
    lateinit var analyticsService2: AnalyticsService

    override fun initView() {
        viewModel.getData()
        viewModel.data.observe(this, Observer {
            binding.textView21.setText(it.name + "心情: " + it.mood + " --- 头发  :" + it.hair.color)
        })

        binding.textView22.setText(analytics.service.analyticsMethods())
        binding.textView23.setText(analytics2.service.analyticsMethods())
        binding.textView24.setText(bindBean.name + "-- " + bindBean.context.javaClass.name +"---  "+ bindBean.server.analyticsMethods())
        binding.textView25.setText(analyticsService.analyticsMethods() + "-- ")
        binding.textView26.setText(analyticsService2.analyticsMethods() + "-- ")
    }


}
