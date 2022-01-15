package org.zchzh.shorturl.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zchzh.shorturl.entity.UrlMap;

/**
 * @author zchzh
 * @date 2022/1/14
 */
public interface UrlMapRepo extends JpaRepository<UrlMap, Long> {

    /**
     * 通过短链接获取实体
     * @param shortUrl 短链接
     * @return 返回实体
     */
    UrlMap findByShortUrl(String shortUrl);

    /**
     * 判断短链接是否已存在
     * @param shortUrl 短链接
     * @return true 表示已存在
     */
    boolean existsByShortUrl(String shortUrl);
}
