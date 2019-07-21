package com.pig.ui.animator;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.Choreographer;
import android.widget.Button;

import com.pig.ui.R;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animator);
        Button button = findViewById(R.id.btn);

        MyObjectAnimator objectAnimator = MyObjectAnimator.
                ofFloat(button, "scaleX", 1,3,2);
        objectAnimator.setDuration(3000);
        objectAnimator.start();


    }
}
