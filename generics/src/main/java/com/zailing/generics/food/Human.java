package com.zailing.generics.food;

import com.zailing.LogUtil;

public class Human extends Animal {
    /**
     * 复写
     */
    @Override
    public void eaten() {
        LogUtil.d("我被吃了");
    }
}
