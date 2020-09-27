package com.hqq.example.demo.weather

import android.app.Activity
import android.content.Intent
import com.hqq.core.ui.base.BaseVmActivity
import com.hqq.core.utils.log.LogUtils.e
import com.hqq.example.BR
import com.hqq.example.R
import com.hqq.example.databinding.ActivityWeatherBinding

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.demo.weather
 * @FileName :   WeatherActivity
 * @Date : 2020/8/4 0004  下午 3:10
 * @Email :  qiqiang213@gmail.com
 * @Descrive :
 */
class WeatherActivity : BaseVmActivity<ActivityWeatherBinding, WeatherViewModel>() {
    override val layoutId: Int
        get() = R.layout.activity_weather

    override val bindingViewModelId: Int
        get() = BR.vm

    override fun initViews() {
        e(" -------WeatherActivity--------- initViews ------")
    }

    companion object {
        fun open(context: Activity) {
            val starter = Intent(context, WeatherActivity::class.java)
            context.startActivityForResult(starter, -1)
        }
    }
}