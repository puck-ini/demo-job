package org.zchzh.shorturl.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zchzh.shorturl.entity.UrlMap;
import org.zchzh.shorturl.repo.UrlMapRepo;
import org.zchzh.shorturl.service.ShortUrlBloomFilter;
import org.zchzh.shorturl.service.UrlMapCache;
import org.zchzh.shorturl.service.UrlMapService;
import org.zchzh.shorturl.util.MurmurHash62;

import javax.annotation.PostConstruct;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * @author zchzh
 * @date 2022/1/14
 */

@Slf4j
@Service
public class HashUrlMapServiceImpl implements UrlMapService {

    @Autowired
    private UrlMapRepo urlMapRepo;

    @Autowired
    private UrlMapCache urlMapCache;

    @Autowired
    private ShortUrlBloomFilter bloomFilter;


    @PostConstruct
    public void initCacheAndBloomFilter() {
        urlMapRepo.findAll().forEach(urlMap -> {
            bloomFilter.add(urlMap.getShortUrl());
        });
    }

    @Override
    public String getShortUrl(String longUrl) {
        String shortUrl = MurmurHash62.hash(longUrl);
        UrlMap urlMap;
        if (bloomFilter.contains(shortUrl)) {
            urlMap = getCache(shortUrl);
            if (Objects.isNull(urlMap) || !Objects.equals(urlMap.getLongUrl(), longUrl)) {
                urlMap = getDbIfNullCreate(shortUrl, longUrl);
            }
        } else {
            urlMap = UrlMap.create(longUrl);
            urlMapRepo.save(urlMap);
            urlMapCache.put(shortUrl, urlMap);
        }
        return urlMap.getShortUrl();
    }

    @Override
    public String getLongUrl(String shortUrl) {
        UrlMap urlMap = getCache(shortUrl);
        if (Objects.isNull(urlMap)) {
            urlMap = urlMapRepo.findByShortUrl(shortUrl);
        }
        if (Objects.isNull(urlMap)) {
            throw new IllegalArgumentException("[" + shortUrl + "]不存在");
        }
        UrlMap urlMap1 = urlMapRepo.findById(urlMap.getId()).orElseThrow(() -> new IllegalArgumentException(""));
        CompletableFuture.runAsync(() -> {
            synchronized (urlMap1) {
                urlMap1.incrVisitCount();
                urlMapRepo.save(urlMap1);
            }
        });
        return urlMap.getLongUrl();
    }

    public UrlMap getCache(String shortUrl) {
        UrlMap urlMap = urlMapCache.get(shortUrl);
        if (Objects.nonNull(urlMap)) {
            urlMapCache.updateTtl(shortUrl);
        }
        return urlMap;
    }

    public UrlMap getDbIfNullCreate(String shortUrl, String longUrl) {
        UrlMap urlMap = urlMapRepo.findByShortUrl(shortUrl);
        if (Objects.nonNull(urlMap) && Objects.equals(urlMap.getLongUrl(), longUrl)) {
            urlMapCache.put(shortUrl, urlMap);
        } else {
            urlMap = UrlMap.createWithUniqueShortUrl(longUrl);
            urlMap = urlMapRepo.save(urlMap);
            urlMapCache.put(urlMap.getShortUrl(), urlMap);
            bloomFilter.add(urlMap.getShortUrl());
        }
        return urlMap;
    }

}
