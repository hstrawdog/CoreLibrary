package com.hqq.example.demo.net

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.demo.net
 * @FileName :   WangAndroidInterface
 * @Date : 2020/7/28 0028  下午 4:37
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
interface WangAndroidInterface {
    @POST("user/login")
    @FormUrlEncoded
    fun getUserCenter(@Field("username") username: String?, @Field("password") password: String?): Call<String?>
}