package com.atguigu.interviewQ2;

import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

/**
 * 死锁Demo
 *
 * 概念: 死锁是指两个或两个以上的进程在执行过程中，因争夺资源而造成的一种互相等待的现象
 * 条件: 1.互斥，线程使用的资源至少有一个不能共享的。
 *      2.持有并等待,至少有一个线程必须持有一个资源且正在等待获取一个当前被别的线程持有的资源。
 *      3.不可剥夺,资源不能被抢占。
 *      4.循环等待。
 * 解决: 破坏其中一个条件,互斥破坏不了
 *
 * 检查: jps -l 命令定位进程号, jstack 查看线程堆栈
 *
 */
class  MyTask implements Runnable {

    private Object resourceA, resourceB;



    public MyTask(Object resourceA,Object resourceB) {
        this.resourceA = resourceA;
        this.resourceB = resourceB;
    }

    @Override
    public void run() {

        // 死锁代码
        synchronized (resourceA){

            System.out.println(String.format("%s 自己持有%s，尝试持有%s",//
                    Thread.currentThread().getName(), resourceA, resourceB));

            try { TimeUnit.SECONDS.sleep(2); }catch (InterruptedException e){ e.printStackTrace(); }

            synchronized (resourceB){
                System.out.println(String.format("%s 同时持有%s，%s",//
                        Thread.currentThread().getName(), resourceA, resourceB));
            }
        }
    }
}

// 死锁测试
public class DeadLockDemo{

    public static void main(String[] args) {
        Object resourceA = new Object();
        Object resourceB = new Object();

        // 死锁
        new Thread(new MyTask(resourceA,resourceB), "AAA").start();
        new Thread(new MyTask(resourceB,resourceA), "BBB").start();

        // 破解: 避免循环等待,按固定顺序
//        new Thread(new MyTask(resourceA,resourceB), "AAA").start();
//        new Thread(new MyTask(resourceA,resourceB), "BBB").start();
    }

}
