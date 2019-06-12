package com.xzl.demo1.presentation;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.xzl.demo1.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends Activity {


    FrameLayout mRoot;
    FrameLayout mContent;
    TextView tv_test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presentation);
        mRoot = findViewById(R.id.root);

        reOrination();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                addContent();
            }
        },2000);
    }


    private void reOrination() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mRoot, "rotation", 0, -90f);
        objectAnimator.setDuration(3000);
        objectAnimator.start();
    }

    private void addContent(){
        LayoutInflater inflater = LayoutInflater.from(this);
        mContent = (FrameLayout) inflater.inflate(R.layout.layout_presentation, mRoot, false);
        mRoot.addView(mContent);
    }

}
