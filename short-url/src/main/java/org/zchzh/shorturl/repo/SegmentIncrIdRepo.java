package org.zchzh.shorturl.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zchzh.shorturl.model.entity.SegmentIncrId;

/**
 * @author zengchzh
 * @date 2022/1/20
 */
public interface SegmentIncrIdRepo extends JpaRepository<SegmentIncrId, Long> {
}
