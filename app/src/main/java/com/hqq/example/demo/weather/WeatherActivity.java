package com.hqq.example.demo.weather;

import android.app.Activity;
import android.content.Intent;

import com.hqq.core.ui.vm.BaseVmActivity;
import com.hqq.core.utils.log.LogUtils;
import com.hqq.example.BR;
import com.hqq.example.R;
import com.hqq.example.databinding.ActivityWeatherBinding;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.demo.weather
 * @FileName :   WeatherActivity
 * @Date : 2020/8/4 0004  下午 3:10
 * @Email :  qiqiang213@gmail.com
 * @Descrive :
 */
public class WeatherActivity extends BaseVmActivity<ActivityWeatherBinding, WeatherViewModel> {

    public static void open(Activity context) {
        Intent starter = new Intent(context, WeatherActivity.class);
        context.startActivityForResult(starter, -1);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_weather;
    }


    @Override
    public int getBindingViewModelId() {
        return BR.vm;
    }

    @Override
    public void initViews() {
        LogUtils.e(" -------WeatherActivity--------- initViews ------");
    }

}