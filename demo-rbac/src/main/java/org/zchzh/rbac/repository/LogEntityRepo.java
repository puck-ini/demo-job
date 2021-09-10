package org.zchzh.rbac.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zchzh.rbac.model.entity.LogEntity;

/**
 * @author zengchzh
 * @date 2021/9/10
 */
public interface LogEntityRepo extends JpaRepository<LogEntity, Long> {
}
