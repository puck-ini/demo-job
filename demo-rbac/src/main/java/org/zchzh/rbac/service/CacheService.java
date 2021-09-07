package org.zchzh.rbac.service;

/**
 * @author zengchzh
 * @date 2021/9/7
 */
public interface CacheService {

    void set(String key, Object value);

    Object get(String key);

    void incr(String key);

}
