package com.example.demo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
// 线程池
public class ThreadPool {

        public static void main(String[] args) {
            ExecutorService executorService = Executors.newFixedThreadPool(1);
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.print("悟空是个猴子");
                }
            });
        }

}
