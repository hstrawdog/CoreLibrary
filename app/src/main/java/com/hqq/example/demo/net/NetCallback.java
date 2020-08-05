package com.hqq.example.demo.net;

import com.hqq.core.utils.ToastUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.demo.net
 * @FileName :   NetCallback
 * @Date : 2020/8/4 0004  下午 4:41
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public abstract class NetCallback<T> implements Callback<NetResponseBody<T>> {


    @Override
    public void onFailure(Call<NetResponseBody<T>> call, Throwable t) {
        onFail(-999, "网络连接失败,请检网络连接");
    }

    @Override
    public void onResponse(Call<NetResponseBody<T>> call, Response<NetResponseBody<T>> response) {
        if (response != null) {
            if (response.code() == 200) {
                NetResponseBody responseBody = response.body();
                if ((responseBody.getError_code() == 0)) {
                    //   if (responseBody.getBody().getStatus() == 0 || responseBody.getBody().getStatus() == 200) {
                    onSuccess(response.body().getResult());
                } else {
                    onFail(responseBody.getError_code(), responseBody.getReason());
                }
                return;
            }
        }
        onFail(-9998, "服务器连接异常");

    }


    /**
     * @param response
     */
    abstract public void onSuccess(T response);

    /**
     * @param code
     * @param message
     */
    public void onFail(int code, String message) {
        ToastUtils.showToast(message);
    }


}
