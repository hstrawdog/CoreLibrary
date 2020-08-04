package com.hqq.example.demo.weather;

import androidx.lifecycle.MutableLiveData;

import com.hqq.core.ui.vm.BaseViewModel;
import com.hqq.core.utils.log.LogUtils;
import com.hqq.example.demo.Repository;
import com.hqq.example.demo.net.NetCallback;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.demo.weather
 * @FileName :   WeatherViewModel
 * @Date : 2020/8/4 0004  下午 3:10
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public class WeatherViewModel extends BaseViewModel {

    public  MutableLiveData<Weather> mWeather = new MutableLiveData<>();

    @Override
    public void onCrete() {
        super.onCrete();
        LogUtils.e(" -------WeatherViewModel--------- onCrete ------");

        setShowLoading(true);
        Repository.getWeather("福州", new NetCallback<Weather>() {
            @Override
            public void onSuccess(Weather response) {
                setShowLoading(false);
                mWeather.setValue(response);
            }

            @Override
            public void onFail(int code, String message) {
                setShowToast(message);
                setShowLoading(false);

            }
        });

    }


}
