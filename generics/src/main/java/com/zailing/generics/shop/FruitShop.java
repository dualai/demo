package com.zailing.generics.shop;

import com.zailing.generics.fruit.Fruit;

public interface FruitShop<T extends Fruit> extends Shop<T> {

}