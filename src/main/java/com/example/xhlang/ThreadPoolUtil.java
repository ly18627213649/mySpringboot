package com.example.xhlang;

import com.example.xhlang.task.ITask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 *  线程工具类
 *
 *  用法: 测试文件thread有例子
 */
public final class ThreadPoolUtil {

    protected static Logger log = LoggerFactory.getLogger(ThreadPoolUtil.class);
    private static ExecutorService service;
    private static final int TESTING_INTERVAL = 2;

    public ThreadPoolUtil() {
    }

    /**
     * 添加任务1
     * @param task
     */
    public static void execute(ITask task) {
        service.execute((Runnable)task);
    }

    /**
     * 添加任务2
     * @param task
     * @return
     */
    public static Future submit(ITask task) {
        return service.submit((Runnable)task);
    }

    static {
        service = new ThreadPoolExecutor(5, 10, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue());
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            service.shutdown();

            while(true) {
                try {
                    boolean isClose = service.awaitTermination(2L, TimeUnit.SECONDS);
                    if (isClose) {
                        break;
                    }

                    log.debug(String.format("线程池还未关闭，请等待任务结束！"));
                } catch (InterruptedException var1) {
                    log.error(String.format("进行线程池关闭检测时出错：%s", var1));
                }
            }

            log.debug(String.format("线程任务已结束，关闭线程池！"));
        }));
    }
}
