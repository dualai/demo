package com.xzl.demo1.rxjava;

import com.xzl.demo1.LLog;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xuluming on 2019/5/25
 */
public class FlatTest2_Login extends BaseTest {
    @Override
    protected void test() {
        LLog.d("=====================start===========================");
        Observable.create(new ObservableOnSubscribe<RegisterBean>() {
            @Override
            public void subscribe(ObservableEmitter<RegisterBean> emitter) throws Exception {
                LLog.d("注册的io请求：" + Thread.currentThread());
                RegisterBean bean = new RegisterBean();
                bean.code = 1;
                bean.message = "注册成功";
                emitter.onNext(bean);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<RegisterBean>() {
                    @Override
                    public void accept(RegisterBean registerBean) throws Exception {
                        LLog.d("在UI上处理注册结果...:" + Thread.currentThread());
                        if (registerBean.code != 0) {
                            LLog.d("注册失败...");
                        }
                    }
                }).observeOn(Schedulers.io())
                .filter(new Predicate<RegisterBean>() {
                    @Override
                    public boolean test(RegisterBean registerBean) throws Exception {
                        LLog.d("filter...");
                        return registerBean.code == 0;
                    }
                })
                .flatMap(new Function<RegisterBean, ObservableSource<LoginBean>>() {
                    @Override
                    public ObservableSource<LoginBean> apply(RegisterBean registerBean) throws Exception {
                        LLog.d("上一步注册的结果,code:" + registerBean.code + " msg:" + registerBean.message);
                        if (registerBean.code == 0) {
                            LLog.d("进行登录请求...");
                            LoginBean loginBean = new LoginBean();
                            loginBean.code = 0;
                            loginBean.data = "登陆成功";
                            return Observable.just(loginBean);
                        } else {
                            return Observable.error(new Throwable("注册失败,终止..."));
                        }
                    }
                })
                .subscribe(new Observer<LoginBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(LoginBean loginBean) {
                        LLog.d("获取登录结果:" + loginBean.data);
                    }

                    @Override
                    public void onError(Throwable e) {
                        LLog.e(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                })
        ;

    }


    private final static class RegisterBean {
        private int code;
        private String message;
    }

    private final static class LoginBean {
        private int code;
        private String data;
    }

}
