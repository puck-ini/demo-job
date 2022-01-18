package org.zchzh.shorturl.service.impl;

import cn.hutool.bloomfilter.BitMapBloomFilter;
import org.springframework.stereotype.Component;
import org.zchzh.shorturl.service.ShortUrlBloomFilter;

/**
 * @author zengchzh
 * @date 2022/1/17
 */

@Component
public class LocalShortUrlBloomFilterImpl implements ShortUrlBloomFilter {

    private final BitMapBloomFilter filter = new BitMapBloomFilter(100);

    @Override
    public void add(String shortUrl) {
        filter.add(shortUrl);
    }

    @Override
    public boolean contains(String shortUrl) {
        return filter.contains(shortUrl);
    }
}
