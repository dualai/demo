package com.zailing.generics;

import com.zailing.generics.fruit.Apple;
import com.zailing.generics.shop.Shop;

/**
 * 泛型嵌套：用一个泛型类型 Shop<T>，来作为另一个泛型HenCoderList 的泛型实例化实参；
 */
public class HenCoderShopList <T> extends HenCoderList<Shop<T>> {
//public class HenCoderShopList extends HenCoderList<Shop<Apple>> { //这样子前面就没必要再写T了；
//public class HenCoderShopList extends HenCoderList<Shop<Apple>> {

}
