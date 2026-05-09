package com.atguigu.interviewQ2;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 自旋锁学习，代码验证
 * @author ly
 * @since 2026/4/23 15:06
 */
public class SpinLockDemo {

    AtomicReference<Thread> atomicReference = new AtomicReference<>();

    // 占用锁
    public void myLock(){

        Thread thread = Thread.currentThread();
        System.out.println(Thread.currentThread().getName()+ " com in mylock");
        // 开始自旋
        while (!atomicReference.compareAndSet(null, thread)) {
            System.out.println(Thread.currentThread().getName()+ " 通过自旋循环等待");
        }
    }

    // 释放锁
    public void myUnlock(){
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread,null);
        System.out.println(Thread.currentThread().getName()+" invoked myUnlock");
    }

    public static void main(String[] args) {
        SpinLockDemo spinLockDemo = new SpinLockDemo();
        new Thread(() -> {
            spinLockDemo.myLock();

            // 让当前线程休眠
            try { TimeUnit.SECONDS.sleep(5); }catch (InterruptedException e){ e.printStackTrace(); }

            spinLockDemo.myUnlock();
        },"interviewQ1").start();

        // 让当前线程休眠
        try { TimeUnit.SECONDS.sleep(1); }catch (InterruptedException e){ e.printStackTrace(); }

        new Thread(() -> {
            spinLockDemo.myLock();

            spinLockDemo.myUnlock();
        },"bb").start();
    }



}
