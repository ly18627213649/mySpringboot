package com.atguigu.interviewQ2;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * 阻塞队列 学习
 * @author ly
 * @since 2026/4/23 20:12
 *
 * 阻塞队列：
 *      1.阻塞队列有没有好的一面
 *      2.不得不阻塞时，如何管理
 * 好处：
 * 在多线程领域：所谓阻塞，在某些情况下会挂起线程（即阻塞）,一旦条件满足，被挂起的线程又会自动被唤醒。
 *
 * 为什么需要BlockingQueue
 * 好处是我们不需要关心什么时候需要阻塞线程，什么时候需要唤醒线程，因为这一切BlockingQueue都给你一手包办了
 *
 * 在Concurrent包发布以前，在多线程环境下，我们每个程序员都必须去自己控制这些细节，尤其还要兼顾效率和线程安全，而这会给我们的程序带来不小的复杂度。
 *
 * 阻塞队列总结: 容器概念,存储+流转数据,内部有真实容器存储元素,典型场景: 生产-消费模式
 *
 * BlockingQueue -->
 *      ArrayBlockingQueue: 数组结构组成的有界阻塞队列
 *      LinkedBlockingQueue：有链表结构组成的有界阻塞队列,(但是有界的默认值为：Integer.MAX_VALUE),非常大
 *      SynchronousQueue: 不存储元素的阻塞队列,也即单个元素的队列,属于专属定制版
 *      PriorityBlockingQueue: 支持优先级排序的无界阻塞队列
 *      DelayQueue: 使用优先级队列实现的延迟无界阻塞队列
 *      LinkedTransferQueue: 由链表结构组成的无界阻塞队列
 *      LinkedBlockingDeque: 由链表结构组成的双向阻塞队列
 *
 */
public class BlockingQueueDemo {

    public static void main(String[] args) {
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);
        // -------第一组,报异常的
        // 当阻塞队列满时：在往队列中add插入元素会抛出 IIIegalStateException：Queue full
        // 当阻塞队列空时：再往队列中remove移除元素，会抛出NoSuchException
        // 插入
        System.out.println(blockingQueue.add("a"));
        System.out.println(blockingQueue.add("b"));
        System.out.println(blockingQueue.add("c"));

        // 检查
        System.out.println( blockingQueue.element());

        // 移除
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());

        // -------第2组,返回booer值,成功true,失败false
        BlockingQueue<String> blockingQueue2 = new ArrayBlockingQueue<>(3);
        // 插入
        System.out.println(blockingQueue2.offer("a"));
        System.out.println(blockingQueue2.offer("b"));
        System.out.println(blockingQueue2.offer("c"));

        // 检查
        System.out.println( blockingQueue2.peek());

        // 移除
        System.out.println(blockingQueue2.poll());
        System.out.println(blockingQueue2.poll());
        System.out.println(blockingQueue2.poll());
        System.out.println(blockingQueue2.poll());

        // -------第3组,阻塞型
        //当阻塞队列满时，生产者继续往队列里put元素，队列会一直阻塞生产线程直到put数据or响应中断退出。
        //当阻塞队列空时，消费者线程试图从队列里take元素，队列会一直阻塞消费者线程直到队列可用。
        BlockingQueue<String> blockingQueue3 = new ArrayBlockingQueue<>(3);
        // 插入
        try {
            // 添加
            blockingQueue3.put("a");
            blockingQueue3.put("b");
            blockingQueue3.put("b");

            // 移除
            System.out.println(blockingQueue3.take());

            // 没有检查
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        // -------第4组,超时控制
        // 阻塞队列满时，队里会阻塞生产者线程一定时间，超过限时后生产者线程会退出
        BlockingQueue<String> blockingQueue4 = new ArrayBlockingQueue<>(3);
        try {
            // 插入
            System.out.println(blockingQueue4.offer("a", 2L,TimeUnit.SECONDS));
            System.out.println(blockingQueue4.offer("b", 2L,TimeUnit.SECONDS));
            System.out.println(blockingQueue4.offer("c", 2L,TimeUnit.SECONDS));
            System.out.println("超时阻塞:" + blockingQueue4.offer("d", 2L,TimeUnit.SECONDS));

            // 移除
            System.out.println(blockingQueue4.poll(2L,TimeUnit.SECONDS));
            System.out.println(blockingQueue4.poll(2L,TimeUnit.SECONDS));
            System.out.println(blockingQueue4.poll(2L,TimeUnit.SECONDS));
            System.out.println("超时阻塞:" + blockingQueue4.poll(2L,TimeUnit.SECONDS));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

/**
 * 阻塞队列 之 同步队列
 */
class synchronousQueueDemo{
    public static void main(String[] args) {
        // 不存储元素的同步队列,搭配put(),task()
        // 从下面例子可以看出,生产一个消费一个,不消费就阻塞生产
        BlockingQueue<String> blockingQueue = new SynchronousQueue<>();

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName()+" put 1");
            try {
                blockingQueue.put("1");
                System.out.println(Thread.currentThread().getName()+" put 2");
                blockingQueue.put("2");
                System.out.println(Thread.currentThread().getName()+" put 3");
                blockingQueue.put("3");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "生成者生产: ").start();

        new Thread(() -> {
            try {
                // 等一会消费一个
                try { TimeUnit.SECONDS.sleep(3); }catch (InterruptedException e){ e.printStackTrace(); }
                System.out.println(Thread.currentThread().getName() +  blockingQueue.take());
                // 等一会消费一个
                try { TimeUnit.SECONDS.sleep(3); }catch (InterruptedException e){ e.printStackTrace(); }
                System.out.println(Thread.currentThread().getName() +  blockingQueue.remove());
                // 等一会消费一个
                try { TimeUnit.SECONDS.sleep(3); }catch (InterruptedException e){ e.printStackTrace(); }
                System.out.println(Thread.currentThread().getName() +  blockingQueue.remove());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "消费者消费: ").start();
    }
}
