package org.zchzh.file.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zchzh.file.entity.VirtualFile;

import java.util.List;

/**
 * @author zengchzh
 * @date 2022/1/12
 */
public interface VirtualFileRepo extends JpaRepository<VirtualFile, Long> {
}
