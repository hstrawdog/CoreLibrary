package com.hqq.core.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Type;

/**
 * @Author : huangqiqiang
 * @Package : com.qi.core.utils
 * @FileName :   GsonUtil
 * @Date : 2019/1/3 0003  上午 10:30
 * @Descrive :
 * @Email :  qiqiang213@gmail.com
 */
public class GsonUtil {
    private static final Gson GSON = new GsonBuilder()
            .serializeNulls()//排除transient标记的字段
            .create();

    public static String toJsonString(Object o) {
        return GSON.toJson(o);
    }

    public static String toJsonString(Object o, Type type) {
        return GSON.toJson(o, type);
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        try {

            return GSON.fromJson(json, clazz);
        } catch (JsonSyntaxException e) {
            return null;
        }
    }

    public static <T> T fromJson(String json, Type type) {
        try {
            return GSON.fromJson(json, type);
        } catch (JsonSyntaxException e) {
            return null;
        }
    }

    public static <T> T fromJson(JsonElement json, Class<T> clazz) {
        try {
            return GSON.fromJson(json, clazz);
        } catch (JsonSyntaxException e) {
            return null;
        }
    }

    public static <T> T fromJson(JsonElement json, Type type) {
        try {
            return GSON.fromJson(json, type);
        } catch (JsonSyntaxException e) {
            return null;
        }
    }

    public static JsonElement toJsonTree(Object o) {
        return GSON.toJsonTree(o);
    }

    public static JsonElement toJsonTree(Object o, Type type) {
        return GSON.toJsonTree(o, type);
    }

    public static JsonElement getValue(String json, String key) {
        return new JsonParser().parse(json).getAsJsonObject().get(key);
    }

}
