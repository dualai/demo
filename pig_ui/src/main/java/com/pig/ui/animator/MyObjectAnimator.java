package com.pig.ui.animator;

import android.animation.ObjectAnimator;
import android.view.View;

import java.lang.ref.WeakReference;


public class MyObjectAnimator implements VSYNCManager.AnimationFrameCallback {

    private final static String TAG = "MyObjectAnimator";
    long mStartTime = -1;
    private long mDuration = 0;
    private WeakReference<View> target; //重量级引用，弱引用
    private MyFloatPropertyValuesHolder myFloatPropertyValuesHolder;
    private float index = 0;
    private TimeInterpolator interpolator;
    public void setDuration(int duration) {
        this.mDuration = duration;
    }
    public void setInterpolator(TimeInterpolator interpolator) {
        this.interpolator = interpolator;
    }

    private MyObjectAnimator(View view, String propertyName, float... values) {
        target = new WeakReference<View>(view);
        myFloatPropertyValuesHolder = new MyFloatPropertyValuesHolder(propertyName, values);
    }

    public static MyObjectAnimator ofFloat(View view, String propertyName, float... values) {
        MyObjectAnimator anim = new MyObjectAnimator(view, propertyName, values);

        return anim;
    }


    //回调，每隔16ms
    @Override
    public boolean doAnimationFrame(long currentTime) {
        float total = mDuration / 16;
        //执行百分比 (index++)/total

        float fraction = (index++) / total; //执行百分比
        if(interpolator != null){
            //插值器，执行时间没变，调用次数没变，每个百分比做转化，线性插值器、加速度插值器等，或者自定义插值器...
            fraction = interpolator.getInterpolation(fraction);
        }

        if(index >= total){
            index = 0;
        }

        //fraction可能被插值器转换过
        myFloatPropertyValuesHolder.setAnimatedValue(target.get(),fraction);

        return false;
    }


    public void start() {
        myFloatPropertyValuesHolder.setupSetter(target);
        mStartTime = System.currentTimeMillis();
        VSYNCManager.getInstance().add(this);
        //初始化

    }
}
