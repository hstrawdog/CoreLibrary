package com.hqq.core.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.utils
 * @FileName :   ReflexUtils
 * @Date : 2020/7/24 0024  下午 3:32
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
class ReflexUtils {

    public static Class getT4Class(Object o) {
        Type type = o.getClass().getGenericSuperclass();

        if (type instanceof ParameterizedType) {
            return (Class) ((ParameterizedType) type).getActualTypeArguments()[0];
        }
        return null;
    }

    static class A<T> {

    }

    public static void main(String[] args) {

        System.out.println("args = " +         ReflexUtils.getT4Class(new A<String>()).getName());
        System.out.println("args = " +         ReflexUtils.getT4Class(new A<Integer>()).getName());
    }
}
