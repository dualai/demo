package com.xzl.demo1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

/**
 * Created by wuyuanteng on 2018/8/26.
 * wuyuanteng@zailingtech.com
 */

public class FutureUtil {
    private final List<Future> futureList = new ArrayList<>();

    public synchronized boolean isEmpty(){
        return futureList.isEmpty();
    }

    public synchronized void add(Future future){
        futureList.add(future);
    }

    public synchronized void clear(){
        ListUtil.foreach(futureList, (future, i)->future.cancel(false));//确保任务完整执行，保证ThreadManager不可重入逻辑的完整性
        futureList.clear();
    }
}
