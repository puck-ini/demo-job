package org.zchzh.shorturl.service;

/**
 * @author zengchzh
 * @date 2022/1/17
 */
public interface ShortUrlBloomFilter {

    void add(String shortUrl);

    boolean contains(String shortUrl);
}
