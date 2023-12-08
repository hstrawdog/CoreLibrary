package com.easy.core.utils.gson

import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes

/**
 * @Author : huangqiqiang
 * @Package : com.easy.readbook.bean.book
 * @Date : 下午 5:55
 * @Email : qiqiang213@gmail.com
 * @Describe :  注解忽略字段
 * GsonBuilder()
 * .setExclusionStrategies( GsonExclusionStrategy())
 */
class GsonExclusionStrategy : ExclusionStrategy {
    /**
     * 是否跳过属性 不序列化
     * 返回 false 代表 属性要进行序列化
     * @param f
     * @return
     */
    override fun shouldSkipField(f: FieldAttributes): Boolean {
        /**
         * 判断当前属性是否带有GsonExclude排除的注解
         * 若有 则 不进行序列化
         * 若无 为 null 则进行序列化
         */
        return f.getAnnotation(GsonExclude::class.java) != null
    }

    /**
     * 是否排除对应的类
     * 同时这里也可以 排除 int 类型的属性 不进行序列化
     *
     * 若不排除任何 类  直接 返回false 即可
     * @param clazz
     * @return
     */
    override fun shouldSkipClass(clazz: Class<*>): Boolean {
        return clazz == Int::class.javaPrimitiveType
    }
}