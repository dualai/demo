package com.xzl.demo1.rxjava;

import com.xzl.demo1.LLog;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xuluming on 2019/5/24
 */
public class Demo1Test extends BaseTest {
    CompositeDisposable compositeDisposable;
    Disposable disposable;
    public Demo1Test() {
        super();
    }

    @Override
    protected void test() {
        compositeDisposable = new CompositeDisposable();

        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                LLog.d("thread:"+Thread.currentThread());
                emitter.onNext("abc");
            }
        }).subscribeOn(Schedulers.io())
        .subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                LLog.d(s);
                String a = null;
                a.trim();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                LLog.d("发生异常...");
            }
        });

    }

    private void stop(){
//        disposable.dispose();
        compositeDisposable.dispose();
        compositeDisposable.clear();
    }
}
