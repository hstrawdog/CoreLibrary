package com.easy.example.test

import java.util.*

/**
 * @Author : huangqiqiang
 * @Package : com.core.netapi.net.manager
 * @Date : 上午 11:46
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class RequestBuild {
    private val mapRequest : MutableMap<String , String> = HashMap()
    fun appent(key : String , value : Any?) : RequestBuild {
        if (value ==null){

        }else{
            mapRequest[key] = value.toString()
        }
        return this
    }

    fun build(isAddToken : Boolean = true , osType :String ="Android") : Map<String , String> {
        if (isAddToken) {
        }
        //        mapRequest.put("accessToken", "fd913447-1672-489c-aaef-44b8e87e2e6d");
        //        mapRequest.put("accessToken", "2234faa5-cd47-4719-bd8f-5c17df549a2a");
        //        mapRequest.put("accessToken", "2234faa5-cd47-4719-bd8f-5c17df549a2a");
//                mapRequest.put("accessToken", "f94409d8-3b8b-4a12-b81e-232168190277");

        return mapRequest
    }

    companion object {
        val map : Map<String , Any>
            get() = HashMap()
    }
}