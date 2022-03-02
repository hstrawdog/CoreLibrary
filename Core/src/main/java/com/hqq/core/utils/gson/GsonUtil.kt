package com.hqq.core.utils.gson

import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import com.google.gson.stream.JsonReader
import com.hqq.core.utils.log.LogUtils
import java.lang.reflect.Type

/**
 * @Author : huangqiqiang
 * @Package : com.qi.core.utils
 * @FileName :   GsonUtil
 * @Date : 2019/1/3 0003  上午 10:30
 * @Email :  qiqiang213@gmail.com
 * @Descrive :
 */
object GsonUtil {
    /**
     * 如果不设置serializeNulls,序列化时默认忽略NULL
     * */
    private val GSON = GsonBuilder()
        .serializeNulls()
        .create()

    /**
     *
     * @param o Any?
     * @return String
     */
    @JvmStatic
    fun toJsonString(o: Any?): String {
        return GSON.toJson(o)
    }

    /**
     *
     * @param o Any?
     * @param type Type?
     * @return String
     */
    @JvmStatic
    fun toJsonString(o: Any?, type: Type?): String {
        return GSON.toJson(o, type)
    }

    /**
     *
     * @param o JsonReader
     * @param type Type?
     * @return String
     */
    @JvmStatic
    fun fromJson(o: JsonReader, type: Type?): String {
        return GSON.fromJson(o, type)
    }

    /**
     *
     * @param json String?
     * @param clazz Class<T>?
     * @return T?
     */
    @JvmStatic
    fun <T> fromJson(json: String?, clazz: Class<T>?): T? {
        return try {
            GSON.fromJson(json, clazz)
        } catch (e: JsonSyntaxException) {
            LogUtils.e("-- fromJson --:  ${e.toString()}")
            null
        }
    }

    /**
     *
     * @param json String?
     * @param type Type?
     * @return T?
     */
    @JvmStatic
    fun <T> fromJson(json: String?, type: Type?): T? {
        return try {
            GSON.fromJson(json, type)
        } catch (e: JsonSyntaxException) {
            LogUtils.e("-- fromJson --:  ${e.toString()}")
            null
        }
    }

    /**
     *
     * @param json JsonElement?
     * @param clazz Class<T>?
     * @return T?
     */
    @JvmStatic
    fun <T> fromJson(json: JsonElement?, clazz: Class<T>?): T? {
        return try {
            GSON.fromJson(json, clazz)
        } catch (e: JsonSyntaxException) {
            LogUtils.e("-- fromJson --:  ${e.toString()}")
            null
        }
    }

    /**
     *
     * @param json JsonElement?
     * @param type Type?
     * @return T?
     */
    @JvmStatic
    fun <T> fromJson(json: JsonElement?, type: Type?): T? {
        return try {
            GSON.fromJson(json, type)
        } catch (e: JsonSyntaxException) {
            LogUtils.e("-- fromJson --:  ${e.toString()}")
            null
        }
    }

    /**
     *
     * @param o Any?
     * @return JsonElement
     */
    @JvmStatic
    fun toJsonTree(o: Any?): JsonElement {
        return GSON.toJsonTree(o)
    }

    /**
     *
     * @param o Any?
     * @param type Type?
     * @return JsonElement
     */
    @JvmStatic
    fun toJsonTree(o: Any?, type: Type?): JsonElement {
        return GSON.toJsonTree(o, type)
    }

    /**
     * 从json 中获取到value
     *
     * @param json
     * @param key
     * @return
     */
    @JvmStatic
    fun getValue(json: String?, key: String?): JsonElement {
        return JsonParser.parseString(json).asJsonObject[key]
    }
}