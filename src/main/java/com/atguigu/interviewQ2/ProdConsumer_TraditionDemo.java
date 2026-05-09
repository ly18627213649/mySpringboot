package com.atguigu.interviewQ2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * 传统版本模拟
 * 生成者,消费者,操作资源类
 *
 * 题目: 一个初始值为零的变量,两个线程对其交替操作,一个加1一个减1,来5轮
 *
 * 1.线程  操作(方法) 资源来
 * 2.判断   干活     通知
 * 3.防止虚假唤醒机制(不能用if,一定要用 while 可以保证线程在醒来后重新检查条件)
 *
 * 最原始: sync -- wait -- notify
 * 传统版: lock -- await -- singal
 */
public class ProdConsumer_TraditionDemo {
    // 测试,消费,生成,一个加一个减(来5轮),没有生产不能消费,没有消费不能生产
    public static void main(String[] args) {

        ShareData shareData = new ShareData();

        new Thread(() -> {
            for (int i = 1; i <= 5 ; i++) {
                try {
                    shareData.increment();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, "生成线程: ").start();

        new Thread(() -> {
            for (int i = 1; i <=5 ; i++) {
                try {
                    shareData.decrement();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, "消费线程: ").start();
    }
}



/**
 * 资源类
 */
class ShareData{
    private int number = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    /**
     * 加1
     */
    public void increment(){
        lock.lock();
        try {
            // 1.判断
            while (number != 0){
                // 等待,不能生产
                condition.await();
            }
            // 2.干活
            number++;
            System.out.println(Thread.currentThread().getName()+"\t"+number);

            // 3.通知唤醒
            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            lock.unlock();
        }
    }

    /**
     * 减1
     */
    public void decrement(){
        lock.lock();
        try {
            // 1.判断
            while (number == 0){
                // 等待,不能消费
                condition.await();
            }
            // 2.干活
            number--;
            System.out.println(Thread.currentThread().getName()+"\t"+number);

            // 3.通知唤醒
            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            lock.unlock();
        }
    }
}
