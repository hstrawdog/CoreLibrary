package com.hqq.example.demo;

import androidx.lifecycle.MutableLiveData;

import com.hqq.example.demo.net.ApiManager;

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
        ApiManager.getWangAndroidInterface().getUserCenter("13696891101","zz789789").enqueue(new Callback<String>() {
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

}
