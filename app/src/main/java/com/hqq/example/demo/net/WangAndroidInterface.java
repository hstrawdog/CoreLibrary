package com.hqq.example.demo.net;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.demo.net
 * @FileName :   WangAndroidInterface
 * @Date : 2020/7/28 0028  下午 4:37
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public interface WangAndroidInterface {


    @POST("user/login")
    @FormUrlEncoded
    Call<String> getUserCenter(@Field("username") String username, @Field("password") String password);


}
