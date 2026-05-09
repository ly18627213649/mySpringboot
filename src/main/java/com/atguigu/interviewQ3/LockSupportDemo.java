package com.atguigu.interviewQ3;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * LockSupport 学习
 * @author ly
 * @since 2026/4/28 16:58
 *
 * 阻塞: 消耗凭证 permit=0
 * 释放唤醒: permit=1 增加一个凭证，但凭证最多只能有1个，累加无放
 * LockSupport.park() -- LockSupport.unpark();
 *
 * LockSupport不需要在同步块里面,
 * LockSupport无视顺序,唤醒不一定要在阻塞后面,先唤醒也可以,只要保证具体的唤醒线程
 */
public class LockSupportDemo {

    public static void main(String[] args) {
        Thread a = new Thread(()->{
//			try {
//				TimeUnit.SECONDS.sleep(2);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
            System.out.println(Thread.currentThread().getName() + " come in. " + System.currentTimeMillis());
            LockSupport.park();
            System.out.println(Thread.currentThread().getName() + " 换醒. " + System.currentTimeMillis());
        }, "Thread A");
        a.start();

        Thread b = new Thread(()->{
            try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
            LockSupport.unpark(a);
            System.out.println(Thread.currentThread().getName()+" 通知.");
        }, "Thread B");
        b.start();
    }
}
