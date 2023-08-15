package com.easy.example.demo.weather

import android.app.Activity
import android.content.Intent
import com.easy.core.ui.base.BaseVmActivity
import com.easy.core.utils.log.LogUtils.e
import com.easy.example.BR
import com.easy.example.R
import com.easy.example.databinding.ActivityWeatherBinding

/**
 * @Author : huangqiqiang
 * @Package : com.easy.example.demo.weather
 * @FileName :   WeatherActivity
 * @Date : 2020/8/4 0004  下午 3:10
 * @Email :  qiqiang213@gmail.com
 * @Descrive :
 */
class WeatherActivity : BaseVmActivity<WeatherViewModel, ActivityWeatherBinding>() {
    override fun getLayoutId(): Int {
        return R.layout.activity_weather
    }

    override fun bindingViewModelId(): Int {
        return BR.vm
    }

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