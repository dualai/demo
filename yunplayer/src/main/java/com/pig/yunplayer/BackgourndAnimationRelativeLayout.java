package com.pig.yunplayer;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.animation.AccelerateInterpolator;
import android.widget.RelativeLayout;

public class BackgourndAnimationRelativeLayout extends RelativeLayout {
    private LayerDrawable layerDrawable;
    private ObjectAnimator objectAnimator;

    public BackgourndAnimationRelativeLayout(Context context) {
        super(context);
    }

    public BackgourndAnimationRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BackgourndAnimationRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        Drawable backgroundDrawable = ContextCompat.getDrawable(getContext(),R.drawable.ic_blackground);
        Drawable[] drawables = new Drawable[2];
        /*初始化时先将前景与背景颜色设为一致*/
        drawables[0] = backgroundDrawable;
        drawables[1] = backgroundDrawable;
        layerDrawable = new LayerDrawable(drawables);

        //监听动画的执行的进度，不需要更改控件任何的属性，给number
        objectAnimator = ObjectAnimator.ofFloat(this,"number",0f,10f);
        objectAnimator.setDuration(500);
        objectAnimator.setInterpolator(new AccelerateInterpolator());
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //(VSYNC-start)%duration / duration
//                animation.getAnimatedValue()
                int foregroundAlpha = (int) ((float) animation.getAnimatedValue() * 255);
                layerDrawable.getDrawable(1).setAlpha(foregroundAlpha);
                setBackground(layerDrawable);
            }
        });


        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                /*动画结束后，记得将原来的背景图及时更新*/
                layerDrawable.setDrawable(0, layerDrawable.getDrawable(1));
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

    }

    //在上面那层，做透明度动画
    public void setForeground(Drawable drawable) {
        layerDrawable.setDrawable(1, drawable); //设置并且提到上层
//        layerDrawable.setDrawableByLayerId() //
        objectAnimator.start();
    }
}
