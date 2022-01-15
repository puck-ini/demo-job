package org.zchzh.shorturl.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zchzh.shorturl.entity.UrlMap;
import org.zchzh.shorturl.repo.UrlMapRepo;
import org.zchzh.shorturl.service.CacheService;
import org.zchzh.shorturl.service.ShortUrlService;
import org.zchzh.shorturl.util.MurmurHash62;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * @author zchzh
 * @date 2022/1/14
 */

@Service
public class HashShortUrlServiceImpl implements ShortUrlService {

    @Autowired
    private UrlMapRepo urlMapRepo;

    @Autowired
    private CacheService cacheService;

    @Override
    public String getShortUrl(String longUrl) {
        String shortUrl = MurmurHash62.hash(longUrl);
        UrlMap urlMap = getCache(shortUrl);
        if (Objects.isNull(urlMap)) {
            urlMap = getDb(shortUrl);
        }
        if (Objects.isNull(urlMap)) {
            urlMap = UrlMap.create(longUrl);
            urlMap = urlMapRepo.save(urlMap);
            cacheService.put(urlMap.getShortUrl(), urlMap);
        }
        return urlMap.getShortUrl();
    }

    @Override
    public String getLongUrl(String shortUrl) {
        UrlMap urlMap = getCache(shortUrl);
        if (Objects.isNull(urlMap)) {
            urlMap = getDb(shortUrl);
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
        return urlMap.getOriginUrl();
    }

    public UrlMap getCache(String shortUrl) {
        UrlMap urlMap = cacheService.get(shortUrl);
        if (Objects.nonNull(urlMap)) {
            cacheService.updateTtl(shortUrl);
        }
        return urlMap;
    }

    public UrlMap getDb(String shortUrl) {
        UrlMap urlMap = urlMapRepo.findByShortUrl(shortUrl);
        if (Objects.nonNull(urlMap)) {
            cacheService.put(shortUrl, urlMap);
        }
        return urlMap;
    }

}
