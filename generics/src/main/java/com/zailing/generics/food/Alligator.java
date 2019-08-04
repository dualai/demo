package com.zailing.generics.food;

import com.zailing.LogUtil;

public class Alligator extends Animal {
    @Override
    public void eaten() {
        LogUtil.d("!!");
    }
}
