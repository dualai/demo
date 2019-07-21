package com.pig.ui.splashwangyi;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class ParallaxFragment extends Fragment {

    //此Fragment上所有的需要实现视差动画的视图
    private List<View> parallaxViews = new ArrayList<View>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater original, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();

        int layoutId = args.getInt("layoutId");

        //监听当前fragment加载layout的过程。。。交给自定义的layoutInflater
        ParallaxLayoutInflater inflater = new ParallaxLayoutInflater(original,getContext(), this);
        //不挂载到根容器？fragment不需要挂载..?
        return inflater.inflate(layoutId,null);
    }

    public List getParallaxViews() {
        return parallaxViews;
    }
}
