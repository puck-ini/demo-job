package org.zchzh.file.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zchzh.file.entity.NetFile;

/**
 * @author zengchzh
 * @date 2022/1/14
 */
public interface NetFileRepo extends JpaRepository<NetFile, Long> {
}
