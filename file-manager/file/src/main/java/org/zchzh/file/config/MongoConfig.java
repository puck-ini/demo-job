package org.zchzh.file.config;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.zchzh.file.constants.Constants;
import org.zchzh.file.service.StorageService;
import org.zchzh.file.service.impl.MongoStorageServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author zengchzh
 * @date 2021/7/27
 */

@Configuration
@ConditionalOnProperty(prefix = "file.storage", name = "type", havingValue = Constants.MONGO)
public class MongoConfig {


    /**
     * spring data mongo >= 2.3.0 弃用了 MongoFactory 改成了 MongoDatabaseFactory
     */
//    private MongoDatabaseFactory mongoDatabaseFactory;

    @Bean
    public MongoDatabaseFactory mongoDatabaseFactory(@Autowired StorageProp prop) {
        List<ServerAddress> serverAddressList = new ArrayList<>();

        String[] replicaSet = prop.getUrl().split(",");

        for (String s : replicaSet) {
            String host = s.substring(0, s.lastIndexOf(":"));
            String port = s.substring(s.lastIndexOf(":") + 1);
            serverAddressList.add(new ServerAddress(host, Integer.parseInt(port)));
        }
        MongoClientSettings settings;
        if(Objects.nonNull(prop.getUsername())) {
            // 连接认证，如果设置了用户名和密码的话
            MongoCredential credential = MongoCredential.createScramSha1Credential(prop.getUsername(),
                    prop.getAuthenticationDb(), prop.getPassword().toCharArray());
            settings = MongoClientSettings.builder()
                    .credential(credential)
                    .applyToClusterSettings(builder -> builder.hosts(serverAddressList)).build();
        }else {
            settings = MongoClientSettings.builder()
                    .applyToClusterSettings(builder -> builder.hosts(serverAddressList)).build();
        }

        MongoClient mongoClient = MongoClients.create(settings);
        // 创建MongoDbFactory
        return new SimpleMongoClientDatabaseFactory(mongoClient,
                prop.getDatabase());
    }

    @Bean
    public MongoTemplate mongoTemplate(@Autowired MongoDatabaseFactory mongoDatabaseFactory) {
        return new MongoTemplate(mongoDatabaseFactory);
    }
    @Bean
    public GridFSBucket gridFsBucket(@Autowired MongoDatabaseFactory mongoDatabaseFactory) {
        MongoDatabase mongoDatabase = mongoDatabaseFactory.getMongoDatabase();
        return GridFSBuckets.create(mongoDatabase);
    }

    @Bean
    public GridFsTemplate gridFsTemplate(@Autowired MongoDatabaseFactory mongoDatabaseFactory,
                                         @Autowired MongoTemplate mongoTemplate) {
        return new GridFsTemplate(mongoDatabaseFactory, mongoTemplate.getConverter());
    }

    @Bean
    public StorageService storageService(@Autowired GridFsTemplate gridFsTemplate,
                                         @Autowired GridFSBucket gridFsBucket) {
        return new MongoStorageServiceImpl(gridFsTemplate, gridFsBucket);
    }
}
