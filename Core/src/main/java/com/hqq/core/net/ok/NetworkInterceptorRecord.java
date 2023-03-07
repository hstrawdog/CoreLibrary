package com.hqq.core.net.ok;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.net.ok
 * @Date : 下午 4:44
 * @Email : qiqiang213@gmail.com
 * @Describe : 统计请求耗时
 */
public class NetworkInterceptorRecord implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        long startReq = System.currentTimeMillis();
        String rawUrl = request.url().uri().toString();
        Response response = chain.proceed(request);
        long endReq = System.currentTimeMillis();
        System.out.println("TTFB:   " + rawUrl + "         :  " + (endReq - startReq));
        return response;
    }
}