package com.atguigu.interviewQ2;

import java.util.concurrent.*;

/**
 * 第三种实现线程的方式 实现 callable接口
 * Callable接口，是一种让线程执行完成后，能够返回结果的
 * FutureTask + Callable 实现
 *
 * @author ly
 * @since 2026/4/24 18:31
 */
public class CallableDemo{

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // FutureTask + Callable 实现
        FutureTask<Integer> futureTask = new FutureTask<>(new MyThread());

        new Thread(futureTask,"A").start();

        new Thread(futureTask,"B").start();//多个线程执行 一个FutureTask的时候，只会计算一次

        // 利用自旋锁理论,等待FutureTask完成
//        while (!futureTask.isDone()){
//            System.out.println("等待FutureTask任务完成");
//            try { TimeUnit.SECONDS.sleep(1); }catch (InterruptedException e){ e.printStackTrace(); }
//        }

        int result1 = 100;
        int reuslt2 = futureTask.get();
        System.out.println("FutureTask result: "+reuslt2);

        System.out.println("****** result: "+ (result1+reuslt2));

        Executors.newSingleThreadExecutor();
        Executors.newFixedThreadPool(2);
        Executors.newCachedThreadPool();
    }
}

class MyThread implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        System.out.println(Thread.currentThread().getName() + " come in Callable");
        TimeUnit.SECONDS.sleep(2);
        return 1024;
    }
}
