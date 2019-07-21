package com.pig.ui.animator;

//关键帧 保存着某一时刻具体的状态
public class MyFloatKeyframe {
    float mFraction;  //当前百分比，0~1
    Class mValueType; //
    float mValue; //值，初始化的时候，把以后的每一个关键帧节点的值都算出来，其他帧由估值器来完成

    public  MyFloatKeyframe(float fraction, float value) {
        mFraction = fraction;
        mValue = value;
        mValueType = float.class;
    }

    public float getValue() {
        return mValue;
    }

    public void setValue(float mValue) {
        this.mValue = mValue;
    }

    public float getFraction() {
        return mFraction;
    }
}
