/*
 * Create on 2016-12-14 下午3:54
 * FileName: OkHttpImpl.java
 * Author: huang qiqiang
 * Contact: http://www.huangqiqiang.cn
 * Email 593979591@QQ.com
 *
 */

package com.hqq.core.net.ok;

import android.os.Handler;
import android.os.Looper;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * @version V1.0 <描述当前版本功能>
 * @Author : huangqiqiang
 * @Package : com.hqq.core.net.ok
 * @FileName :   OkHttpImpl
 * @Date : 2016/12/14  下午15:54
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public class OkHttpImpl implements HttpCompat {
    OkHttpClient mOkHttpClient;
    Handler mHandler;
    public final static int WRITE_TIMEOUT = 60;

    @Override
    public HttpCompat create() {
        mOkHttpClient = new OkHttpClient.Builder()
                //设置读取超时时间
                .readTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                //设置写的超时时
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .build();
        mHandler = new Handler(Looper.getMainLooper());
        return this;
    }

    /**
     * get 请求
     *
     * @param url
     * @param params
     * @param callback
     */
    @Override
    public void get(String url, ParamsCompat params, final OkNetCallback callback) {
        doGet(mHandler, url, params, callback);
    }

    /**
     * 同步的get  请求
     *
     * @param url
     * @param params
     * @param callback
     * @return
     */
    @Override
    public Call getExecute(String url, ParamsCompat params, OkNetCallback callback) {
        Request.Builder request = getBuilder(url, params);
        Call call = mOkHttpClient.newCall(request.build());
        try {
            Response response = call.execute();

            postHandler(null, callback, response, params);
        } catch (IOException e) {
            e.printStackTrace();
            postHandler(null, callback, null, null);
        }
        return call;
    }

    @NotNull
    private Request.Builder getBuilder(String url, ParamsCompat params) {
        final String realUrl;
        if (params != null) {
            realUrl = url + '?' + params.paramGet();
        } else {
            realUrl = url;
        }
        return new Request.Builder().url(realUrl).get();
    }

    /**
     * 执行get 请求
     *
     * @param handler
     * @param url
     * @param params
     * @param callback
     * @return
     */
    private Call doGet(Handler handler, String url, ParamsCompat params, OkNetCallback callback) {
        Request.Builder request = getBuilder(url, params);
        Call call = mOkHttpClient.newCall(request.build());
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                postHandler(handler, callback, null, null);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                postHandler(handler, callback, response, params);
            }
        });
        return call;
    }

    /**
     * post 请求
     *
     * @param url
     * @param params
     * @param callback
     * @return
     */
    @Override
    public Call post(String url, ParamsCompat params, OkNetCallback callback) {
        return doPost(url, params, callback, mHandler);
    }

    /**
     * 执行post 请求
     *
     * @param url
     * @param params
     * @param callback
     * @param o
     * @return
     */
    private Call doPost(String url, ParamsCompat params, OkNetCallback callback, Handler o) {
        RequestBody body = params.paramForm();
        Request.Builder request = new Request.Builder().url(url).post(body);
        return doRequest(o, request, callback, params);
    }

    /**
     * 同步请求
     *
     * @param url
     * @param params
     * @param callback
     * @return
     */
    @Override
    public Call postExecute(String url, ParamsCompat params, OkNetCallback callback) {
        RequestBody body = params.paramForm();
        Request.Builder request = new Request.Builder().url(url).post(body);
        Call call = mOkHttpClient.newCall(request.build());
        try {
            Response response = call.execute();
            postHandler(null, callback, response, params);
        } catch (IOException e) {
            e.printStackTrace();
            postHandler(null, callback, null, null);

        }
        return call;

    }

    /**
     * 处理 post  请求
     *
     * @param request
     * @param callback
     * @param params
     * @return
     */
    private Call doRequest(Handler handler, Request.Builder request, final OkNetCallback callback, ParamsCompat params) {
        Call call = mOkHttpClient.newCall(request.build());
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                postHandler(handler, callback, null, null);
            }

            @Override
            public void onResponse(Call call, Response response) {
                postHandler(handler, callback, response, params);
            }
        });
        return call;
    }

    /**
     * 是否需要切换线程
     *
     * @param handler
     * @param callback
     * @param response
     * @param paramsCompat
     */
    private static void postHandler(Handler handler, OkNetCallback callback
            , Response response, ParamsCompat paramsCompat) {
        if (handler != null) {
            handler.post(() -> post(callback, response, paramsCompat)
            );
        } else {
            post(callback, response, paramsCompat);
        }

    }


    /**
     * 回调数据
     *
     * @param callback
     * @param response
     * @param paramsCompat
     */
    private static void post(final OkNetCallback callback, Response response, ParamsCompat paramsCompat) {
        if (response != null) {
            try {
                String string = getDecodeResponse(response, paramsCompat);
                // 错误码二次校验
                int code = response.code();

                callback.onSuccess(code + "", string);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            callback.onFailure("0", "网络连接失败,请检查网络", "");
        }

    }

    /**
     * 解码数据
     *
     * @param response
     * @param paramsCompat
     * @return
     * @throws IOException
     */
    @NotNull
    private static String getDecodeResponse(Response response, ParamsCompat paramsCompat) throws IOException {
        // 返回数据进行解码
        String string = "";
        if (paramsCompat.getDecode().isEmpty()) {
            string = response.body().string();
        } else {
            byte bytes[] = response.body().bytes();
            string = new String(bytes, paramsCompat.getDecode());
        }
        return string;
    }

}
