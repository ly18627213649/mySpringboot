package com.atguigu.interviewQ2;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁 demo
 *
 * synchronized , ReentrantLock 是独享锁
 *
 * 多个线程同时读一个资源没有任何问题，所以为了满足并发量，读取共享资源应该可以同时进行
 * 但是
 * 如果有一个线程想去写共享资源来，就不应该再有其它线程可以对该资源进行读或写，即：写操作独占，互斥，不能被加塞
 * 小总结：
 *      读-读能共存
 *      读-写不能共存
 *      写-写不能共存
 *
 * ReentrantReadWriteLock其读锁是共享锁，其写锁是独占锁
 * 读锁的共享锁可保证并发读是非常高效的，读写，写读，写写的过程是互斥的。
 * 读写锁有三个重要特性:
 * 1.公平选择性: 支持非公平(默认)和公平,吞吐量还是非公平优先于公平;
 * 2.重入: 读锁和写锁都支持线程重入;
 * 3.锁降级:遵循获取写锁,获取读锁在释放写锁的持续,写锁能够降级成为读锁.
 */
public class ReentrantReadWriteLockDemo {

    // 共享资源类
    public volatile Map<String,Object> map = new HashMap<>();

    // 使用读写锁(保证写必须原子，读可共享)
    public ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public void put(String key,String value){

        // 加锁情况
        lock.writeLock().lock(); // 写锁
        try {
            System.out.println(Thread.currentThread().getName()+" 正在写入:"+ key);
            // 让它多写一会
            try { TimeUnit.SECONDS.sleep(2); }catch (InterruptedException e){ e.printStackTrace(); }
            map.put(key,value);
            System.out.println(Thread.currentThread().getName()+" 写入完成");
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            lock.writeLock().unlock();
        }

        // 没有锁情况
//        System.out.println(Thread.currentThread().getName()+" 正在写入:"+ key);
//        // 让它多写一会
//        try { TimeUnit.SECONDS.sleep(2); }catch (InterruptedException e){ e.printStackTrace(); }
//        map.put(key,value);
//        System.out.println(Thread.currentThread().getName()+" 写入完成");
    }

    public void get(String key){

        lock.readLock().lock(); // 读锁
        try {
            System.out.println(Thread.currentThread().getName() + " 正在读取:");
            try { TimeUnit.SECONDS.sleep(2); }catch (InterruptedException e){ e.printStackTrace(); }
            Object result = map.get(key);
            System.out.println(Thread.currentThread().getName()+" 读完成，结果："+result);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            lock.readLock().unlock();
        }

        // 没有锁情况
//        System.out.println(Thread.currentThread().getName() + " 正在读取:");
//        try { TimeUnit.SECONDS.sleep(2); }catch (InterruptedException e){ e.printStackTrace(); }
//        Object result = map.get(key);
//        System.out.println(Thread.currentThread().getName()+" 读完成，结果："+result);
    }


    public static void main(String[] args) {
        ReentrantReadWriteLockDemo readWriteLockDemo = new ReentrantReadWriteLockDemo();

        // 模拟线程操作资源类，5个线程写
        for (int i = 0; i <= 5; i++) {
            final int itemInt = i;
            new Thread(() -> {
                readWriteLockDemo.put(itemInt+"",itemInt+"");
            },itemInt+"").start();
        }

        // 模拟线程操作资源类， 5个线程读
        for (int i = 0; i <= 5; i++) {
            final int tempInt = i;
            new Thread(() -> {
                readWriteLockDemo.get(tempInt + "");
            }, String.valueOf(i)).start();
        }

        //没用锁的时候，从这个例子可以看出，写的时候，还没写完，就被打断了，读出来都是null;
        //所以我们要用读写锁 ReentrantReadWriteLock
    }
}
