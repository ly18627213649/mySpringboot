package com.atguigu.interviewQ2;


import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 阻塞队列版本
 * 生成者,消费者,操作资源类
 *
 * valatile/CAS/atomicInteger/BlockQueue/线程交互/原子引用
 */
public class ProdConsumer_BlockQueueDemo {

    public static void main(String[] args) {

        // 传入具体的阻塞队列实现类,ArrayBlockingQueue
        MyResouce myResouce = new MyResouce(new ArrayBlockingQueue<String>(10));

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName()+"\t"+"生产线程启动");

            try {
                // 生产线程开始生产
                myResouce.myProd();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }, "生产线程").start();


        new Thread(() -> {
            System.out.println(Thread.currentThread().getName()+"\t"+"消费线程启动");

            try {
                // 消费线程开始消费
                myResouce.myCustom();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "消费线程").start();


        // 暂停一会线程
        try { TimeUnit.SECONDS.sleep(5); }catch (InterruptedException e){ e.printStackTrace(); }

        System.out.println();

        System.out.println("5秒钟时间到,大老板main线程叫停,活动结束");
        myResouce.stop();
    }
}

/**
 * 资源类
 */
class MyResouce{

    //默认开启,进行生成+消费
    // 这里用到了volatile是为了保持数据的可见性，也就是当TLAG修改时，要马上通知其它线程进行修改
    private volatile boolean FLAG = true;

    // 原子类,代替传统版本i++
    private AtomicInteger atomicInteger = new AtomicInteger();

    // 阻塞队列接口,保证可扩展性
    BlockingQueue<String> blockingQueue = null;

    // 构造注入具体的阻塞队列实现类
    public MyResouce(BlockingQueue<String> blockingQueue){
        this.blockingQueue = blockingQueue;
        // 把传入的基础类打印出来,帮助日志排查问题
        System.out.println(blockingQueue.getClass().getName());
    }

    /**
     * 生产操作
     */
    public void myProd() throws Exception{

        String data = null;
        boolean retValue;

        // 当FLAG为true的时候，开始生产
        // 多线程环境判断,一定要用while防止线程虚假唤醒
        while (FLAG){
            data = atomicInteger.incrementAndGet()+"";

            // 插入队列
            retValue = blockingQueue.offer(data, 2L, TimeUnit.SECONDS);

            if (retValue){
                System.out.println(Thread.currentThread().getName()+"\t"+"插入"+data+"成功");
            }else{
                System.out.println(Thread.currentThread().getName()+"\t"+"插入"+data+"失败");
            }

            // 生产一个,歇一会
            try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e){ e.printStackTrace(); }
        }

        System.out.println(Thread.currentThread().getName()+"\t"+"大老板叫停了,表示FLAG=false，生产结束");
    }

    /**
     * 消费操作
     */
    public void myCustom() throws Exception{

        String result;

        // 当FLAG为true的时候，开始消费
        // 多线程环境判断,一定要用while防止线程虚假唤醒
        while (FLAG){

            // 取出队列
            result = blockingQueue.poll(2L, TimeUnit.SECONDS);

            if (StringUtils.isNotBlank(result)){
                System.out.println(Thread.currentThread().getName()+"\t"+"消费"+result+"成功");
            } else{
                FLAG = false;
                System.out.println(Thread.currentThread().getName()+"\t 消费失败，队列中已为空，退出");

                // 退出消费队列
                return;
            }
        }
    }

    /**
     * 停止生产
     */
    public void stop(){
        this.FLAG = false;
    }
}
