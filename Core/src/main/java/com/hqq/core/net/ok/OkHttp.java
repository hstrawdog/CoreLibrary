
package com.hqq.core.net.ok;

/**
 * @Author : huangqiqiang
 * @Package : com.qq.readbook.net.ok
 * @FileName :   OkHttp
 * @Date : 2016/12/14 15:51
 * @Email :  qiqiang213@gmail.com
 * @Descrive :
 */
public class OkHttp {
    private static HttpCompat mOkHttp = null;

    public synchronized static HttpCompat newHttpCompat() {
        if (mOkHttp == null) {
            mOkHttp = new OkHttpImpl().create();
        }
        return mOkHttp;
    }

    /**
     * 空的对象
     *
     * @return
     */
    public static HttpCompat.ParamsCompat newParamsCompat() {
        return new OkParamsImpl();
    }

    /**
     * key value 对象
     *
     * @param key
     * @param values
     * @return
     */
    public static HttpCompat.ParamsCompat newParamsCompat(String key, Object values) {
        HttpCompat.ParamsCompat paramsCompat = newParamsCompat();
        paramsCompat.put(key, values);
        return paramsCompat;
    }
}
