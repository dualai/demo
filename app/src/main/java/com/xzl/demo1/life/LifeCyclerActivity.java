package com.xzl.demo1.life;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.xzl.demo1.R;

public class LifeCyclerActivity extends AppCompatActivity {
    private MapLocation mapLocation;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lifecycle);

        mapLocation = new MapLocation(getLifecycle());
        getLifecycle().addObserver(mapLocation);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        getLifecycle().removeObserver(mapLocation);
    }
}
