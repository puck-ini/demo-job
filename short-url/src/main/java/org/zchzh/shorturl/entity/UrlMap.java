package org.zchzh.shorturl.entity;

import cn.hutool.core.lang.Validator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.zchzh.shorturl.repo.UrlMapRepo;
import org.zchzh.shorturl.types.UrlState;
import org.zchzh.shorturl.util.MurmurHash62;
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

    private String originUrl;

    private LocalDateTime createTime;

    private Long visitCount;

    private UrlState state;

    @Transient
    @JsonIgnore
    private String tempUrl;

    public static UrlMap create(String longUrl) {
        checkUrl(longUrl);
        UrlMap urlMap = new UrlMap();
        urlMap.setOriginUrl(longUrl);
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
        this.shortUrl = MurmurHash62.hash(tempUrl);
        UrlMap dbMap = SpringContextUtils.getBean(UrlMapRepo.class).findByShortUrl(this.shortUrl);
        if (Objects.nonNull(dbMap)) {
            if (!Objects.equals(dbMap.getOriginUrl(), this.originUrl)) {
                this.tempUrl = this.tempUrl + REDUNDANCY;
                this.checkAndSetUniqueShortUrl();
            }
        }
    }


    @PrePersist
    private void prePersist() {
        this.createTime = LocalDateTime.now();
    }

}
