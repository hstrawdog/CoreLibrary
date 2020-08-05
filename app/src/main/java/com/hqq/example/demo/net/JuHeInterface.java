package com.hqq.example.demo.net;

import com.hqq.example.demo.joke.Joke;
import com.hqq.example.demo.news.News;
import com.hqq.example.demo.weather.Weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.demo.net
 * @FileName :   JuHeInterface
 * @Date : 2020/8/4 0004  下午 3:06
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public interface JuHeInterface {

    @GET("simpleWeather/query")
    Call<NetResponseBody<Weather>> getWeather(@Query(value = "city", encoded = true) String username, @Query("key") String password);

    @GET("toutiao/index")
    Call<NetResponseBody<News>> getNews(@Query("type") String s, @Query("key") String b82c044b8029c9b134b91309a60ed10d);

    @GET("joke/content/list.php")
    Call<NetResponseBody<Joke>> getJoke(@Query("page") int pageCount, @Query("time") String time, @Query("key") String b82c044b8029c9b134b91309a60ed10d);
}
