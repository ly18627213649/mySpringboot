package com.atguigu.interviewQ3;

import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * Redisson 分布式锁工具类
 * 特性：
 * 1. 可重入性（同一个线程可以重复获取同一把锁）
 * 2. 自动续期（看门狗机制，默认30秒，每10秒续一次）
 * 3. 防止误删（Lua脚本保证原子性，只有锁持有者才能释放锁）
 *
 * 底层原理：Redis Hash 结构存储，key为锁名，field为"UUID:线程ID"，value为重入次数[citation:5][citation:6]
 *
 */
public class RedissonLockUtil {
    private static final Logger logger = LoggerFactory.getLogger(RedissonLockUtil.class);

    @Autowired
    private RedissonClient redissonClient;

    /**
     * 获取锁（阻塞，直到获取成功）
     * 内部会自动续期，不用担心业务执行超过锁过期时间
     *
     * @param lockKey 锁的key
     * @param task 需要执行的业务逻辑
     */
    public void lock(String lockKey, Runnable task) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock();  // 阻塞等待，自动续期（看门狗默认30秒）[citation:6][citation:10]
        try {
            task.run();
        } finally {
            unlockSafely(lock);
        }
    }

    /**
     * 获取锁（带返回值版本）
     */
    public <T> T lock(String lockKey, Supplier<T> supplier) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock();
        try {
            return supplier.get();
        } finally {
            unlockSafely(lock);
        }
    }

    /**
     * 尝试获取锁（非阻塞，立即返回结果）
     *
     * @param lockKey 锁的key
     * @param waitTime 等待获取锁的时间（超过则放弃）
     * @param leaseTime 锁自动释放时间（超过则强制释放，Redisson会关闭看门狗）
     * @param unit 时间单位
     * @param task 业务逻辑
     * @return 是否成功执行任务
     */
    public boolean tryLock(String lockKey, long waitTime, long leaseTime,
                           TimeUnit unit, Runnable task) {
        RLock lock = redissonClient.getLock(lockKey);
        boolean locked = false;
        try {
            locked = lock.tryLock(waitTime, leaseTime, unit);
            if (!locked) {
                logger.warn("获取锁失败，锁key: {}", lockKey);
                return false;
            }
            task.run();
            return true;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("获取锁被中断，锁key: {}", lockKey, e);
            return false;
        } finally {
            if (locked) {
                unlockSafely(lock);
            }
        }
    }

    /**
     * 安全释放锁
     * Redisson 内部已通过 Lua 脚本保证"检查持有者+删除锁"的原子性，无需手动写 Lua[citation:6]
     */
    private void unlockSafely(RLock lock) {
        try {
            // 只有当前线程持有锁时才释放，防止误删其他线程的锁
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        } catch (Exception e) {
            logger.error("释放锁异常", e);
        }
    }

    /**
     * 获取读写锁（读锁共享，写锁互斥）
     * 适用场景：读多写少的数据缓存
     */
    public RLock getReadWriteLock(String lockKey, boolean isWriteLock) {
        RReadWriteLock readWriteLock = redissonClient.getReadWriteLock(lockKey);
        return isWriteLock ? readWriteLock.writeLock() : readWriteLock.readLock();
    }

    /**
     * 获取公平锁（按请求顺序获取锁，避免线程饥饿）
     */
    public RLock getFairLock(String lockKey) {
        return redissonClient.getFairLock(lockKey);
    }
}
