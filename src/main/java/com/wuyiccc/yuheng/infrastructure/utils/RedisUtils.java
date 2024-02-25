package com.wuyiccc.yuheng.infrastructure.utils;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.*;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * @author wuyiccc
 * @date 2023/6/24 16:25
 */
@Slf4j
@Component
public class RedisUtils {

    private static RedissonClient CLIENT;

    public RedisUtils(RedissonClient redissonClient) {
        RedisUtils.CLIENT = redissonClient;
    }


    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key   缓存的键值
     * @param value 缓存的值
     */
    public static <T> void setCacheObject(final String key, final T value) {
        setCacheObject(key, value, false);
    }

    /**
     * 缓存基本的对象，保留当前对象 TTL 有效期
     *
     * @param key       缓存的键值
     * @param value     缓存的值
     * @param isSaveTtl 是否保留TTL有效期(例如: set之前ttl剩余90 set之后还是为90)
     * @since Redis 6.X 以上使用 setAndKeepTTL 兼容 5.X 方案
     */
    public static <T> void setCacheObject(final String key, final T value, final boolean isSaveTtl) {
        RBucket<T> bucket = CLIENT.getBucket(key);
        if (isSaveTtl) {
            try {
                bucket.setAndKeepTTL(value);
            } catch (Exception e) {
                long timeToLive = bucket.remainTimeToLive();
                setCacheObject(key, value, Duration.ofMillis(timeToLive));
            }
        } else {
            bucket.set(value);
        }
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key      缓存的键值
     * @param value    缓存的值
     * @param duration 时间
     */
    public static <T> void setCacheObject(final String key, final T value, final Duration duration) {
        RBatch batch = CLIENT.createBatch();
        RBucketAsync<T> bucket = batch.getBucket(key);
        bucket.setAsync(value);
        bucket.expireAsync(duration);
        batch.execute();
    }

    /**
     * 获得缓存的基本对象。
     *
     * @param key 缓存键值
     * @return 缓存键值对应的数据
     */
    public static <T> T getCacheObject(final String key) {
        RBucket<T> rBucket = CLIENT.getBucket(key);
        return rBucket.get();
    }


    /**
     * 带锁时间的redis锁
     *
     * @param key  锁key值
     * @param time 锁时间
     * @param unit 时间单位
     * @return 是否锁成功
     */
    public static boolean lock(String key, int time, TimeUnit unit) {
        RLock rLock = CLIENT.getLock(key);
        if (rLock.isLocked()) {
            return false;
        }
        rLock.lock(time, unit);
        return true;
    }


    /**
     * 不带锁时间的redis锁
     *
     * @param key 锁的key值
     * @return 是否锁成功
     */
    public static boolean lock(String key) {
        RLock rLock = CLIENT.getLock(key);
        if (rLock.isLocked()) {
            return false;
        }
        rLock.lock();
        return true;
    }


    /**
     * 手动释放锁
     *
     * @param key 锁名称
     */
    public static void unlock(String key) {
        RLock rLock = CLIENT.getLock(key);
        if (rLock.isLocked()) {
            rLock.unlock();
        }
    }


    /**
     * 尝试获取锁
     *
     * @param key     锁key
     * @param timeout 获取锁的等待时间 ms
     * @return 是否获取成功
     */
    public static boolean tryLock(String key, Long timeout) throws InterruptedException {
        RLock rLock = CLIENT.getLock(key);
        return rLock.tryLock(timeout, TimeUnit.MILLISECONDS);
    }
}