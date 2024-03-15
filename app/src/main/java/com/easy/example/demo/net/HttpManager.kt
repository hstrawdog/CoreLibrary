package com.easy.example.demo.net

import android.os.Looper
import androidx.lifecycle.MutableLiveData
import com.easy.example.demo.joke.Joke
import com.easy.example.demo.news.News
import com.easy.example.demo.weather.Weather
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.UnsupportedEncodingException
import java.net.URLEncoder

/**
 * @Author : huangqiqiang
 * @Package : com.easy.example.demo
 * @FileName :   Repository
 * @Date : 2020/7/28 0028  下午 4:21
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
object HttpManager {
    /**
     * 登录
     *
     * @param showLoading
     */
    fun login(showLoading: MutableLiveData<Boolean>) {
//        showLoading.value = true
        ApiManager.wangAndroidInterface?.getUserCenter("13696891101", "zz789789")?.enqueue(object : Callback<String?> {
            override fun onResponse(call: Call<String?>, response: Response<String?>) {
//                showLoading.value = false
            }

            override fun onFailure(call: Call<String?>, t: Throwable) {
//                showLoading.value = false
            }
        })
    }

    /**
     * 天气查询
     *
     * @param city
     * @param netCallback
     */
    fun getWeather(city: String, netCallback: NetCallback<com.easy.example.demo.weather.Weather>) {
        var city = city
        try {
            city = URLEncoder.encode(city, "utf-8")
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }
        ApiManager.getJuHeInterface("http://apis.juhe.cn/")
                .getWeather(city, "dea6d7a061c30aba4e30e11ed51d852a").enqueue(netCallback)
    }


    suspend fun  getWeather2(city: String): NetResponseBody<com.easy.example.demo.weather.Weather> {
        var city = city
        try {
            city = URLEncoder.encode(city, "utf-8")
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }
       return  ApiManager.getJuHeInterface("http://apis.juhe.cn/")
                .getWeather2(city, "dea6d7a061c30aba4e30e11ed51d852a")
    }

    /**
     * 热点新闻
     *
     * @param netCallback
     */
    fun getNews(netCallback: NetCallback<com.easy.example.demo.news.News>) {
        ApiManager.getJuHeInterface("http://v.juhe.cn/")
                .getNews("", "b82c044b8029c9b134b91309a60ed10d")
                .enqueue(netCallback!!)
    }

    /**
     * 笑话
     *
     * @param pageCount
     * @param netCallback
     */
    fun getJoke(pageCount: Int, netCallback: NetCallback<com.easy.example.demo.joke.Joke>) {
        ApiManager.getJuHeInterface("http://v.juhe.cn/")
                .getJoke(pageCount, (System.currentTimeMillis() / 1000L).toString() + "", "e2c17c621f374e49ddece7836ff321a9")
                .enqueue(netCallback)
    }
}