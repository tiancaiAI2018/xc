package com.xuecheng.mange_cms_client.config;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(GridFSProperties.class)
public class GridFSConfig {
    private GridFSProperties gridFSProperties;
    public GridFSConfig(GridFSProperties gridFSProperties){
        this.gridFSProperties=gridFSProperties;
    }
    @Bean
    public GridFSBucket gridFSBucket(MongoClient mongoClient){
        MongoDatabase database = mongoClient.getDatabase(this.gridFSProperties.getDatabase());
        return GridFSBuckets.create(database);
    }
}
