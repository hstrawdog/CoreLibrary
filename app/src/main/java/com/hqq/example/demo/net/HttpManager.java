package com.hqq.example.demo.net;

import androidx.lifecycle.MutableLiveData;

import com.hqq.example.demo.joke.Joke;
import com.hqq.example.demo.net.ApiManager;
import com.hqq.example.demo.net.NetCallback;
import com.hqq.example.demo.news.News;
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
public class HttpManager {
    /**
     * 登录
     *
     * @param showLoading
     */
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

    /**
     * 天气查询
     *
     * @param city
     * @param netCallback
     */
    public static void getWeather(String city, NetCallback<Weather> netCallback) {
        try {
            city = URLEncoder.encode(city, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        ApiManager.getJuHeInterface("http://apis.juhe.cn/").getWeather(city, "dea6d7a061c30aba4e30e11ed51d852a").enqueue(netCallback);
    }

    /**
     * 热点新闻
     *
     * @param netCallback
     */
    public static void getNews(NetCallback<News> netCallback) {

        ApiManager.getJuHeInterface("http://v.juhe.cn/")
                .getNews("", "b82c044b8029c9b134b91309a60ed10d")
                .enqueue(netCallback);
    }

    /**
     * 笑话
     *
     * @param pageCount
     * @param netCallback
     */
    public static void getJoke(int pageCount, NetCallback<Joke> netCallback) {

        ApiManager.getJuHeInterface("http://v.juhe.cn/")
                .getJoke(pageCount, (System.currentTimeMillis() / 1000L) + "", "e2c17c621f374e49ddece7836ff321a9")
                .enqueue(netCallback);
    }
}
