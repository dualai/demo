package com.pig.ui.animator;

import android.view.View;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 设值,助理
 */
public class MyFloatPropertyValuesHolder {
    //属性名
    String mPropertyName;
    //
    Class mValueType;
    //
    MyKeyframeSet mKeyframes;
    //从类中取出method
    Method mSetter = null; //把View的set属性相关的方法反射出来,setScaleX、setAlpha、setRotate等..

    public MyFloatPropertyValuesHolder(String mPropertyName, float ...values) {
        this.mPropertyName = mPropertyName;
        mValueType = float.class;
        //
        mKeyframes = MyKeyframeSet.ofFloat(values);
    }

    public void setupSetter(WeakReference<View> target) {
        char firstLetter = Character.toUpperCase(mPropertyName.charAt(0));
        String theRest = mPropertyName.substring(1);
        String methodName="set"+ firstLetter + theRest;
        //获取方法
        try {
            mSetter = View.class.getMethod(methodName, float.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public void setAnimatedValue(View target, float fraction) {
        Object value = mKeyframes.getValue(fraction);//根据百分比，返回具体的值
        try {
            mSetter.invoke(target,value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
