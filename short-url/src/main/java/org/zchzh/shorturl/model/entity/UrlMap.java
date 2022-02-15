package org.zchzh.shorturl.model.entity;

import cn.hutool.core.lang.Validator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.zchzh.shorturl.repo.UrlMapRepo;
import org.zchzh.shorturl.service.ShortUrlBloomFilter;
import org.zchzh.shorturl.util.ShortUrlBuilder;
import org.zchzh.shorturl.model.types.UrlState;
import org.zchzh.shorturl.util.SpringContextUtils;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

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

    private String longUrl;

    private LocalDateTime createTime;

    private Long visitCount;

    @Enumerated(EnumType.STRING)
    private UrlState state;

    @Version
    private Long version;

    @Transient
    @JsonIgnore
    private String tempUrl;

    public static UrlMap create(String shortUrl, String longUrl) {
        checkUrl(longUrl);
        UrlMap urlMap = new UrlMap();
        urlMap.setLongUrl(longUrl);
        urlMap.setVisitCount(0L);
        urlMap.setState(UrlState.AVAILABLE);
        urlMap.setTempUrl(longUrl);
        urlMap.setShortUrl(shortUrl);
        return urlMap;
    }

    public static UrlMap createWithUniqueShortUrl(String longUrl) {
        checkUrl(longUrl);
        UrlMap urlMap = new UrlMap();
        urlMap.setLongUrl(longUrl);
        urlMap.setVisitCount(0L);
        urlMap.setState(UrlState.AVAILABLE);
        urlMap.setTempUrl(longUrl);
        urlMap.checkAndSetUniqueShortUrl();
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

    private static final String REDUNDANCY = "redundancy";

    public void checkAndSetUniqueShortUrl() {
        this.shortUrl = SpringContextUtils.getBean(ShortUrlBuilder.class).hash(tempUrl);
        if (SpringContextUtils.getBean(ShortUrlBloomFilter.class).contains(this.shortUrl)) {
            UrlMap dbMap = SpringContextUtils.getBean(UrlMapRepo.class).findByShortUrl(this.shortUrl);
            if (Objects.nonNull(dbMap)) {
                this.tempUrl = this.tempUrl + REDUNDANCY;
                this.checkAndSetUniqueShortUrl();
            }
        }
    }


    @PrePersist
    private void prePersist() {
        this.createTime = LocalDateTime.now();
    }


    public void changeState(UrlState state) {
        this.state = state;
    }

    public boolean isAvailable() {
        return this.state == UrlState.AVAILABLE;
    }
}
