/*
 * Create on 2016-10-26 下午2:47
 * FileName: OKParamsImpl.java
 * Author: Du Jianchi
 * Contact: du-1@qq.com or http://dujc.cn
 */

package com.hqq.core.net.ok;


import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @Author : huangqiqiang
 * @Package : com.qq.readbook.net.ok
 * @FileName :   OKParamsImpl
 * @Date : 2020/12/9 0009  下午 5:15
 * @Email :  qiqiang213@gmail.com
 * @Descrive :Created by du on 16/9/4.
 */
public class OkParamsImpl implements HttpCompat.ParamsCompat {

    private final List<String> keys = new ArrayList<>();
    private final List<Object> values = new ArrayList<>();

    @Override
    public HttpCompat.ParamsCompat put(String key, Object value) {
        keys.add(key);
        values.add(value);
        return this;
    }

    @Override
    public String paramGet() {
        return lineUp();
    }

    /**
     * @return
     */
    @Override
    public RequestBody paramForm() {
        return RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), lineUp());
    }

    /**
     * 表单方式提交数据
     * 正常是用于数据上传
     * 未测试
     *
     * @return
     */
    @Override
    public RequestBody paramMulti() {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        StringBuilder stringBuilder = new StringBuilder();
        final int length = keys.size();
        for (int index = 0; index < length; index++) {
            Object value = values.get(index);
            if (value instanceof File) {
                File f = (File) value;
                String filename = f.getName();
                RequestBody fileBody = RequestBody.create(MediaType.parse(guessMimeType(filename)), f);
                builder.addFormDataPart(keys.get(index), filename, fileBody);
                builder.addPart(fileBody);
            } else {
                builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + keys.get(index) + "\""),
                        RequestBody.create(null, value + ""));
                stringBuilder.append(keys.get(index)).append('=').append(encode(value + "")).append('&');
            }
        }
        int len = stringBuilder.length();
        if (len > 0) {
            String strings = stringBuilder.substring(0, len - 1);
            RequestBody params = RequestBody.create(MultipartBody.FORM
                    , strings);
            builder.addPart(params);
        }
        return builder.build();
    }

    @Override
    public String toString() {
        return lineUp();
    }

    /**
     * 组装成一行数据
     *
     * @return
     */
    private String lineUp() {
        StringBuilder builder = new StringBuilder();
        final int length = keys.size();
        for (int index = 0; index < length - 1; index++) {
            builder.append(keys.get(index)).append('=').append(encode(values.get(index) + "")).append('&');
        }
        if (length - 1 >= 0) {
            builder.append(keys.get(length - 1)).append('=').append(encode(values.get(length - 1) + ""));
        }
        return builder.toString();
    }

    private static String encode(String string) {
        try {
            return URLEncoder.encode(string, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return string;
    }

    private static String guessMimeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = null;
        try {
            contentTypeFor = fileNameMap.getContentTypeFor(URLEncoder.encode(path, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }
}
