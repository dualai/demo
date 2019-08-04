package com.zailing.generics.shop;


import com.zailing.generics.fruit.Fruit;

public interface NormalFruitShop extends NormalShop {
    Fruit buy();

    float refund(Fruit item);
}
