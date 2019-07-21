package com.pig.ui.animator;

import android.animation.FloatEvaluator;
import android.animation.TypeEvaluator;

import com.pig.ui.util.LLog;

import java.util.Arrays;
import java.util.List;

public class MyKeyframeSet {
    // 类型估值器，
    TypeEvaluator mEvaluator;
    MyFloatKeyframe mFirstKeyframe;
    List<MyFloatKeyframe> mKeyframes;

    public MyKeyframeSet(MyFloatKeyframe ...keyframes) {
        mKeyframes = Arrays.asList(keyframes);
        mFirstKeyframe = keyframes[0];
        mEvaluator = new FloatEvaluator();
    }


    /**
     * 关键帧的属性值
     * @param values
     * @return
     */
    public static MyKeyframeSet ofFloat(float  ...values) {
        int numKeyframes = values.length;

        //生成关键帧
        MyFloatKeyframe keyframes[] = new MyFloatKeyframe[numKeyframes];
        //值，初始化的时候，把以后的每一个关键帧的值都算出来
        keyframes[0] = new MyFloatKeyframe(0, values[0]);
        for (int i = 1; i < numKeyframes; ++i) {
            keyframes[i] = new MyFloatKeyframe((float) i / (numKeyframes - 1), values[i]);
        }
        return new MyKeyframeSet(keyframes);
    }

    /**
     * fraction 已经被插值器转换过
     * 估值器
     * @param fraction
     * @return
     */
    public Object getValue(float fraction) {
        MyFloatKeyframe prevKeyframe = mFirstKeyframe;
        for (int i = 1; i < mKeyframes.size(); ++i) {
            MyFloatKeyframe nextKeyframe = mKeyframes.get(i);
            if (fraction < nextKeyframe.getFraction()) {
                LLog.d("evaluator,fraction,"+fraction+" prevKeyframe.getValue(),"+prevKeyframe.getValue()+
                        " nextKeyframe.getValue(),"+nextKeyframe.getValue()
                        );
                Object item = mEvaluator.evaluate(fraction, prevKeyframe.getValue(),
                        nextKeyframe.getValue());
                LLog.d("item,"+item);
                return item;
            }
            prevKeyframe = nextKeyframe;
        }
        return null;
    }
}
