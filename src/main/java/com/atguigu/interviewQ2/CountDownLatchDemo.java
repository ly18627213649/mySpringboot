package com.atguigu.interviewQ2;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatchDemo 倒计时 学习
 * @author ly
 * @since 2026/4/23 16:58
 *
 * CountDownLatch  它允许一个或多个线程等待其他线程完成一组操作
 */
public class CountDownLatchDemo {

//    public static void main(String[] args) {
//        for (int i = 1; i <=6 ; i++) {
//            final int item = i;
//            new Thread(() -> {
//                    System.out.println(Thread.currentThread().getName()+"上完自习走人");
//
//            }, item+"").start();
//        }
//
//        System.out.println("班长上完自习走人");
//    }

    // 这时，不能保证班长最后走人，为了保证班长最后走，
    // 即： 让一线程阻塞直到另一组线程完成一系列操作后，计数器达到0后才被唤醒
    public static void main(String[] args) throws InterruptedException {
        // 计数器
        CountDownLatch countDownLatch = new CountDownLatch(6);

        for (int i = 1; i <=6 ; i++) {
            final int item = i;
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName()+"上完自习走人");
                // 做减法：其它线程调用countDown()会将计数器减1 (调用countDown方法的线程不会阻塞)，
                countDownLatch.countDown();
            }, item+"").start();
        }

        // 调用线程会被阻塞,当计数器的值变为零时，阻塞的线程会被唤醒，最后执行
        countDownLatch.await();

        System.out.println("其它同学都走完了，班长上完自习走人");
    }
}

