package org.zchzh.shorturl.service.impl;

import cn.hutool.core.lang.hash.MurmurHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.zchzh.shorturl.model.entity.UrlMap;
import org.zchzh.shorturl.model.event.IncrVisitCountEvent;
import org.zchzh.shorturl.repo.UrlMapRepo;
import org.zchzh.shorturl.util.ShortUrlBuilder;
import org.zchzh.shorturl.service.UrlMapCache;
import org.zchzh.shorturl.service.UrlMapService;
import org.zchzh.shorturl.util.SpringContextUtils;

import java.util.Objects;

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
     * 在一定时间内通过长链接获取的短链接是一样的
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
}
