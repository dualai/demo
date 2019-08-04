package com.zailing.generics.fruit;

import com.zailing.generics.fruit.Fruit;

import java.util.List;

public class Watermelon implements Fruit {
    @Override
    public float getWeight() {
        return 1;
    }

    public boolean hasSeed() {
        return true;
    }
}
