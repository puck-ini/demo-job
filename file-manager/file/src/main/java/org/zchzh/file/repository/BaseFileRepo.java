package org.zchzh.file.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zchzh.file.entity.BaseFile;

import java.util.List;

/**
 * @author zengchzh
 * @date 2022/1/13
 */
public interface BaseFileRepo extends JpaRepository<BaseFile, Long> {
    List<BaseFile> findAllByFolderId(Long folderId);
}
