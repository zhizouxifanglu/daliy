package com.heath.daily;

import java.lang.reflect.Field;

/**
 * 使用反射来修改这个内部的 IntegerCache 缓存，从⽽让 Integer 的值发生紊乱
 * @author hex
 * @date 2024/2/1
 **/
public class IntegerCacheManipulation {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        // 通过反射的方式访问 IntegerCache
        Class<?> clazz = Integer.class.getDeclaredClasses()[0];
        Field cacheField = clazz.getDeclaredField("cache");
        cacheField.setAccessible(true);

        // 获取 cache 数组的实际引用
        Integer[] cache = (Integer[]) cacheField.get(null);

        // 修改cache，造成奇怪行为
        cache[129] = cache[130];

        Integer a = 1;
        Integer b = 2;

        System.out.println(a == b); // true
    }

}
