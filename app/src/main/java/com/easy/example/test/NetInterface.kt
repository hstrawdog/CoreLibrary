package com.easy.example.test

import com.easy.core.net.net.RetrofitService
import com.google.gson.JsonElement
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.QueryMap

/**
 * @Author : huangqiqiang
 * @Package : com.easy.example.test
 * @Date  : 09:47
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */

object ApiManager {
    //
    var netInterface:NetInterface = RetrofitService.createService("https://r.ccwork.com/", NetInterface::class.java)
    var netInterface2:NetInterface = RetrofitService.createService("http://117.73.18.229:81/", NetInterface::class.java)
    var netInterface3:NetInterface = RetrofitService.createService("https://gray.myhtime.com:8088/", NetInterface::class.java)
    var netInterface4:NetInterface = RetrofitService.createService("http://117.73.18.229:6382/", NetInterface::class.java)


    
    //改由后台下发2021-8-25
//    var removeInterface: NetInterface =
//        RetrofitImageService.createService("http://220.250.40.61:8987/", NetInterface::class.java)
    //外网地址
//    var netInterface: NetInterface =
//            RetrofitService.createService("http://218.5.2.10:14699/", NetInterface::class.java)

}

interface NetInterface {

    @POST("api/router/v2.0/OrgRouter")
    suspend fun OrgRouter(@Body request: Map<String, @JvmSuppressWildcards Any>):EncodeResponseBody

    @FormUrlEncoded
    @POST("htime/app/login/doLogin")
    suspend fun doLogin(@FieldMap request: Map<String, @JvmSuppressWildcards Any>):EncodeResponseBody

    @GET("cert/getCrts")
    suspend fun getCrts(@QueryMap request: Map<String, @JvmSuppressWildcards Any>):EncodeResponseBody

    @POST("cert/htime/queryOneVersion")
    suspend fun queryOneVersion(@Header("VersionCode") versionCode: String, @QueryMap request: Map<String, @JvmSuppressWildcards Any>):EncodeResponseBody

}