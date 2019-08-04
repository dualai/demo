package com.zailing.generics.shop;

/**
 * T定义后只在当前类中有效； 所以子类中如果确定不了T的类型，又要延续T，需要重新定义T； 或者定义成其他名字，E，也可以；
 *
 * 第一个T 是 参数类型，形参 也只有在这里才是泛型的定义；
 * 第二个T 是 具体参数 实参；
 * 泛型的实例化，List<Fruit> fruits = new ArrayList<>(); List<Fruit> fruits 这一段也是对泛型的实例化，其中Fruit就是实参；
 *
 *
 */
public interface RepairableShop<T> extends Shop<T> {
    void repair(T item);
}
