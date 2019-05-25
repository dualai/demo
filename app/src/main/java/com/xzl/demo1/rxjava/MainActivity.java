package com.xzl.demo1.rxjava;

import android.app.Activity;
import android.os.Bundle;

import com.xzl.demo1.LLog;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xuluming on 2019/5/24
 */
public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("连载1");
                emitter.onNext("连载2");
                emitter.onNext("连载3");
                emitter.onComplete();
            }
        }).observeOn(AndroidSchedulers.mainThread()) // 回调在主线程
        .subscribeOn(Schedulers.io()) //执行在子线程
        .subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                LLog.d("onSubscribe:"+Thread.currentThread().toString());
            }

            @Override
            public void onNext(String s) {
                LLog.d("onNext:"+s);
            }

            @Override
            public void onError(Throwable e) {
                LLog.d("onError:"+Thread.currentThread().toString());
            }

            @Override
            public void onComplete() {
                LLog.d("onComplete:"+Thread.currentThread().toString());
            }
        })
        ;

//        Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
//                emitter.onNext("连载1");
//                emitter.onNext("连载2");
//                emitter.onNext("连载3");
//                emitter.onComplete();
//            }
//        })
//                .observeOn(AndroidSchedulers.mainThread())//回调在主线程
//                .subscribeOn(Schedulers.io())//执行在io线程
//                .subscribe(new Observer<String>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        Log.e(TAG,"onSubscribe");
//                    }
//
//                    @Override
//                    public void onNext(String value) {
//                        Log.e(TAG,"onNext:"+value);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.e(TAG,"onError="+e.getMessage());
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.e(TAG,"onComplete()");
//                    }
//                });


    }
}
