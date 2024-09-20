package com.easy.example.demo.net

import com.easy.example.demo.weather.Weather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @Author : huangqiqiang
 * @Package : com.easy.example.demo.net
 * @FileName :   JuHeInterface
 * @Date : 2020/8/4 0004  下午 3:06
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
interface JuHeInterface {
    @GET("simpleWeather/query")
    fun getWeather(@Query(value = "city", encoded = true) username: String?, @Query("key") password: String?): Call<NetResponseBody<Weather>>

    @GET("simpleWeather/query")
    suspend fun getWeather2(@Query(value = "city", encoded = true) username: String?, @Query("key") password: String?): NetResponseBody<Weather>

    @GET("toutiao/index")
    fun getNews(@Query("type") s: String?, @Query("key") key: String): Call<NetResponseBody<com.easy.example.demo.news.News>>

    @GET("joke/content/list.php")
    fun getJoke(@Query("page") pageCount: Int, @Query("time") time: String?, @Query("key") key: String): Call<NetResponseBody<com.easy.example.demo.joke.Joke>>
}