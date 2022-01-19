package org.zchzh.shorturl.service;

/**
 * @author zchzh
 * @date 2022/1/14
 */
public interface UrlMapService {

    /**
     * 通过长链接获取短链接
     * @param longUrl 长链接
     * @return 短链接
     */
    String getShortUrl(String longUrl);

    /**
     * 通过短链接获取长链接
     * @param shortUrl 短链接
     * @return 长链接
     */
    String getLongUrl(String shortUrl);

    /**
     * 自定义长短链接映射
     * @param shortUrl 短链接
     * @param longUrl 长链接
     * @return 短链接
     */
    String custom(String shortUrl, String longUrl);

}
