package com.lty.www.intel_ta_dsp.utils;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
public class RedisLockUtil {

    private final StringRedisTemplate redisTemplate;

    public RedisLockUtil(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 获取锁
     * @param key 锁的key
     * @param expireSeconds 过期时间（秒）
     * @return 唯一标识（用于释放锁时验证）
     */
    public String tryLock(String key, long expireSeconds) {
        String lockValue = UUID.randomUUID().toString();
        Boolean success = redisTemplate.opsForValue()
                .setIfAbsent(key, lockValue, expireSeconds, TimeUnit.SECONDS);
        return Boolean.TRUE.equals(success) ? lockValue : null;
    }

    /**
     * 释放锁
     * @param key 锁的key
     * @param value 加锁时返回的唯一标识
     */
    public void unlock(String key, String value) {
        try {
            String currentValue = redisTemplate.opsForValue().get(key);
            if (value.equals(currentValue)) {
                redisTemplate.delete(key);
            }
        } catch (Exception e) {
            // 日志记录异常，避免影响主流程
            System.err.println("释放锁失败：" + e.getMessage());
        }
    }
}
