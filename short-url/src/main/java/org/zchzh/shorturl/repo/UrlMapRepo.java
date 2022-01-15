package org.zchzh.shorturl.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zchzh.shorturl.entity.UrlMap;

/**
 * @author zchzh
 * @date 2022/1/14
 */
public interface UrlMapRepo extends JpaRepository<UrlMap, Long> {

    UrlMap findByShortUrl(String shortUrl);
}
