package com.pig.ui.splashwangyi;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.nineoldandroids.view.ViewHelper;
import com.pig.ui.R;
import com.pig.ui.util.LLog;

import java.util.ArrayList;
import java.util.List;

/**
 * 平行空间的总控件，里头有个viewpager，viewpager装载了fragment
 */
public class ParallaxContainer extends FrameLayout implements ViewPager.OnPageChangeListener {
    private List<ParallaxFragment> fragments;
    public ParallaxContainer(Context context) {
        super(context);
    }

    public ParallaxContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    private ParallaxPagerAdapter adapter;
    private ImageView iv_man;
    public void setIv_man(ImageView iv_man) {
        this.iv_man = iv_man;
    }

    public void setUp(int ...childrenIds){
        //Fragment数组
        fragments = new ArrayList<>();

        //Bundle传递参数给fragment，在fragment的onCreateView当中接收
        for (int i = 0; i < childrenIds.length; i++) {
            ParallaxFragment f = new ParallaxFragment();
            Bundle args = new Bundle();
            //Fragment中需要加载的布局文件id
            args.putInt("layoutId", childrenIds[i]);
            f.setArguments(args);
            fragments.add(f);
        }

        ViewPager vp = new ViewPager(getContext());
        vp.setId(R.id.parallax_pager);
        SplashActivity activity = (SplashActivity)getContext();
        adapter = new ParallaxPagerAdapter(activity.getSupportFragmentManager(),fragments);
        vp.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        vp.setAdapter(adapter);
        vp.setOnPageChangeListener(this);
        addView(vp,0);
    }


    /**
     *
     * position -->当前页面，即点击滑动的页面 ;
     * positionOffset-->  当前页面偏移的百分比 ;
     * positionOffsetPixels --> 当前页面偏移的像素位置
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        /**
         * 手指移动的时候，设置界面元素的translate偏移值，实现动画
         *
         * X : 向左移动，translateX负数； 向右移动，translateX正数
         * Y : 向上移动，translateY负数， 向下移动，translateY正数
         */

        //做动画操作...
        LLog.d("position:"+position+" offset:"+positionOffset+" pixels:"+positionOffsetPixels);
        int containerWidth = getWidth();
        ParallaxFragment outFragment = null;
        try {
            outFragment = fragments.get(position - 1);
        } catch (Exception e) {}

        /**
         *
         */
        ParallaxFragment inFragment = null;
        try {
            inFragment = fragments.get(position);
        } catch (Exception e) {}


        if (outFragment != null) {
            //获取Fragment上所有的视图，实现动画效果
            List<View> inViews = outFragment.getParallaxViews();
//            动画
            if (inViews != null) {
                for (View view : inViews) {
//
                    ParallaxViewTag tag = (ParallaxViewTag) view.getTag(R.id.parallax_view_tag);
                    if (tag == null) {
                        continue;
                    }
                    LLog.d("containerWidth - positionOffsetPixels:"+(containerWidth - positionOffsetPixels));
                    ViewHelper.setTranslationX(view, (containerWidth - positionOffsetPixels) * tag.xIn);
                    ViewHelper.setTranslationY(view, (containerWidth - positionOffsetPixels) * tag.yIn);
                }

            }

        }
        if(inFragment != null){
            List<View> outViews = inFragment.getParallaxViews();
            if (outViews != null) {
                for (View view : outViews) {
                    ParallaxViewTag tag = (ParallaxViewTag) view.getTag(R.id.parallax_view_tag);
                    if (tag == null) {
                        continue;
                    }
                    //仔细观察退出的fragment中view从原始位置开始向上移动，translationY应为负数
                    ViewHelper.setTranslationY(view, 0 - positionOffsetPixels * tag.yOut);
                    ViewHelper.setTranslationX(view, 0 - positionOffsetPixels * tag.xOut);
                }
            }
        }
    }

    @Override
    public void onPageSelected(int position) {
        if (position == adapter.getCount() - 1) {
            iv_man.setVisibility(INVISIBLE);
        }else{
            iv_man.setVisibility(VISIBLE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        AnimationDrawable animation = (AnimationDrawable) iv_man.getBackground();
        switch (state) {
            case ViewPager.SCROLL_STATE_DRAGGING:
                animation.start();
                break;

            case ViewPager.SCROLL_STATE_IDLE:
                animation.stop();
                break;

            default:
                break;
        }
    }
}
