package com.atguigu.interviewQ2;

import java.util.concurrent.*;

/**
 * 第4中获取线程方式: 使用java线程池
 */
public class MyThreadPoolDemo {


    public static void main(String[] args) {
        // 查看硬件参数,CPU核数,方便配置线程数参数做准备
        // CPU密集型: 核数+1
        // IO密集型: 参考公式: cup/(1-阻塞系数)  阻塞系数一般 0.8-0.9
        // core = 4/1-0.9 = 40
        System.out.println(Runtime.getRuntime().availableProcessors());

        ExecutorService threadPool = new ThreadPoolExecutor(
                2,
                5,
                1L,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy());

        try {
            // 模拟10个人去办业务,银行最大可容纳 5+3 = 8人,测试拒绝策略
            for (int i = 1; i <=10 ; i++) {
                threadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName()+"\t"+"办理业务");
                });
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            threadPool.shutdown();
        }
    }
}
