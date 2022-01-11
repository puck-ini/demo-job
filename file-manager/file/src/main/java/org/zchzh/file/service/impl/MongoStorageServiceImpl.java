package org.zchzh.file.service.impl;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsCriteria;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.zchzh.file.service.StorageService;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * @author zengchzh
 * @date 2021/7/27
 */
@Slf4j
public class MongoStorageServiceImpl implements StorageService {

    private final GridFsTemplate gridFsTemplate;

    private final GridFSBucket gridFsBucket;

    public MongoStorageServiceImpl(GridFsTemplate gridFsTemplate, GridFSBucket gridFsBucket) {
        this.gridFsTemplate = gridFsTemplate;
        this.gridFsBucket = gridFsBucket;
    }

    @Override
    public void upload(String fileName, InputStream is) {
        gridFsTemplate.store(is, fileName);
    }

    @Override
    public InputStream getInputStream(String fileName) {
        Query query = new Query(GridFsCriteria.whereFilename().is(fileName));
        GridFSFile fsFile = gridFsTemplate.findOne(query);
        InputStream is = null;
        if (Objects.nonNull(fsFile)) {
            GridFSDownloadStream fsDownloadStream = gridFsBucket.openDownloadStream(fsFile.getObjectId());
            GridFsResource resource = new GridFsResource(fsFile, fsDownloadStream);
            try {
                is = resource.getInputStream();
            } catch (IOException e) {
                log.error("获取mongo文件流失败", e);
            }
        }
        return is;
    }
}
