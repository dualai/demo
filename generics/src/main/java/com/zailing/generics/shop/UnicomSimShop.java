package com.zailing.generics.shop;

import com.zailing.generics.fruit.Apple;
import com.zailing.generics.sim.ChinaUnicomSim;

/**
 * 直接确定了泛型的类型。。。。。。。。
 */

/**
 * SimShop<Apple, ChinaUnicomSim> 泛型参数实例化；
 */
public class UnicomSimShop implements SimShop<Apple, ChinaUnicomSim> {
    @Override
    public ChinaUnicomSim buySim(String name, String id) {
        return new ChinaUnicomSim();
    }

    @Override
    public Apple buy() {
        return new Apple();
    }

    @Override
    public float refund(Apple item) {
        return 10;
    }
}
