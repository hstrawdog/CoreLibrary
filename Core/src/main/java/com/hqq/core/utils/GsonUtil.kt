package com.hqq.core.utils

import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
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
    private val GSON = GsonBuilder()
            .serializeNulls() //排除transient标记的字段
            .create()

    fun toJsonString(o: Any?): String {
        return GSON.toJson(o)
    }

    fun toJsonString(o: Any?, type: Type?): String {
        return GSON.toJson(o, type)
    }

    fun <T> fromJson(json: String?, clazz: Class<T>?): T? {
        return try {
            GSON.fromJson(json, clazz)
        } catch (e: JsonSyntaxException) {
            null
        }
    }

    fun <T> fromJson(json: String?, type: Type?): T? {
        return try {
            GSON.fromJson(json, type)
        } catch (e: JsonSyntaxException) {
            null
        }
    }

    fun <T> fromJson(json: JsonElement?, clazz: Class<T>?): T? {
        return try {
            GSON.fromJson(json, clazz)
        } catch (e: JsonSyntaxException) {
            null
        }
    }

    fun <T> fromJson(json: JsonElement?, type: Type?): T? {
        return try {
            GSON.fromJson(json, type)
        } catch (e: JsonSyntaxException) {
            null
        }
    }

    fun toJsonTree(o: Any?): JsonElement {
        return GSON.toJsonTree(o)
    }

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
    fun getValue(json: String?, key: String?): JsonElement {
        return JsonParser.parseString(json).asJsonObject[key]
    }
}