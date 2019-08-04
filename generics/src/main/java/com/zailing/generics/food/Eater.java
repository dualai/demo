package com.zailing.generics.food;

public class Eater<T extends Food> {
    public void eat(T food){
        food.eaten();
    }
}
