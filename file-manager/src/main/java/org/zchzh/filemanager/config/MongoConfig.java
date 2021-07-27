package org.zchzh.filemanager.config;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;

import javax.annotation.Resource;

/**
 * @author zengchzh
 * @date 2021/7/27
 */

@Configuration
public class MongoConfig {


    /**
     * spring data mongo >= 2.3.0 弃用了 MongoFactory 改成了 MongoDatabaseFactory
     */
    @Autowired
    private MongoDatabaseFactory mongoDatabaseFactory;

    @Bean
    public GridFSBucket gridFSBucket() {
        MongoDatabase mongoDatabase = mongoDatabaseFactory.getMongoDatabase();
        return GridFSBuckets.create(mongoDatabase);
    }
}
