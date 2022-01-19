package org.zchzh.shorturl.service;

/**
 * @author zengchzh
 * @date 2022/1/17
 */
public interface ShortUrlBloomFilter {

    /**
     * 添加短链接到bloomfilter中
     * @param shortUrl 短链接
     */
    void add(String shortUrl);

    /**
     * 判断短链接是否存在
     * @param shortUrl 短链接
     * @return true 表示可能存在，false 表示不存在
     */
    boolean contains(String shortUrl);
}
