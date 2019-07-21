package com.xzl.demo1.life;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.OnLifecycleEvent;
import android.util.Log;

import com.xzl.demo1.LLog;

public class MapLocation implements LifecycleObserver {
    private Lifecycle mLifecycle;
    public MapLocation(Lifecycle lifecycle) {
        this.mLifecycle = lifecycle;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate(){
        LLog.d("map onCreate");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume(){
        LLog.d("map onResume");
        startLocate();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause(){
        LLog.d("map onPause");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy(){
        LLog.d("map onDestroy");
    }

    public void startLocate(){
        LLog.d("current state:"+this.mLifecycle.getCurrentState().toString());
        if(this.mLifecycle.getCurrentState().isAtLeast(Lifecycle.State.STARTED)){
            LLog.d("map startLocate");
        }else{
            LLog.d("not STARTED");
        }

    }

    public void stopLocate(){
        LLog.d("map stopLocate");
    }
}
