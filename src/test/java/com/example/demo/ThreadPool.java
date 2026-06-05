package com.example.demo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
// 线程池
public class ThreadPool {

        public static void main(String[] args) {

            // 线程池还得自己手动构件,7大参数自己设定


            // Executors 方式创建线程池, 开发环境慎用
            // 1.固定线程,队列是LinkedBlockingQueue 相当于无界阻塞队列
            ExecutorService executorService = Executors.newFixedThreadPool(1);

            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.print("悟空是个猴子");
                }
            });

            // 2.创建单个线程,队列相当于是无界阻塞队列
            Executors.newSingleThreadExecutor();

            // 3.创建缓存线程池,非核心线程大小是Integer.MAX_VALUE,相当于无限大
            Executors.newCachedThreadPool();


        }

}
