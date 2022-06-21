package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 多线程+List分段-->完美解决java批量更新
 */
public class SplitList_Thread {

    private static final Logger log = LoggerFactory.getLogger(SplitList_Thread.class);

    /**
     * 拆分集合
     *
     * @param <T> 泛型对象
     * @param resList 需要拆分的集合
     * @param subListLength 每个子集合的元素个数
     * @return 返回拆分后的各个集合组成的列表
     * 代码里面用到了guava和common的结合工具类
     **/
    public static <T> List<List<T>> split(List<T> resList, int subListLength) {
        if (CollectionUtils.isEmpty(resList) || subListLength <= 0) {
            return new ArrayList<>();
        }
        List<List<T>> ret = new ArrayList<>();
        int size = resList.size();
        if (size <= subListLength) {
            // 数据量不足 subListLength 指定的大小
            ret.add(resList);
        } else {
            int pre = size / subListLength;
            int last = size % subListLength;
            // 前面pre个集合，每个大小都是 subListLength 个元素
            for (int i = 0; i < pre; i++) {
                List<T> itemList = new ArrayList<>();
                for (int j = 0; j < subListLength; j++) {
                    itemList.add(resList.get(i * subListLength + j));
                }
                ret.add(itemList);
            }
            // last的进行处理
            if (last > 0) {
                List<T> itemList = new ArrayList<>();
                for (int i = 0; i < last; i++) {
                    itemList.add(resList.get(pre * subListLength + i));
                }
                ret.add(itemList);
            }
        }
        return ret;
    }


    /**
     * 开启异步执行任务的线程池
     * @param totalList
     * @param <T>
     */
    public static <T> void threadMethod(List<T> totalList) {
        List<T> updateList = new ArrayList();
        // 初始化线程池, 参数一定要一定要一定要调好！！！！
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(20, 50,
                4, TimeUnit.SECONDS, new ArrayBlockingQueue(10), new ThreadPoolExecutor.AbortPolicy());
        // 大集合拆分成N个小集合, 这里集合的size可以稍微小一些（这里我用100刚刚好）, 以保证多线程异步执行, 过大容易回到单线程
        List<List<T>> splitNList = SplitList_Thread.split(totalList, 100);
        // 记录单个任务的执行次数
        CountDownLatch countDownLatch = new CountDownLatch(splitNList.size());
        // 对拆分的集合进行批量处理, 先拆分的集合, 再多线程执行
        for (List<T> singleList : splitNList) {
            // 线程池执行
            threadPool.execute(new Thread(new Runnable(){
                @Override
                public void run() {
                    for (T aa : singleList) {
                        // 将每一个对象进行数据封装, 并添加到一个用于存储更新数据的list
                        // ......

                    }
                }
            }));
            // 任务个数 - 1, 直至为0时唤醒await()
            countDownLatch.countDown();
        }
        try {
            // 让当前线程处于阻塞状态，直到锁存器计数为零
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException();
        }
        // 通过mybatis的批量插入的方式来进行数据的插入, 这一步还是要做判空
        if (!updateList.isEmpty()) {
            // TODO 批量更新
            log.info("xxxxxxxxxxxxxxx");
        }
    }
}
