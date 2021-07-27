package org.zchzh.filemanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.zchzh.filemanager.entity.DemoFile;

/**
 * @author zengchzh
 * @date 2021/7/26
 */
public interface DemoFileRepo extends JpaRepository<DemoFile, Long>, JpaSpecificationExecutor<DemoFile> {
}
