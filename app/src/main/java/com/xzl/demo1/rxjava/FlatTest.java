package com.xzl.demo1.rxjava;

import android.util.Log;

import com.xzl.demo1.LLog;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * Created by xuluming on 2019/5/25
 */
public class FlatTest extends BaseTest {

    public FlatTest() {

    }

    @Override
    protected void test() {
        List<Person> personList = new ArrayList<>();

        List<Plan> planList = new ArrayList<>();
        Plan plan0 = new Plan("", "猪头");
        Plan plan01 = new Plan("", "猪头2");
        List<String> actions = new ArrayList<>();
        actions.add("小明 action0");
        actions.add("小明 action1");
        actions.add("小明 action2");
        plan0.setActionList(actions);
        planList.add(plan0);
        planList.add(plan01);
        Person person0 = new Person("小明", planList);


        List<Plan> planList1 = new ArrayList<>();
        Plan plan1 = new Plan("", "dua");
        Plan plan11 = new Plan("", "dua1");
        List<String> actions1 = new ArrayList<>();
        actions1.add("小刚 action0");
        actions1.add("小刚 action1");
        actions1.add("小刚 action2");
        plan1.setActionList(actions1);

        planList1.add(plan1);
        planList1.add(plan11);
        Person person1 = new Person("小刚", planList1);

        personList.add(person0);
        personList.add(person1);


        Observable.fromIterable(personList)
                .flatMap(new Function<Person, ObservableSource<Plan>>() {
                    @Override
                    public ObservableSource<Plan> apply(Person person) throws Exception {
                        return Observable.fromIterable(person.getPlanList());
                    }
                })
                .flatMap(new Function<Plan, ObservableSource<String>>() { //依赖上一步
                    @Override
                    public ObservableSource<String> apply(Plan plan) throws Exception {
                        return Observable.fromIterable(plan.getActionList());
                    }
                })
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        d.dispose();
                    }

                    @Override
                    public void onNext(String s) {
                        LLog.d("action:" + s);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


        testConcat(personList);
    }


    private void testConcat(List<Person> personList) {
        Observable.fromIterable(personList)
                .concatMap(new Function < Person, ObservableSource < Plan >> () {
                    @Override
                    public ObservableSource < Plan > apply(Person person) {
                        if ("小明".equals(person.getName())) {
                            return Observable.fromIterable(person.getPlanList()).delay(3110, TimeUnit.MILLISECONDS);
                        }
                        return Observable.fromIterable(person.getPlanList());
                    }
                })
                .subscribe(new Observer < Plan > () {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Plan plan) {
                        LLog.d("==================plan " + plan.getContent());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
