package org.zchzh.shorturl.service;

import org.zchzh.shorturl.entity.UrlMap;

/**
 * @author zchzh
 * @date 2022/1/15
 */
public interface UrlMapCache {


    UrlMap get(String key);

    void updateTtl(String key);

    void put(String key, UrlMap value);

}
