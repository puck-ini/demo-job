package org.zchzh.shorturl.service;

import org.zchzh.shorturl.model.entity.UrlMap;

/**
 * @author zchzh
 * @date 2022/1/15
 */
public interface UrlMapCache {


    /**
     * 获取缓存中的 UrlMap 对象
     * @param key 键值
     * @return 返回缓存的对象
     */
    UrlMap get(String key);

    /**
     * 更新过期时间
     * @param key 键值
     */
    void updateTtl(String key);

    /**
     * 讲 UrlMap 保存在缓存中
     * @param key 键值
     * @param value UrlMap
     */
    void put(String key, UrlMap value);

}
