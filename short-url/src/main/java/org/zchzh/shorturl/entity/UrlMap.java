package org.zchzh.shorturl.entity;

import cn.hutool.core.lang.Validator;
import lombok.Data;
import org.zchzh.shorturl.types.UrlState;
import org.zchzh.shorturl.util.MurmurHash62;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author zchzh
 * @date 2022/1/14
 */

@Data
@Entity
public class UrlMap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String shortUrl;

    private String originUrl;

    private LocalDateTime createTime;

    private Long visitCount;

    private UrlState state;

    public static UrlMap create(String longUrl) {
        checkUrl(longUrl);
        UrlMap urlMap = new UrlMap();
        urlMap.setOriginUrl(longUrl);
        urlMap.setVisitCount(0L);
        urlMap.setShortUrl(MurmurHash62.hash(longUrl));
        urlMap.setState(UrlState.AVAILABLE);
        return urlMap;

    }

    private static void checkUrl(String url) {
        if (!Validator.isUrl(url)) {
            throw new IllegalArgumentException("[" + url + "]不是一个url");
        }
    }

    public void incrVisitCount() {
        visitCount++;
    }


    @PrePersist
    private void prePersist() {
        this.createTime = LocalDateTime.now();
    }

}
