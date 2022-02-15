package org.zchzh.shorturl.service.impl;

import cn.hutool.core.lang.hash.MurmurHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.zchzh.shorturl.model.entity.UrlMap;
import org.zchzh.shorturl.model.event.IncrVisitCountEvent;
import org.zchzh.shorturl.model.types.UrlState;
import org.zchzh.shorturl.repo.UrlMapRepo;
import org.zchzh.shorturl.util.ShortUrlBuilder;
import org.zchzh.shorturl.service.UrlMapCache;
import org.zchzh.shorturl.service.UrlMapService;
import org.zchzh.shorturl.util.SpringContextUtils;

import java.util.Objects;
import java.util.Optional;

/**
 * @author zengchzh
 * @date 2022/1/20
 */

@Component
@ConditionalOnProperty(prefix = "short-url", name = "service", havingValue = "incr")
public class IncrUrlMapServiceImpl implements UrlMapService {

    @Autowired
    private UrlMapRepo urlMapRepo;

    @Autowired
    private UrlMapCache urlMapCache;

    @Autowired
    private ShortUrlBuilder builder;

    /**
     * 通过长链接获取短链接，这里用缓存是为了保证在一定时间内通过同样的长链接获取的短链接是一样的
     * @param longUrl 长链接
     * @return 返回短链接
     */
    @Override
    public String getShortUrl(String longUrl) {
        String key = String.valueOf(MurmurHash.hash32(longUrl));
        UrlMap urlMap = urlMapCache.get(key);
        if (Objects.isNull(urlMap)) {
            urlMap = urlMapRepo.save(UrlMap.create(builder.incr(longUrl), longUrl));
            urlMapCache.put(key, urlMap);
        }
        return urlMap.getShortUrl();
    }

    /**
     * 通过短链接获取长链接，这里使用缓存是为了缓解数据库访问压力
     * @param shortUrl 短链接
     * @return 长链接
     */
    @Override
    public String getLongUrl(String shortUrl) {
        UrlMap urlMap = urlMapCache.get(shortUrl);
        if (Objects.isNull(urlMap)) {
            urlMap = urlMapRepo.findByShortUrl(shortUrl);
        }
        if (Objects.isNull(urlMap)) {
            throw new IllegalArgumentException("[" + shortUrl + "]不存在");
        }
        urlMapCache.put(urlMap.getShortUrl(), urlMap);
        SpringContextUtils.pushEvent(new IncrVisitCountEvent(this, urlMap.getId()));
        return urlMap.getLongUrl();
    }

    @Override
    public String custom(String shortUrl, String longUrl) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void invalid(String shortUrl) {
        Optional.ofNullable(urlMapRepo.findByShortUrlAndState(shortUrl, UrlState.AVAILABLE)).ifPresent(urlMap -> {
            urlMap.changeState(UrlState.INVALID);
            urlMapRepo.save(urlMap);
            String key = String.valueOf(MurmurHash.hash32(urlMap.getLongUrl()));
            urlMapCache.remove(key);
            urlMapCache.remove(urlMap.getShortUrl());
        });
    }
}
