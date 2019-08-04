package com.zailing.generics.shop;


import com.zailing.generics.fruit.Apple;
import com.zailing.generics.sim.Sim;

import java.io.Serializable;

/**
 * 沿用父类的泛型T，并且，自己也有自己的扩展泛型S;
 * @param <T>
 * @param <S> 边界限制；extends 只是限定符，S可以直接是Sim，或者Sim的子类；跟继承类或接口不是一个概念；
 *
 * S extends Apple & Sim & Serializable, 但是不能同时继承多个类，再加一个Banana就不行；可以多个接口；类要写在最左边；
 *
 *  S super :  super后面是
 *  <？>:
 *
 *  < ? extends Fruit> :
 *
 */
public interface SimShop<T,S extends Sim> extends Shop<T>{
    S buySim(String name,String id);
}
