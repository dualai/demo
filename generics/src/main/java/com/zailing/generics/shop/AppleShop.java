package com.zailing.generics.shop;

import com.zailing.generics.fruit.Apple;


/**
 * 实现泛型;
 * Class<具体类型>
 */

public class AppleShop implements Shop<Apple> {
    @Override
    public Apple buy() {
        return null;
    }

    @Override
    public float refund(Apple item) {
        return 0;
    }
}
