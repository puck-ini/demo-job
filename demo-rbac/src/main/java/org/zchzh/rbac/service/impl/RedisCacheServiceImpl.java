package org.zchzh.rbac.service.impl;

import org.springframework.data.redis.core.RedisTemplate;
import org.zchzh.rbac.service.CacheService;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author zengchzh
 * @date 2021/9/7
 */
public class RedisCacheServiceImpl implements CacheService {

    private final RedisTemplate<String, Object> redisTemplate;

    private static final long TIMEOUT = 600L;

    public RedisCacheServiceImpl(RedisTemplate<String, Object>  redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value, TIMEOUT, TimeUnit.SECONDS);
    }

    @Override
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public void incr(String key) {
        Object o = get(key);
        if (Objects.isNull(o)) {
            set(key, 0);
        }
        redisTemplate.opsForValue().increment(key);
    }


}
