package com.zailing.generics.shop;

import com.zailing.generics.food.Eater;
import com.zailing.generics.food.Food;

/***
 * 嵌套泛型；一步步看懂；卖 吃人的物品 的商店，T是 吃人的物品，F是被吃的食物；
 * @param <F>
 * @param <T>
 */
public interface EaterShop<F extends Food,T extends Eater<F>> extends Shop<T> {

}
