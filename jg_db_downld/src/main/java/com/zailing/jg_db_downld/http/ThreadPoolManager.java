package com.zailing.jg_db_downld.http;

import android.util.Log;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class ThreadPoolManager {
    private static final String TAG ="dongnao" ;
    private static  ThreadPoolManager instance=new ThreadPoolManager();

    private LinkedBlockingQueue<Future<?>> taskQuene=new LinkedBlockingQueue<>();

    private ThreadPoolExecutor threadPoolExecutor;
    public static ThreadPoolManager getInstance() {

        return instance;
    }
    private ThreadPoolManager()
    {
        threadPoolExecutor=new ThreadPoolExecutor(4,10,10, TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(4), handler);
        //直接执行 "创建队列与线程池的"交互"线程",可以直接交给线程池来执行
        threadPoolExecutor.execute(communicateThread);

        //
//        threadPoolExecutor.execute(delayThread);
    }

    public <T> boolean removeTask(FutureTask futureTask)
    {
        boolean result=false;
        if(taskQuene.contains(futureTask))
        {
            taskQuene.remove(futureTask);
        }else
        {
            result=threadPoolExecutor.remove(futureTask);
        }
        return  result;
    }

    // 创建延迟队列,DelayQueue 无边界的,重试队列;
//    private DelayQueue<HttpTask> mDelayQueue = new DelayQueue<>();

//    public void addDelayTask(HttpTask task) {
//        if (task != null) {
//            task.setDelayTime(3000);
//            mDelayQueue.put(task); //这里调用offer和调用put是一回事，因为DelayQueue是无界的
//        }
//    }
//
//    public Runnable delayThread = new Runnable() {
//        @Override
//        public void run() {
//            HttpTask task = null;
//            while (true) {
//                try {
//                    task = mDelayQueue.take();
//                    if (task.getRetryCount() < 3) {
//                        threadPoolExecutor.execute(task);
//                        task.setRetryCount(task.getRetryCount() + 1);
//                        Log.d(TAG, "重试机制 " + task.getRetryCount());
//                    } else {
//                        Log.d(TAG, "执行次数超限,放弃");
//                    }
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    };


    // 创建队列与线程池的"交互"线程
    public Runnable communicateThread = new Runnable() {
        @Override
        public void run() {
            //不断的获取
            while (true)
            {
                FutureTask futrueTask=null;
                try {
                    /**
                     * 阻塞式函数
                     */
                    Log.i(TAG,"等待队列     "+taskQuene.size());
                    futrueTask= (FutureTask) taskQuene.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(futrueTask!=null)
                {
                    threadPoolExecutor.execute(futrueTask);
                }
                Log.i(TAG,"线程池大小      "+threadPoolExecutor.getPoolSize());
            }
        }
    };


    public <T> void execte(FutureTask<T> futureTask) throws InterruptedException {
        taskQuene.put(futureTask);
    }

    //比如线程池突然间挂了，任务会被作为异常抛出来；拒绝就添加，重新排队
    private RejectedExecutionHandler handler=new RejectedExecutionHandler() {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            try {
                taskQuene.put(new FutureTask<Object>(r,null) {
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };
}
