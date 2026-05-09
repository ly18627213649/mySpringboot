package com.atguigu.interviewQ2;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * SemaphoreDemo 信号量 学习
 * @author ly
 * @since 2026/4/23 18:09
 *
 *  信号量主要用于：
 *  1.多个共享资源的互斥使用， 如：6个车抢3个车位
 *  2.用于并发线程数的控制（限流），如接口限流，数据库连接池限流
 *
 * 总结: 是"计数许可"概念, , 控制并发访问数量, 只有计数器+等待线程, 典型场景: 限流（最多N个线程同时访问）
 *
 */
public class SemaphoreDemo {

    public static void main(String[] args) {

        Semaphore semaphore = new Semaphore(3); // 模拟3个停车位
        // 模拟6部汽车
        for (int i = 1; i <=6 ; i++) {
            new Thread(() -> {
                try {
                    // 抢占, 代表一辆车，已经占用了该车位s
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName()+" 抢到了车位");
                    // 每个车停了3秒钟
                    try { TimeUnit.SECONDS.sleep(3); }catch (InterruptedException e){ e.printStackTrace(); }
                    System.out.println(Thread.currentThread().getName()+" 停车3秒后离开车位");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    // 释放停车位
                    semaphore.release();
                }
            }, String.valueOf(i)).start();

        }
    }
}
