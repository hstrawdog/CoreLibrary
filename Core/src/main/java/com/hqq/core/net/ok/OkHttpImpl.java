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

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * $desc$
 * author  黄其强
 * Created by Administrator on 2016/12/14 15:54.
 */

public class OkHttpImpl implements HttpCompat {

    OkHttpClient mOkHttpClient;
    Handler mHandler;
    public final static int WRITE_TIMEOUT = 60;
    private static String TAG = "OkHttpImpl";

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
        final String realUrl;
        if (params != null) {
            realUrl = url + '?' + params.paramGet();
        } else {
            realUrl = url;
        }
        Request.Builder request = new Request.Builder().url(realUrl).get();
        Call call = mOkHttpClient.newCall(request.build());
        try {
            Response response = call.execute();
            final String string = response.body().string();
            final int code = response.code();
            postHandler(null, callback, response.isSuccessful(), code, string);
        } catch (IOException e) {
            e.printStackTrace();
            postHandler(null, callback, false, 0, "网络连接失败,请检查网络");
        }
        return call;
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
        final String realUrl;
        if (params != null) {
            realUrl = url + '?' + params.paramGet();
        } else {
            realUrl = url;
        }
        Request.Builder request = new Request.Builder().url(realUrl).get();
        Call call = mOkHttpClient.newCall(request.build());
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                postHandler(handler, callback, false, 0, "网络连接失败,请检查网络");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String string = response.body().string();
                final int code = response.code();
                postHandler(handler, callback, response.isSuccessful(), code, string);
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
     * 同步请求
     *  @param url
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

            final int code = response.code();
            final String string = response.body().string();
            postHandler(null, callback, response.isSuccessful(), code, string);
        } catch (IOException e) {
            e.printStackTrace();
            postHandler(null, callback, false, 0, "网络连接失败,请检查网络");

        }
        return call;

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
        return doRequest(o, request, callback);
    }

    /**
     * 处理 post  请求
     *
     * @param request
     * @param callback
     * @return
     */
    private Call doRequest(Handler handler, Request.Builder request, final OkNetCallback callback) {
        Call call = mOkHttpClient.newCall(request.build());
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                postHandler(mHandler, callback, false, 0, "网络连接失败,请检查网络");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final int code = response.code();
                final String string = response.body().string();
                postHandler(mHandler, callback, response.isSuccessful(), code, string);
            }
        });
        return call;
    }

    /**
     * 回调数据
     *
     * @param handler
     * @param callback
     * @param successful
     * @param statusCode
     * @param responseString
     */
    private static void postHandler(Handler handler, final OkNetCallback callback
            , final boolean successful, final int statusCode, final String responseString) {
        if (handler != null) {
            handler.post(() -> post(callback, successful, statusCode, responseString)
            );
        } else {
            post(callback, successful, statusCode, responseString);
        }

    }

    /**
     * 回调数据
     *
     * @param callback
     * @param successful
     * @param statusCode
     * @param responseString
     */
    private static void post(final OkNetCallback callback
            , final boolean successful, final int statusCode, final String responseString) {
        if (successful) {
            callback.onSuccess(statusCode + "", responseString);
            return;
        } else {
            callback.onFailure(String.valueOf(statusCode), "网络连接失败,请检查网络", "");
        }

    }

}
