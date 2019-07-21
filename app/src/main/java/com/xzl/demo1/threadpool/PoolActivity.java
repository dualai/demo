package com.xzl.demo1.threadpool;

import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class PoolActivity extends AppCompatActivity {

    ScheduledExecutorService ssPool = new ScheduledThreadPoolExecutor(5);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ssPool.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                Log.d("test","thread:"+isMainThread()); //子线程
            }
        },1000,500, TimeUnit.MILLISECONDS);

        runUiOnSubThread();
    }

    private void runUiOnSubThread(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(PoolActivity.this,"你好",Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }).start();
    }

    public boolean isMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }
}
