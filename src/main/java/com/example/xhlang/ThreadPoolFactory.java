package com.example.xhlang;

import com.example.xhlang.factory.NameableThreadFactory;
import com.example.xhlang.task.ITask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.concurrent.*;

/**
 * 线程池factory
 *
 * 用法: 测试文件thread有例子
 */
public final class ThreadPoolFactory {

    protected static Logger log = LoggerFactory.getLogger(ThreadPoolFactory.class);
    private static final ConcurrentHashMap<String, ExecutorService> SERVICE_CONTAINER = new ConcurrentHashMap();
    private static final int MAX_AVAILABLE_RESOURCES = Runtime.getRuntime().availableProcessors() * 50;
    private static int FREE_AVAILABLE_RESOURCES;
    private static final int TESTING_INTERVAL = 2;

    public ThreadPoolFactory() {
    }

    /**
     * 执行线程
     * @param scope 线程描述
     * @param task  具体线程任务
     */
    public static void execute(String scope, ITask task) {
        ExecutorService service = getTheadPool(scope);
        service.execute((Runnable)task);
    }

    public static Future<?> submit(String scope, ITask task) {
        ExecutorService service = getTheadPool(scope);
        return service.submit((Runnable)task);
    }

    private static ExecutorService getTheadPool(String scope) {
        ExecutorService service = (ExecutorService)SERVICE_CONTAINER.get(scope);
        if (service == null) {
            throw new RuntimeException(String.format("未查询到此 scope 为 [%s] 的线程池，请先调用register()方法进行注册！", scope));
        } else {
            return service;
        }
    }

    /**
     * 注册线程
     */
    public static void register(String scope, int corePoolSize, int maximumPoolSize, long keepAliveTime) {
        Class var5 = ThreadPoolFactory.class;
        synchronized(ThreadPoolFactory.class) {
            if (FREE_AVAILABLE_RESOURCES <= 0) {
                log.warn(String.format("scope为[%s]无法创建新的线程池，已无可用资源，请调整线程池策略！", scope));
            } else {
                if (maximumPoolSize >= FREE_AVAILABLE_RESOURCES) {
                    maximumPoolSize = FREE_AVAILABLE_RESOURCES;
                    FREE_AVAILABLE_RESOURCES -= maximumPoolSize;
                }

                ExecutorService service = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, new LinkedBlockingQueue(), new NameableThreadFactory(scope));
                SERVICE_CONTAINER.put(scope, service);
            }
        }
    }

    static {
        FREE_AVAILABLE_RESOURCES = MAX_AVAILABLE_RESOURCES;
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (!SERVICE_CONTAINER.isEmpty()) {
                Iterator var0 = SERVICE_CONTAINER.values().iterator();

                ExecutorService service;
                while(var0.hasNext()) {
                    service = (ExecutorService)var0.next();
                    service.shutdown();
                }

                var0 = SERVICE_CONTAINER.values().iterator();

                label35:
                while(var0.hasNext()) {
                    service = (ExecutorService)var0.next();

                    while(true) {
                        while(true) {
                            try {
                                boolean isClose = service.awaitTermination(2L, TimeUnit.SECONDS);
                                if (isClose) {
                                    continue label35;
                                }

                                log.debug("线程池还未关闭，请等待任务结束！");
                            } catch (InterruptedException var3) {
                                log.error(String.format("进行线程池关闭检测时出错：%s", var3));
                            }
                        }
                    }
                }

                log.debug("线程任务已结束，关闭线程池！");
            }
        }));
    }
}
