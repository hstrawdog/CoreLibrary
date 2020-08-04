package com.hqq.example.demo;

import androidx.lifecycle.MutableLiveData;

import com.hqq.example.demo.net.ApiManager;
import com.hqq.example.demo.net.NetCallback;
import com.hqq.example.demo.weather.Weather;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.demo
 * @FileName :   Repository
 * @Date : 2020/7/28 0028  下午 4:21
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public class Repository {

    public static void login(MutableLiveData<Boolean> showLoading) {
        showLoading.setValue(true);
        ApiManager.getWangAndroidInterface().getUserCenter("13696891101", "zz789789").enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                showLoading.setValue(false);

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                showLoading.setValue(false);
            }
        });
    }


    public static void getWeather(String city, NetCallback<Weather> netCallback) {
        try {
            city = URLEncoder.encode(city, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        ApiManager.getJuHeInterface("http://apis.juhe.cn/").getWeather(city, "dea6d7a061c30aba4e30e11ed51d852a").enqueue(new Callback<Weather>() {

            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                netCallback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                netCallback.onFail(-999, "网络请求失败");
            }

        });
    }
}
