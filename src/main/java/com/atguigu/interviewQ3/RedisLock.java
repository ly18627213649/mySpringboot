package com.atguigu.interviewQ3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Redis 做分布式锁 (手写版本)
 *
 * @author ly
 * @since 2026/4/29 16:14
 */
public class RedisLock {

    @Autowired
    private StringRedisTemplate redisTemplate;

    // 预编译Lua脚本（性能优化，避免每次执行都发送脚本）
    private static final DefaultRedisScript<Long> UNLOCK_SCRIPT;

    static {
        UNLOCK_SCRIPT = new DefaultRedisScript<>();
        // Lua脚本 关键：判断value是否匹配，匹配才删除（原子操作）
        UNLOCK_SCRIPT.setScriptText(
                "if redis.call('get', KEYS[1]) == ARGV[1] then " +
                        "    return redis.call('del', KEYS[1]) " +
                        "else " +
                        "    return 0 " +
                        "end"
        );
        UNLOCK_SCRIPT.setResultType(Long.class);
    }

    /**
     * 尝试加锁（非阻塞，立即返回）
     * @param key 锁的key
     * @param value 唯一标识（通常用UUID）
     * @param expireSeconds 锁过期时间（秒）
     * @return 是否加锁成功
     */
    public boolean tryLock(String key, String value, long expireSeconds) {
        Boolean success = redisTemplate.opsForValue().setIfAbsent(key, value, expireSeconds, TimeUnit.SECONDS);
        return Boolean.TRUE.equals(success);
    }

    /**
     * 释放锁（必须使用Lua脚本保证原子性）
     * @param key 锁的key
     * @param value 唯一标识（必须和加锁时的value一致）
     * @return 是否释放成功
     */
    public boolean unlock(String key, String value) {
        // 使用预编译的Lua脚本执行原子操作
        Long result = redisTemplate.execute(
                UNLOCK_SCRIPT,
                Collections.singletonList(key),
                value
        );
        return result != null && result == 1;
    }

    /**
     * 自动管理锁的模板方法（推荐使用这个）
     * @param key 锁的key
     * @param expireSeconds 锁过期时间（秒）
     * @param task 需要加锁执行的任务
     * @return 是否成功执行任务
     */
    public boolean executeWithLock(String key, long expireSeconds, Runnable task) {
        String requestId = UUID.randomUUID().toString();

        // 1. 获取锁
        if (!tryLock(key, requestId, expireSeconds)) {
            return false;
        }

        // 2. 执行任务 + 3. 释放锁（用finally保证一定释放）
        try {
            task.run();
            return true;
        } finally {
            unlock(key, requestId);  // 这里内部使用了Lua脚本
        }
    }
}
