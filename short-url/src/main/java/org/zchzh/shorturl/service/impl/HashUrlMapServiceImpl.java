package org.zchzh.shorturl.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.zchzh.shorturl.model.entity.UrlMap;
import org.zchzh.shorturl.model.event.IncrVisitCountEvent;
import org.zchzh.shorturl.model.types.UrlState;
import org.zchzh.shorturl.repo.UrlMapRepo;
import org.zchzh.shorturl.service.ShortUrlBloomFilter;
import org.zchzh.shorturl.util.ShortUrlBuilder;
import org.zchzh.shorturl.service.UrlMapCache;
import org.zchzh.shorturl.service.UrlMapService;
import org.zchzh.shorturl.util.SpringContextUtils;

import javax.annotation.PostConstruct;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.regex.Pattern;

/**
 * @author zchzh
 * @date 2022/1/14
 */

@Slf4j
@Component
@ConditionalOnProperty(prefix = "short-url", name = "service", havingValue = "hash")
public class HashUrlMapServiceImpl implements UrlMapService {

    @Autowired
    private UrlMapRepo urlMapRepo;

    @Autowired
    private UrlMapCache urlMapCache;

    @Autowired
    private ShortUrlBloomFilter bloomFilter;

    @Autowired
    private ShortUrlBuilder builder;


    @PostConstruct
    public void initCacheAndBloomFilter() {
        urlMapRepo.findAll().forEach(urlMap -> {
            bloomFilter.add(urlMap.getShortUrl());
        });
    }

    /**
     * TODO 如何在上一个短链接失效后下一个该长链接生成的短链接在失效之前每次获取都不会新创建。
     * @param longUrl 长链接
     * @return
     */
    @Override
    public String getShortUrl(String longUrl) {
        String shortUrl = builder.hash(longUrl);
        UrlMap urlMap;
        if (bloomFilter.contains(shortUrl)) {
            urlMap = getCache(shortUrl);
            if (Objects.isNull(urlMap) || !Objects.equals(urlMap.getLongUrl(), longUrl)) {
                urlMap = getDbIfNullCreate(shortUrl, longUrl);
            }
        } else {
            urlMap = UrlMap.create(shortUrl, longUrl);
            urlMapRepo.save(urlMap);
            urlMapCache.put(shortUrl, urlMap);
            bloomFilter.add(shortUrl);
        }
        return urlMap.getShortUrl();
    }

    public UrlMap getCache(String shortUrl) {
        UrlMap urlMap = urlMapCache.get(shortUrl);
        if (Objects.nonNull(urlMap)) {
            urlMapCache.updateTtl(shortUrl);
        }
        return urlMap;
    }

    public UrlMap getDbIfNullCreate(String shortUrl, String longUrl) {
        UrlMap urlMap = urlMapRepo.findByShortUrlAndState(shortUrl, UrlState.AVAILABLE);
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

    @Override
    public String getLongUrl(String shortUrl) {
        UrlMap urlMap = getCache(shortUrl);
        if (Objects.isNull(urlMap)) {
            urlMap = urlMapRepo.findByShortUrlAndState(shortUrl, UrlState.AVAILABLE);
        }
        if (Objects.isNull(urlMap)) {
            throw new IllegalArgumentException("[" + shortUrl + "]不存在");
        }
        SpringContextUtils.pushEvent(new IncrVisitCountEvent(this, urlMap.getId()));
        return urlMap.getLongUrl();
    }

    @Override
    public String custom(String shortUrl, String longUrl) {
        checkShortUrl(shortUrl);
        Optional.ofNullable(urlMapRepo.findByShortUrl(shortUrl)).ifPresent(urlMap -> {
            throw new IllegalArgumentException("短链接[" + shortUrl + "]已存在");
        });
        UrlMap urlMap = UrlMap.create(shortUrl, longUrl);
        return urlMapRepo.save(urlMap).getShortUrl();
    }

    @Override
    public void invalid(String shortUrl) {
        Optional.ofNullable(urlMapRepo.findByShortUrlAndState(shortUrl, UrlState.AVAILABLE)).ifPresent(urlMap -> {
            urlMap.changeState(UrlState.INVALID);
            urlMapRepo.save(urlMap);
            urlMapCache.remove(shortUrl);
        });
    }

    /**
     * 大小写字母数字6-8个字符
     */
    private static final Pattern PATTERN = Pattern.compile("^[A-Za-z0-9]{6,8}$");

    private void checkShortUrl(String shortUrl) {
        if (!PATTERN.matcher(shortUrl).matches()) {
            throw new IllegalArgumentException("大小写字母数字6-8个字符");
        }
    }

}
