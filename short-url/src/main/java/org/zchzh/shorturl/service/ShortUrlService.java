package org.zchzh.shorturl.service;

/**
 * @author zchzh
 * @date 2022/1/14
 */
public interface ShortUrlService {

    String getShortUrl(String longUrl);

    String getLongUrl(String shortUrl);

}
