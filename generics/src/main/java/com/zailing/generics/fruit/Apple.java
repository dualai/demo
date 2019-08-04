package com.zailing.generics.fruit;

import java.util.List;

public class Apple implements Fruit {
    @Override
    public float getWeight() {
        return 1;
    }

    /**
     * 能放apple的list List<? super Apple>
     * @param list
     */
    public void addMetoList(List<? super Apple> list){
//        this = list.get(0); 取的话，取不了了；
        list.add(this);
    }
}
