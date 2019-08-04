package com.zailing.generics;

import java.util.Arrays;
import java.util.List;

public class HenCoderList<T> {
//    T[] instances = new T[0];
    Object[] instances = new Object[0];

//    public static T staticInstance;

    public T get(int index){
        return (T) instances[index];
    }

    public void add(T instance){
        // 拷贝数组，并且赋予新长度；
        instances = Arrays.copyOf(instances, instances.length + 1);
        instances[instances.length - 1] = instance;

    }
}
