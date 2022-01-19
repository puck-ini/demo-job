package org.zchzh.shorturl.service.impl;

import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;
import cn.hutool.core.date.DateUnit;
import org.springframework.stereotype.Service;
import org.zchzh.shorturl.model.entity.UrlMap;
import org.zchzh.shorturl.service.UrlMapCache;

/**
 * @author zchzh
 * @date 2022/1/15
 */

@Service
public class LocalUrlMapCacheImpl implements UrlMapCache {

    private static final Cache<String, UrlMap> CACHE = CacheUtil.newLRUCache(100);

    @Override
    public UrlMap get(String key) {
        return CACHE.get(key);
    }

    @Override
    public void updateTtl(String key) {
        CACHE.get(key, true);
    }

    @Override
    public void put(String key, UrlMap value) {
        CACHE.put(key, value, DateUnit.MINUTE.getMillis() * 10);
    }
}
