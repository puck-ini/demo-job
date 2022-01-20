package org.zchzh.shorturl.model.entity;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author zengchzh
 * @date 2022/1/20
 *
 */

@Entity
public class SegmentIncrId {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long start;

    private Long end;

    private LocalDateTime createTime;

    @Transient
    private Long currId;

    @PrePersist
    private void prePersist() {
        this.createTime = LocalDateTime.now();
    }

    /**
     * 分段大小
     */
    private static final Long SEGMENT_SIZE = 10L;

    @PostPersist
    private void postPersist() {
        start = id * SEGMENT_SIZE - SEGMENT_SIZE + 1;
        end = id * SEGMENT_SIZE;
    }


    public synchronized Long nextId() {
        if (Objects.isNull(currId)) {
            currId = start;
        }
        if (currId > end) {
            return null;
        }
        return currId++;
    }
}
