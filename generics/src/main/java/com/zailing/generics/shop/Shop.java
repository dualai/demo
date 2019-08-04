package com.zailing.generics.shop;

import android.view.View;

import com.zailing.generics.sim.Sim;

import java.util.List;

/***
 * 泛型参数，不能和关键字一样
 */

/**
 * Parameter : 形参
 * <p>
 * Argument： 实参
 */

public interface Shop<T> {
    T buy(); // 不是泛型方法；

    float refund(T item);

    //泛型应用于限制方法的参数类型，java除了泛型以外，其他方式达不到；
//    <E extends Shop & Sim> void someMethod(E item);
    //泛型用于限制list必须可以add E，java 其他方式也达不到要求
//    <E> void addToList(E item, List<? super E> list);

    /**
     * 不一定非要返回E；
     * 每次调用该方法，传的E可以不同；
     * @param item
     * @param money
     * @param <E>
     * @return
     */
//    <E> E refurbish(E item,float money);

    //泛型方法，常需要运行时强转
//    public <V> V findViewById(int id){
//        View view = findView(id);
//        return (V) view;
//    }
    /***
     * 静态方法的泛型跟成员方法一样，选用的标准就是，如果不沿用对象的任何属性，直接用静态方法的泛型方式；
     */


}


/**
 * 以下写法扩展性很差，因为一开始创建shop的时候就要确定卖的T是什么，能换的物品E又是什么？
 * 所以，用以上方法，把能换的物品和卖的物品独立开来
 *
 * @param <T>
 * @param <E>
 */
//public interface Shop<T,E> {
//    T buy(); // 不是泛型方法；
//    float refund(T item);
//
//    E refurbish(E item,float money);
//
//    //泛型方法，常需要运行时强转
////    public <V> V findViewById(int id){
////        View view = findView(id);
////        return (V) view;
////    }
//}

