package com.atguigu.interviewQ2;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier 循环屏障 学习
 * @author ly
 * @since 2026/4/23 17:33
 *
 * CyclicBarrier 它允许一组线程相互等待，直到所有线程都到达一个共同的屏障点。
 *
 * CountDownLatch 与 CyclicBarrier 区别:
 * CyclicBarrier 可以在屏障点被释放后再次使用，因此适用于多阶段使用的场景;
 * CyclicBarrier 用于所有线程必须同时到达屏障位置,才能继续执行;
 * CountDownLatch用于等待事件,CyclicBarrier用于等待其它线程;
 */
public class CyclicBarrierDemo {

    public static void main(String[] args) {
        // 定义一个可循环的屏障
        // 参数1：在阻塞被触发前，必须调用的线程数
        // 参数2：多个线程达到屏障后需要执行的方法
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7, () -> {
            System.out.println("*********召唤神龙");
        });

        for (int i = 1; i <= 7 ; i++) {
            final int tempInt = i;
            new Thread(() -> {
                try {

                    System.out.println("第一阶段"+ Thread.currentThread().getName()+" 收集到第"+tempInt+"颗龙珠");

                    // 做加法: 先到的线程被阻塞（进入屏障）
                    // 等全部线程达到屏障后，屏障才会开门，才能执行cyclicBarrier定义的方法
                    // 所有被屏障拦截的线程才继续干活
                    cyclicBarrier.await();

                    System.out.println("打开屏障后，线程"+tempInt+" 继续执行。。。。");
                    System.out.println("第二阶段"+ Thread.currentThread().getName()+" 收集到第"+tempInt+"颗龙珠");
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }, tempInt+"").start();
        }
    }
}
